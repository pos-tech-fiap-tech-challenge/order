package br.com.techchallenge.order_microsservice.order.core.entities

import java.util.UUID

class Payment (
    var  paymentQrCodeData: ByteArray?,
    var paymentProgress: PaymentProgress = PaymentProgress.OPPENED
)