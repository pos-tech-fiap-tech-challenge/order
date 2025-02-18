package br.com.techchallenge.order_microsservice.order.adapters.external.payment.DTO

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

const val DEFAULT_PAYMENT_METHOD = "MERCADO_PAGO"
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentOrderRequest(
    val orderId: String,
    val paymentGateway:String = DEFAULT_PAYMENT_METHOD,
    val items : List<PaymentItem>,
    val totalPrice: Double
)
data class PaymentItem(
    val amount: Double,
    val product: PaymentProduct,
    val quantity: Int
)
data class PaymentProduct(
    val productId: String,
    val name: String,
    val price: Double
)