package br.com.techchallenge.order_microsservice.order.core.usecase


import br.com.techchallenge.order_microsservice.customers.core.usecase.GetCustomerService
import br.com.techchallenge.order_microsservice.order.adapters.repository.order.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.ListOrderUseCase
import org.springframework.stereotype.Service

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
}