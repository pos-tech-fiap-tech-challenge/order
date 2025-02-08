package br.com.techchallenge.order_microsservice.order.adapters.repository.order

import br.com.techchallenge.order_microsservice.customers.adapters.repository.CustomerRepositoryPort
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerNotFoundException
import br.com.techchallenge.order_microsservice.order.adapters.repository.order.converter.toDomain
import br.com.techchallenge.order_microsservice.order.adapters.repository.order.converter.toEntity
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Service
class OrderReposityAdapter(
    private val orderRepositoryPort: OrderRepository,
    private val customerRepositoryPort: CustomerRepositoryPort
): OrderRepositoryPort {
    override fun saveOrder(order: Order) {
        orderRepositoryPort.save(order.toEntity())
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
            listOrderEntity.map{it.toDomain(customer)}
    }

    override fun updateOrderProgress(progress: OrderProgress, orderId: UUID) {
        val order = orderRepositoryPort.findById(orderId.toString()).getOrElse {
            throw  OrderNotFoundException("Nenhum pedido encontrado para esse ID")
        }
        order.progress = progress.name
        orderRepositoryPort.save(order)
    }
}