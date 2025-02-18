package br.com.techchallenge.order_microsservice.order.adapters.repository

import java.time.LocalDateTime

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.adapters.repository.converter.toEntity
import br.com.techchallenge.order_microsservice.order.core.entities.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class OrderRepositoryAdapterTest {

    @Mock
    private lateinit var orderRepositoryPort: OrderRepository

    @InjectMocks
    private lateinit var orderRepositoryAdapter: OrderRepositoryAdapter

    private val customer = Customer(UUID.randomUUID(), "John Doe", "john.doe@example.com", "12345678900")
    private val orderItems = listOf(Order.OrderItem(UUID.randomUUID(), 40.00 , UUID.randomUUID(), "Product 1", 2))

    @Test
    fun `should save order successfully`() {
        val order = Order(
            UUID.randomUUID(),
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = orderItems,
            delivery = DeliveryData(LocalDateTime.now(), LocalDateTime.now())
        )
        order.setPayment(Payment(ByteArray(0), PaymentProgress.APPROVED))

        val orderEntity = order.toEntity()

        whenever(orderRepositoryPort.save(orderEntity)).thenReturn(orderEntity)

        val savedOrder = orderRepositoryAdapter.saveOrder(order)

        assertEquals(order, savedOrder)
    }

    @Test
    fun `should get order progress successfully`() {
        val orderId = UUID.randomUUID()
        val order = Order(
            UUID.randomUUID(),
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = orderItems
        )
        val orderEntity = order.toEntity()

        whenever(orderRepositoryPort.findById(orderId.toString())).thenReturn(Optional.of(orderEntity))

        val progress = orderRepositoryAdapter.getOrderProgress(orderId)

        assertNotNull(progress)
        assertEquals(OrderProgress.RECEIVED, progress)
    }

    @Test
    fun `should return null when order progress not found`() {
        val orderId = UUID.randomUUID()

        whenever(orderRepositoryPort.findById(orderId.toString())).thenReturn(Optional.empty())

        val progress = orderRepositoryAdapter.getOrderProgress(orderId)

        assertNull(progress)
    }

    @Test
    fun `should list orders by progress and customer successfully`() {
        val order = Order(
            UUID.randomUUID(),
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = orderItems
        )
        val orderEntity = order.toEntity()

        whenever(orderRepositoryPort.findByCustomerAndProgress(customer.id.toString(), OrderProgress.RECEIVED.name)).thenReturn(listOf(orderEntity))

        val orders = orderRepositoryAdapter.listOrderByProgressAndCustomer(OrderProgress.RECEIVED, customer)

        assertNotNull(orders)
        assertEquals(1, orders?.size)
        assertEquals(order, orders?.get(0))
    }

    @Test
    fun `should return null when no orders found by progress and customer`() {
        whenever(orderRepositoryPort.findByCustomerAndProgress(customer.id.toString(), OrderProgress.RECEIVED.name)).thenReturn(emptyList())

        val orders = orderRepositoryAdapter.listOrderByProgressAndCustomer(OrderProgress.RECEIVED, customer)

        assertNull(orders)
    }

    @Test
    fun `should get order by id successfully`() {
        val orderId = UUID.randomUUID()
        val order = Order(
            UUID.randomUUID(),
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = orderItems
        )
        val orderEntity = order.toEntity()

        whenever(orderRepositoryPort.findById(orderId.toString())).thenReturn(Optional.of(orderEntity))

        val foundOrder = orderRepositoryAdapter.getOrderById(orderId)

        assertNotNull(foundOrder)
        assertEquals(order, foundOrder)
    }

    @Test
    fun `should return null when order not found by id`() {
        val orderId = UUID.randomUUID()

        whenever(orderRepositoryPort.findById(orderId.toString())).thenReturn(Optional.empty())

        val foundOrder = orderRepositoryAdapter.getOrderById(orderId)

        assertNull(foundOrder)
    }

    @Test
    fun `should list orders by progress successfully`() {
        val order = Order(
            UUID.randomUUID(),
            progress = OrderProgress.RECEIVED,
            LocalDateTime.now(),
            customer = customer,
            items = orderItems
        )
        val orderEntity = order.toEntity()

        whenever(orderRepositoryPort.findByProgressIn(listOf(OrderProgress.RECEIVED.name))).thenReturn(listOf(orderEntity))

        val orders = orderRepositoryAdapter.listOrderByProgress(listOf(OrderProgress.RECEIVED))

        assertNotNull(orders)
        assertEquals(1, orders.size)
        assertEquals(order, orders[0])
    }
}