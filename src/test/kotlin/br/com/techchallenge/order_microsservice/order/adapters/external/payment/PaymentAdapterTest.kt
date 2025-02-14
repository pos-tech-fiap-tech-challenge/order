package br.com.techchallenge.order_microsservice.order.adapters.external.payment

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class PaymentAdapterTest {

    @Mock
    private lateinit var paymentClient: PaymentConfig

    @InjectMocks
    private lateinit var paymentAdapter: PaymentAdapter

    @Test
    fun `should create payment successfully`() {
        val orderId = UUID.randomUUID()
        val orderItem = Order.OrderItem(UUID.randomUUID(), 2.0, UUID.randomUUID(), "product1", 2)
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900")
        val order = Order(orderId, items = listOf(orderItem), progress = OrderProgress.RECEIVED, customer = customer)
        val paymentQrCodeData = ByteArray(0)

        whenever(paymentClient.createPayment(order.toPaymentOrderRequest())).thenReturn(paymentQrCodeData)

        val payment = paymentAdapter.createPayment(order)

        assertNotNull(payment)
        assertEquals(paymentQrCodeData, payment.paymentQrCodeData)
    }
}