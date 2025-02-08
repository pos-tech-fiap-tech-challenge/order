package br.com.techchallenge.order_microsservice.order.core.usecase

import br.com.techchallenge.order_microsservice.order.adapters.repository.order.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdateOrderProgressUseCase
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateOrderService(
    private val orderPort:OrderRepositoryPort
): UpdateOrderProgressUseCase {
    override fun updateProgress(orderProgress: OrderProgress, orderId: UUID) {
        orderPort.updateOrderProgress(orderProgress, orderId)
    }
}