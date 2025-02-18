package br.com.techchallenge.order_microsservice.order.core.usecase


import br.com.techchallenge.order_microsservice.customers.core.usecase.GetCustomerService
import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.ListOrderUseCase
import org.springframework.cglib.core.Local
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ListOrderService(
    private val orderPort: OrderRepositoryPort,
    private val getCustomerService: GetCustomerService
) : ListOrderUseCase {
    override fun listOrderByProgressAndCustomer(progress: OrderProgress, cpf: String): List<Order> {
        val customer = getCustomerService.getCustomerByCpf(cpf)
        val orders = orderPort.listOrderByProgressAndCustomer(progress, customer)
        orders?.sortedBy{it.progress.ordinal}?: throw OrderNotFoundException("Nenhum pedido encontrado para o cpf: $cpf")
        return orders
    }

    override fun listOrderByProgress(progressStatus: List<OrderProgress>): List<Order> {
        val order:List<Order> = orderPort.listOrderByProgress(progressStatus)
        val newOrder = order.sortedWith(
            compareBy<Order, LocalDateTime?>(nullsLast()) { it.productionStartDate }.thenComparing(
                compareBy<Order, LocalDateTime?>(nullsLast()) { it.createdAt }
            )
        )
        return newOrder
    }
}