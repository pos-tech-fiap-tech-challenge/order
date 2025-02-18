package br.com.techchallenge.order_microsservice.order.core.usecase

import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.GetOrderUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdatePaymentOrderUseCase
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UpdateOrderPaymentService(
    private val orderService  : GetOrderUseCase,
    private val orderReposityAdapter: OrderRepositoryPort
):UpdatePaymentOrderUseCase {
    override fun updatePayment(id: UUID, paymentProgress: PaymentProgress) {
        val order: Order =orderService.getOrder(id)
        order.payment!!.paymentProgress = paymentProgress
        order.payment!!.paymentDate = LocalDateTime.now()
        orderReposityAdapter.saveOrder(order)

    }
}