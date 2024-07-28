package io.github.ppichler94.vuekeycloak.order

import io.github.ppichler94.vuekeycloak.cart.CartRepository
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.temporal.ChronoUnit

@RestController
class OrderController(
    private val repository: OrderRepository,
    private val cartRepository: CartRepository,
) {

    @GetMapping("/orders")
    fun getOrders(): Iterable<OrderDto> {
        return repository.findAll().map {
            it.toDto()
        }
    }

    @PostMapping("new-order")
    fun newOrder(order: NewOrderDto): OrderDto {
        val user = SecurityContextHolder.getContext().authentication.principal as OidcUser
        val userId = user.userInfo.preferredUsername
        val newOrder = Order(
            customerId = userId,
            orderLines = order.orderLines
                .filter { it.amount > 0 }
                .map { OrderLine(null, it.product, it.amount, it.purchasePrice) }
                .toSet()
        )
        val savedOrder = repository.save(newOrder)
        cartRepository.clearCart(userId)
        return savedOrder.toDto()
    }

    fun Order.toDto(): OrderDto {
        return OrderDto(id!!, date, customerId, orderLines)
    }
}

@Repository
interface OrderRepository : CrudRepository<Order, Int>

@Table("orders")
data class Order(
    @Id val id: Int? = null,
    val date: Instant = Instant.now().truncatedTo(ChronoUnit.MICROS),
    val customerId: String,
    val orderLines: Set<OrderLine>
)

data class OrderDto(
    val id: Int,
    val date: Instant,
    val customerName: String,
    val orderLines: Set<OrderLine>
)

data class NewOrderDto(
    var orderLines: MutableList<NewOrderLine> = mutableListOf()
)

data class NewOrderLine(
    var product: Int = 0,
    var amount: Int = 0,
    var purchasePrice: Int = 0,
)

@Table("order_lines")
data class OrderLine(
    @Id val id: Int?,
    val product: Int,
    val amount: Int,
    val purchasePrice: Int,
)

