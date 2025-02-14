package br.com.techchallenge.order_microsservice.order.controller

import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import org.springframework.http.ResponseEntity
import java.util.*

interface OrderProgressApi {
    fun updateOrderProgress(orderProgress: OrderProgress, orderSnackId: UUID): ResponseEntity<Void>
    fun getOrderProgress(orderId: UUID): ResponseEntity<OrderProgress>
    fun listOrderByProgressAndCustomer(progress: OrderProgress, cpf: String): ResponseEntity<List<Order>>
    fun updateOrderPayment(orderId: UUID , paymentProgress: PaymentProgress): ResponseEntity<String>
    fun getUnfinishedOrders(): List<Order>
}