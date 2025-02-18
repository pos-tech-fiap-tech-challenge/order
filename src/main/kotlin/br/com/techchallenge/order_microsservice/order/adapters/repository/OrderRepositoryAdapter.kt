package br.com.techchallenge.order_microsservice.order.adapters.repository

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.adapters.repository.converter.toDomain
import br.com.techchallenge.order_microsservice.order.adapters.repository.converter.toEntity
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class OrderRepositoryAdapter(
    private val orderRepositoryPort: OrderRepository,
): OrderRepositoryPort {
    override fun saveOrder(order: Order):Order {
        return orderRepositoryPort.save(order.toEntity()).toDomain()
    }

    override fun getOrderProgress(orderId: UUID): OrderProgress? {
        val order =  orderRepositoryPort.findById(orderId.toString()).getOrNull()
        return order?.progress?.let { OrderProgress.valueOf(it) }
    }

    override fun listOrderByProgressAndCustomer(  progress: OrderProgress, customer: Customer): List<Order>? {
        val listOrderEntity = orderRepositoryPort.findByCustomerAndProgress(customer.id.toString(),progress.name)
        return if (listOrderEntity.isEmpty())
            null;
        else
            listOrderEntity.map{it.toDomain()}
    }

    override fun getOrderById(orderId :UUID): Order?{
        return orderRepositoryPort.findById(orderId.toString()).getOrNull()?.toDomain()
    }

    override fun listOrderByProgress(progressStatus: List<OrderProgress>): List<Order> {
        val progressList= progressStatus.map{it.name}
        return orderRepositoryPort.findByProgressIn(progressList).map{it.toDomain()}
    }
}