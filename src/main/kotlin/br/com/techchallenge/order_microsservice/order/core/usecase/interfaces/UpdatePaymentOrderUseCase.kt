package br.com.techchallenge.order_microsservice.order.core.usecase.interfaces

import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import java.util.*

interface UpdatePaymentOrderUseCase {
    fun updatePayment(id: UUID, paymentProgress: PaymentProgress )
}