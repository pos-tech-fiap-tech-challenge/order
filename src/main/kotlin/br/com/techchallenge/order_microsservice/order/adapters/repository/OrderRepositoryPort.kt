package br.com.techchallenge.order_microsservice.order.adapters.repository

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import java.util.*


interface OrderRepositoryPort {
    fun saveOrder(order: Order):Order
    fun getOrderProgress(orderId: UUID): OrderProgress?
    fun listOrderByProgressAndCustomer( progress: OrderProgress, customer: Customer): List<Order>?
    fun getOrderById(orderId: UUID): Order?
    fun listOrderByProgress(progressStatus: List<OrderProgress>): List<Order>
}