package io.github.ppichler94.vuekeycloak.cart

import io.github.ppichler94.vuekeycloak.UserManager
import io.github.ppichler94.vuekeycloak.order.Order
import io.github.ppichler94.vuekeycloak.order.OrderDto
import io.github.ppichler94.vuekeycloak.order.OrderLine
import io.github.ppichler94.vuekeycloak.product.ProductRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CartController(
    private val basketRepository: CartRepository,
    private val productRepository: ProductRepository,
) {
    @GetMapping("cart")
    fun cart(): CartDto {
        val user = SecurityContextHolder.getContext().authentication.principal as OidcUser
        val userId = user.userInfo.preferredUsername
        val cart = basketRepository.getCartByUserId(userId)
        return cart.toDto()
    }

    @PostMapping("/cart/add/{productId}")
    fun addToCart(@PathVariable productId: Int) {
        val user = UserManager.getUsername()
        val basket = basketRepository.getCartByUserId(user)
        val item = basket.cartItems.find { it.productId == productId }
        if (item != null) {
            item.amount++
        } else {
            basket.cartItems.add(CartItem(productId, 1))
        }
    }

    @PostMapping("/cart")
    fun updateCart(cart: CartDto): CartDto {
        val user = SecurityContextHolder.getContext().authentication.principal as OidcUser
        val userId = user.userInfo.preferredUsername
        val newCart = Cart(
            userId,
            cart.cartItems
                .filter { it.amount != 0 }
                .map { CartItem(it.productId, it.amount) }
                .toMutableSet()
        )
        basketRepository.save(newCart)
        return newCart.toDto()
    }

    @PostMapping("/checkout")
    fun checkout(cart: CartDto): OrderDto {
        val user = SecurityContextHolder.getContext().authentication.principal as OidcUser
        val userId = user.userInfo.preferredUsername

        val order = Order(
            customerId = userId,
            orderLines = cart.cartItems
                .map {
                    // the price is read directly from the repository because we cannot trust the price sent
                    // by the post-request
                    val price = productRepository.findById(it.productId).get().price
                    OrderLine(null, it.productId, it.amount, price)
                }
                .toSet()
        )

        return OrderDto(order.id!!, order.date, order.customerId, order.orderLines)
    }

    private fun Cart.toDto(): CartDto {
        val items = cartItems.map {
            val product = productRepository.findById(it.productId)
            require(product.isPresent) { "Product not found" }
            CartItemDto(it.productId, it.amount, product.get().name, product.get().price)
        }
        return CartDto(items.toMutableList())
    }
}

@Repository
class CartRepository {
    private val carts = mutableMapOf<String, Cart>()

    fun getCartByUserId(userId: String): Cart {
        if (!carts.containsKey(userId)) {
            carts[userId] = Cart(userId)
        }
        return carts[userId]!!
    }

    fun clearCart(userId: String) {
        carts.remove(userId)
    }

    fun save(cart: Cart): Cart {
        carts[cart.userId] = cart
        return cart
    }
}

data class Cart(
    val userId: String,
    val cartItems: MutableSet<CartItem> = mutableSetOf()
)

data class CartItem(
    val productId: Int,
    var amount: Int,
)

data class CartDto(
    val cartItems: MutableList<CartItemDto> = mutableListOf()
)

data class CartItemDto(
    var productId: Int = 0,
    var amount: Int = 0,
    var productName: String = "",
    var productPrice: Int = 0,
)