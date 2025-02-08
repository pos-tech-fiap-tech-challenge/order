package br.com.techchallenge.order_microsservice.order.core.entities

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.*


data class Order(
    val orderId: UUID = UUID.randomUUID(),
    val progress:OrderProgress = OrderProgress.RECEIVED,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var paymentProgress: PaymentProgress? = null,
    var customer: Customer,
    val items: List<OrderItem>
) {
    fun setPayment( payment:Payment ) {
        this.paymentProgress =  payment.paymentProgress
    }
    val amount:Double
        get(){
            return items.sumOf{it.amountPerItem * it.quantity}
        }

    data class OrderItem (
        val orderItemId: UUID,
        val amountPerItem: Double,
        val productId: UUID,
        val productName: String,
        val quantity: Int
    )
}