package br.com.techchallenge.order_microsservice.order.core.usecase.interfaces

import br.com.techchallenge.order_microsservice.order.controller.request.OrderRequest

interface CreateOrderUseCase {
    fun requestOrder(orderRequest: OrderRequest):ByteArray
}