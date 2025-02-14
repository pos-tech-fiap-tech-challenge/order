package br.com.techchallenge.order_microsservice.order.core.usecase.interfaces

import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import java.util.*

interface GetOrderUseCase {
    fun getOrderProgress(orderId: UUID):OrderProgress
    fun getOrder(orderId: UUID):Order
}