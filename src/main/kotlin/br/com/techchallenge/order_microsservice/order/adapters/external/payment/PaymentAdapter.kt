package br.com.techchallenge.order_microsservice.order.adapters.external.payment

import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.Payment
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentAdapter(
    private val paymentClient: PaymentConfig
) : PaymentPort {
    override fun createPayment(order: Order): Payment {
        val image =  paymentClient.createPayment(order.toPaymentOrderRequest())
        return Payment( paymentQrCodeData = image)
    }

}