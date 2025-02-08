package br.com.techchallenge.order_microsservice.order.controller

import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import org.springframework.http.ResponseEntity
import java.util.*

interface OrderProgressApi {
    fun updateOrderSnackProgress(orderProgress: OrderProgress, orderSnackId: UUID): ResponseEntity<Void>
    fun getOrderSnackProgress(orderSnackId: UUID): ResponseEntity<OrderProgress>
    fun listOrderByProgressAndCustomer(progress: OrderProgress, cpf: String): ResponseEntity<List<Order>>
}