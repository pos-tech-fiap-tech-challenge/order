package br.com.techchallenge.order_microsservice.order.core.usecase


import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.usecase.GetCustomerService
import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class ListOrderServiceTest {

    @Mock
    private lateinit var orderRepositoryPort: OrderRepositoryPort

    @Mock
    private lateinit var getCustomerService: GetCustomerService

    @InjectMocks
    private lateinit var listOrderService: ListOrderService

    @Test
    fun `should list orders by progress and customer successfully`() {
        val cpf = "12345678900"
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", cpf)
        val order = Order(
            orderId = UUID.randomUUID(),
            customer = customer,
            items = listOf(),
            progress = OrderProgress.RECEIVED
        )

        whenever(getCustomerService.getCustomerByCpf(cpf)).thenReturn(customer)
        whenever(orderRepositoryPort.listOrderByProgressAndCustomer(OrderProgress.RECEIVED, customer)).thenReturn(listOf(order))

        val result = listOrderService.listOrderByProgressAndCustomer(OrderProgress.RECEIVED, cpf)

        assertTrue(result.contains(order))
    }

    @Test
    fun `should throw exception when no orders found for customer`() {
        val cpf = "12345678900"
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", cpf)

        whenever(getCustomerService.getCustomerByCpf(cpf)).thenReturn(customer)
        whenever(orderRepositoryPort.listOrderByProgressAndCustomer(OrderProgress.RECEIVED, customer)).thenReturn(null)

        val exception = assertThrows(OrderNotFoundException::class.java) {
            listOrderService.listOrderByProgressAndCustomer(OrderProgress.RECEIVED, cpf)
        }

        assertEquals("Nenhum pedido encontrado para o cpf: $cpf", exception.message)
    }

    @Test
    fun `should list orders by progress successfully`() {
        val order = Order(
            orderId = UUID.randomUUID(),
            customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900"),
            items = listOf(),
            progress = OrderProgress.RECEIVED
        )

        whenever(orderRepositoryPort.listOrderByProgress(listOf(OrderProgress.RECEIVED))).thenReturn(listOf(order))

        val result = listOrderService.listOrderByProgress(listOf(OrderProgress.RECEIVED))

        assertTrue(result.contains(order))
    }
}