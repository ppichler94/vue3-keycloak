package io.github.ppichler94.vuekeycloak.product

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val repository: ProductRepository) {
    @GetMapping("/products")
    fun getProducts(): Iterable<ProductDto> {
        return repository.findAll().map { it.toDto() }
    }

    @PostMapping("/products")
//    @PreAuthorize("hasRole('ADMIN')")
    fun newProduct(@RequestBody @Valid dto: NewProductDto): Product {
        return repository.save(dto.toEntity())
    }

    private fun Product.toDto(): ProductDto {
        return ProductDto(id!!, name, "", price)
    }

    private fun NewProductDto.toEntity(): Product {
        return Product(null, name!!, price!!, true)
    }
}

@Repository
interface ProductRepository : CrudRepository<Product, Int>

@Table("products")
data class Product(
    @Id val id: Int?,
    @field:NotBlank
    val name: String,
    @field:Min(1)
    val price: Int,
    val inStock: Boolean,
)

data class ProductDto(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
)

data class NewProductDto(
    @field:NotBlank
    val name: String,
    val description: String,
    @field:Min(1)
    val price: Int,
)