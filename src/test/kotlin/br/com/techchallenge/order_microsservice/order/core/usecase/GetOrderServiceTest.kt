package br.com.techchallenge.order_microsservice.order.core.usecase

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
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
import java.util.*

@ExtendWith(MockitoExtension::class)
class GetOrderServiceTest {

    @Mock
    private lateinit var orderRepositoryPort: OrderRepositoryPort

    @InjectMocks
    private lateinit var getOrderService: GetOrderService

    @Test
    fun `should get order by id successfully`() {
        val orderId = UUID.randomUUID()
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900")
        val order = Order(
            orderId = orderId,
            customer = customer,
            items = listOf(),
            progress = OrderProgress.RECEIVED
        )

        whenever(orderRepositoryPort.getOrderById(orderId)).thenReturn(order)

        val result = getOrderService.getOrder(orderId)
        assertEquals(order, result)
    }

    @Test
    fun `should return Exception when order not found`() {
        val orderId = UUID.randomUUID()

        whenever(orderRepositoryPort.getOrderById(orderId)).thenReturn(null)

        val exception = assertThrows(OrderNotFoundException::class.java) {
            getOrderService.getOrder(orderId)
        }
        assertEquals("Order com o id $orderId não encontrado!", exception.message)
    }
    @Test
    fun `should get order progress by id successfully`() {
        val orderId = UUID.randomUUID()
        val orderProgress = OrderProgress.RECEIVED

        whenever(orderRepositoryPort.getOrderProgress(orderId)).thenReturn(orderProgress)

        val result = getOrderService.getOrderProgress(orderId)
        assertEquals(orderProgress, result)
    }

    @Test
    fun `should throw exception when order progress not found`() {
        val orderId = UUID.randomUUID()

        whenever(orderRepositoryPort.getOrderProgress(orderId)).thenReturn(null)

        val exception = assertThrows(OrderNotFoundException::class.java) {
            getOrderService.getOrderProgress(orderId)
        }
        assertEquals("Order com o id $orderId não encontrado!", exception.message)
    }

}