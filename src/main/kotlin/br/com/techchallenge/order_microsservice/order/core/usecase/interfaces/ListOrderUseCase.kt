package br.com.techchallenge.order_microsservice.order.core.usecase.interfaces

import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress

interface ListOrderUseCase {
    fun listOrderByProgressAndCustomer(progress: OrderProgress, cpf: String): List<Order>
}