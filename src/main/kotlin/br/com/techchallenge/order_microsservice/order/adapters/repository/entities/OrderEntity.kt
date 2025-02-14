package br.com.techchallenge.order_microsservice.order.adapters.repository.entities

import br.com.techchallenge.order_microsservice.customers.adapters.repository.entities.CustomerEntity
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("orderDocument")
data class OrderEntity (
    var id: String,
    @DBRef
    var customer: CustomerEntity,
    var orderItems: List<OrderItemEntity>,
    var payment: PaymentEntity?,
    var progress: String,
    var createdAt: LocalDateTime,
    var delivery: DeliveryEntity?,
    var productionStartDate: LocalDateTime?
)

data class OrderItemEntity (
    val orderItemId: String,
    val amountPerItem: Double,
    val productId: String,
    val productName: String,
    val quantity: Int
)

data class DeliveryEntity(
    var expectedDeliveryDate:LocalDateTime?,
    var deliveryDateTime:LocalDateTime?
)

data class PaymentEntity(
    val paymentProgress: PaymentProgress,
    val paymentDate: LocalDateTime? = null
)