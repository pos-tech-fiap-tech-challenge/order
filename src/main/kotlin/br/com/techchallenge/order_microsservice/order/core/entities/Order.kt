package br.com.techchallenge.order_microsservice.order.core.entities

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.cglib.core.Local
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


data class Order(
    val orderId: UUID = UUID.randomUUID(),
    var progress:OrderProgress = OrderProgress.RECEIVED,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var productionStartDate: LocalDateTime? = null,
    var payment: PaymentData? = null,
    var customer: Customer,
    val items: List<OrderItem>,
    var delivery: DeliveryData? = null
) {
    fun setPayment( payment:Payment ) {
        this.payment =  PaymentData(payment.paymentProgress)
    }

    fun setDeliveryDate(date:LocalDateTime){
        if(delivery != null){
            delivery!!.deliveryDateTime = date;
        }else{
            this.delivery = DeliveryData(date,date)
        }
    }
    fun setExpectedDeliveryDate(date:LocalDateTime){
        if(delivery != null){
            delivery!!.expectedDeliveryDate = date;
        }else{
            this.delivery = DeliveryData(date,null)
        }
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

data class DeliveryData(
    var expectedDeliveryDate:LocalDateTime?,
    var deliveryDateTime:LocalDateTime?
)

data class PaymentData(
    var paymentProgress: PaymentProgress,
    var paymentDate: LocalDateTime? = null
)
