package br.com.techchallenge.order_microsservice.order.core.usecase

import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.DeliveryData
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdateOrderProgressUseCase
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UpdateOrderService(
    private val orderPort: OrderRepositoryPort
): UpdateOrderProgressUseCase {
    override fun updateProgress(orderProgress: OrderProgress, orderId: UUID) {
        val order:Order = orderPort.getOrderById(orderId)?: throw OrderNotFoundException("Order com Id: $orderId não encontrado!")
        when(orderProgress){
            OrderProgress.IN_PREPARATION -> updateInPreparation(order)
            OrderProgress.READY -> updateReady(order)
            OrderProgress.FINISHED -> updateFinished(order)
            else -> {}
        }
        order.progress = orderProgress
        orderPort.saveOrder(order)
    }
    fun updateInPreparation(order:Order){
        order.productionStartDate = LocalDateTime.now()
    }
    fun updateReady(order:Order){
        order.setExpectedDeliveryDate(LocalDateTime.now().plusMinutes(30))
    }
    fun updateFinished(order: Order){
        order.setDeliveryDate(LocalDateTime.now())
    }


}