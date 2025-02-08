package br.com.techchallenge.order_microsservice.order.adapters.repository.order.entities

import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("orderDocument")
data class OrderEntity (
    var id: String,
    var customer: String,
    var orderItems: List<OrderItemEntity>,
    var paymentProgress: String,
    var progress: String,
    var createdAt: LocalDateTime
)

data class OrderItemEntity (
    val orderItemId: String,
    val amountPerItem: Double,
    val productId: String,
    val productName: String,
    val quantity: Int
)
