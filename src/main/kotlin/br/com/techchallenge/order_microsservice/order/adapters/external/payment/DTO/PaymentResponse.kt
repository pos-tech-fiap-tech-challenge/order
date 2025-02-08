package br.com.techchallenge.order_microsservice.order.adapters.external.payment.DTO

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.util.*
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentResponse (
    val paymentId: UUID,
    val qrCodeData: ByteArray
)