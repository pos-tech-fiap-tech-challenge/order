package br.com.techchallenge.order_microsservice.order.controller

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.controller.request.OrderRequest
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import br.com.techchallenge.order_microsservice.order.core.usecase.GetOrderService
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.CreateOrderUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.ListOrderUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdateOrderProgressUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdatePaymentOrderUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

@ExtendWith(MockitoExtension::class)
class OrderControllerTest {

    @Mock
    private lateinit var createOrderUseCase: CreateOrderUseCase

    @Mock
    private lateinit var getOrderService: GetOrderService

    @Mock
    private lateinit var listOrderUseCase: ListOrderUseCase

    @Mock
    private lateinit var updateOrderProgressUseCase: UpdateOrderProgressUseCase

    @Mock
    private lateinit var updatePaymentOrderUseCase: UpdatePaymentOrderUseCase

    @InjectMocks
    private lateinit var orderController: OrderController

    @Test
    fun `should create order successfully`() {
        val orderItemRequest = OrderRequest.OrderItemRequest(UUID.randomUUID().toString(), 2)
        val items = listOf(Order.OrderItem(UUID.randomUUID(), 2.0, UUID.randomUUID(), "product1", 2))
        val customerId = UUID.randomUUID().toString()
        val orderRequest = OrderRequest( customerId, listOf(orderItemRequest) )
        val byteArray = ByteArray(0)
        val customer = Customer(
            UUID.fromString(customerId), "John Doe", "gmail@gmail.com", "12345678900"
        )
        val order = Order(progress = OrderProgress.IN_PREPARATION, customer= customer, items= items)

        whenever(createOrderUseCase.requestOrder(orderRequest)).thenReturn(byteArray)

        val response: ResponseEntity<ByteArray> = orderController.createOrder(orderRequest)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(response.body, byteArray)
    }
    @Test
    fun `should get order progress by ID successfully`() {
        val orderId = UUID.randomUUID()
        val orderProgress = OrderProgress.IN_PREPARATION
        whenever(getOrderService.getOrderProgress(orderId)).thenReturn(orderProgress)

        val response: ResponseEntity<OrderProgress> = orderController.getOrderProgress(orderId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(orderProgress, response.body)
    }

    @Test
    fun `should update order progress successfully`() {
        val orderId = UUID.randomUUID()
        val orderProgress = OrderProgress.IN_PREPARATION

        val response: ResponseEntity<Void> = orderController.updateOrderProgress(orderProgress, orderId)

        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun `should update order payment successfully`() {
        val orderId = UUID.randomUUID()
        val paymentProgress = PaymentProgress.APPROVED

        val response: ResponseEntity<String> = orderController.updateOrderPayment(orderId, paymentProgress)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Status do pagamento atualizado com sucesso para $paymentProgress", response.body)
    }

    @Test
    fun `should list orders by progress and customer successfully`() {
        val orderProgress = OrderProgress.IN_PREPARATION
        val cpf = "12345678900"
        val orders = listOf<Order>()
        whenever(listOrderUseCase.listOrderByProgressAndCustomer(orderProgress, cpf)).thenReturn(orders)

        val response: ResponseEntity<List<Order>> = orderController.listOrderByProgressAndCustomer(orderProgress, cpf)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(orders, response.body)
    }

    @Test
    fun `should get unfinished orders successfully`() {
        val orders = listOf<Order>()
        whenever(listOrderUseCase.listOrderByProgress(listOf(OrderProgress.READY, OrderProgress.IN_PREPARATION, OrderProgress.RECEIVED))).thenReturn(orders)

        val response: List<Order> = orderController.getUnfinishedOrders()

        assertEquals(orders, response)
    }
}