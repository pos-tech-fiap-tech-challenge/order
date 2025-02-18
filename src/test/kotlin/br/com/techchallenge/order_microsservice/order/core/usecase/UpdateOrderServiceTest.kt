import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import br.com.techchallenge.order_microsservice.order.core.usecase.UpdateOrderService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class UpdateOrderServiceTest {

    @Mock
    private lateinit var orderRepositoryPort: OrderRepositoryPort

    @InjectMocks
    private lateinit var updateOrderService: UpdateOrderService

    @Test
    fun `should update order progress to IN_PREPARATION successfully`() {
        val orderId = UUID.randomUUID()
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900")
        val order = Order(
            orderId,
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = listOf()
        )

        whenever(orderRepositoryPort.getOrderById(orderId)).thenReturn(order)

        updateOrderService.updateProgress(OrderProgress.IN_PREPARATION, orderId)

        verify(orderRepositoryPort).saveOrder(order)
        assertEquals(OrderProgress.IN_PREPARATION, order.progress)
        assertNotNull(order.productionStartDate)
    }

    @Test
    fun `should throw exception when order not found`() {
        val orderId = UUID.randomUUID()

        whenever(orderRepositoryPort.getOrderById(orderId)).thenReturn(null)

        val exception = assertThrows(OrderNotFoundException::class.java) {
            updateOrderService.updateProgress(OrderProgress.IN_PREPARATION, orderId)
        }

        assertEquals("Order com Id: $orderId não encontrado!", exception.message)
    }

    @Test
    fun `should update order progress to READY successfully`() {
        val orderId = UUID.randomUUID()
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900")
        val order = Order(
            orderId,
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = listOf()
        )

        whenever(orderRepositoryPort.getOrderById(orderId)).thenReturn(order)

        updateOrderService.updateProgress(OrderProgress.READY, orderId)

        verify(orderRepositoryPort).saveOrder(order)
        assertEquals(OrderProgress.READY, order.progress)
        assertNotNull(order.delivery?.expectedDeliveryDate)
    }

    @Test
    fun `should update order progress to FINISHED successfully`() {
        val orderId = UUID.randomUUID()
        val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900")
        val order = Order(
            orderId,
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = listOf()
        )

        whenever(orderRepositoryPort.getOrderById(orderId)).thenReturn(order)

        updateOrderService.updateProgress(OrderProgress.FINISHED, orderId)

        verify(orderRepositoryPort).saveOrder(order)
        assertEquals(OrderProgress.FINISHED, order.progress)
        assertNotNull(order.delivery?.expectedDeliveryDate)
        assertNotNull(order.delivery?.deliveryDateTime)
    }
}