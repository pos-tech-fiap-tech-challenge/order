package br.com.techchallenge.order_microsservice.order.core.usecase.interfaces

import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import java.util.*

interface UpdateOrderProgressUseCase {
    fun updateProgress(orderProgress: OrderProgress, orderId: UUID)
}