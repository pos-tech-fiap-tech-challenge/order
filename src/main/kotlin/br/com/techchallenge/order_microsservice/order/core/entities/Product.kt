package br.com.techchallenge.order_microsservice.order.core.entities

import java.util.UUID

data class Product (
    val productId: UUID,
    val price: Double,
    val name: String,
    val category: ProductCategory
){
    fun toOrderItem(qtd: Int ): Order.OrderItem =
        with(this) {
            Order.OrderItem(
                orderItemId = UUID.randomUUID(),
                productName = this.name,
                productId = this.productId,
                amountPerItem = this.price,
                quantity = qtd
            )
        }
}