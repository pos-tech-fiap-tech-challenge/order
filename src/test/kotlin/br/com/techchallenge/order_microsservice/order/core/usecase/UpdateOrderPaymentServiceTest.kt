package br.com.techchallenge.order_microsservice.order.core.usecase


import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentData
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.GetOrderUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class UpdateOrderPaymentServiceTest {

    @Mock
    private lateinit var orderRepositoryPort: OrderRepositoryPort

    @Mock
    private lateinit var getOrderUseCase: GetOrderUseCase

    @InjectMocks
    private lateinit var updateOrderPaymentService: UpdateOrderPaymentService

    @Test
    fun `should update order payment successfully`() {
        val orderId = UUID.randomUUID()
        val paymentProgress = PaymentProgress.APPROVED
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900")
        val order = Order(orderId, customer = customer, items = listOf(), progress = OrderProgress.IN_PREPARATION, payment = PaymentData(paymentProgress = PaymentProgress.APPROVED , null))

        whenever(getOrderUseCase.getOrder(orderId)).thenReturn(order)

        updateOrderPaymentService.updatePayment(orderId, paymentProgress)

        verify(orderRepositoryPort).saveOrder(order)
        assertEquals(PaymentProgress.APPROVED, order.payment?.paymentProgress)
    }

    @Test
    fun `should throw exception when order not found`() {
        val orderId = UUID.randomUUID()
        val paymentProgress = PaymentProgress.APPROVED

        whenever(getOrderUseCase.getOrder(orderId)).thenThrow(OrderNotFoundException("Order with id $orderId not found"))

        val exception = assertThrows(OrderNotFoundException::class.java) {
            updateOrderPaymentService.updatePayment(orderId, paymentProgress)
        }

        assertEquals("Order with id $orderId not found", exception.message)
    }
}