package br.com.techchallenge.order_microsservice.order.adapters.repository.order

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import java.util.*


interface OrderRepositoryPort {
    fun saveOrder(order: Order)
    fun getOrderProgress(orderId: UUID): OrderProgress?
    fun listOrderByProgressAndCustomer( progress: OrderProgress, customer: Customer): List<Order>?
    fun updateOrderProgress(progress: OrderProgress, orderId: UUID)
}