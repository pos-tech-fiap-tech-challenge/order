package br.com.techchallenge.order_microsservice.order.core.usecase

import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.GetOrderUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetOrderService(
    private val orderPort : OrderRepositoryPort
) : GetOrderUseCase {
    override fun getOrderProgress(orderId: UUID): OrderProgress {

        return orderPort.getOrderProgress(orderId)?:
            throw OrderNotFoundException("Order com o id $orderId não encontrado!");
    }

    override fun getOrder(orderId: UUID): Order {
        return orderPort.getOrderById(orderId)?:
            throw OrderNotFoundException("Order com o id $orderId não encontrado!")
    }
}