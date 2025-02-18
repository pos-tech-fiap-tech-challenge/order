package br.com.techchallenge.order_microsservice.order.core.usecase

import br.com.techchallenge.order_microsservice.customers.adapters.repository.entities.CustomerEntity
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces.GetCustomerIdUseCase
import br.com.techchallenge.order_microsservice.order.adapters.external.payment.PaymentPort
import br.com.techchallenge.order_microsservice.order.adapters.external.product.ProductPort
import br.com.techchallenge.order_microsservice.order.controller.request.OrderRequest

import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepository
import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.adapters.repository.entities.OrderEntity
import br.com.techchallenge.order_microsservice.order.adapters.repository.entities.OrderItemEntity
import br.com.techchallenge.order_microsservice.order.core.entities.*
import com.google.zxing.WriterException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class CreateOrderServiceTest {


    @InjectMocks
    private lateinit var createOrderService: CreateOrderService

    @Mock
    private lateinit var customerPort: GetCustomerIdUseCase

    @Mock
    private lateinit var paymentIntegrationAdapter: PaymentPort

    @Mock
    private lateinit var productAdapter: ProductPort

    @Mock
    private lateinit var orderRepositoryPort: OrderRepositoryPort

    @Test
    fun `should create order successfully`() {
        val orderItemRequest = OrderRequest.OrderItemRequest(UUID.randomUUID().toString(), 2)
        val customerId = UUID.randomUUID().toString()
        val orderRequest = OrderRequest(customerId, listOf(orderItemRequest))
        val customer = Customer(UUID.fromString(customerId), "John Doe", "john.doe@example.com", "12345678900")
        val product = Product(UUID.fromString(orderItemRequest.productId), 10.0, "Product", ProductCategory.ACOMPANHAMENTO)
        val payment = Payment( ByteArray(0) ,PaymentProgress.OPPENED)


        whenever(productAdapter.getProduct(orderItemRequest.productId)).thenReturn(product)
        whenever(customerPort.getCustomerById(customerId)).thenReturn(customer)
        whenever(paymentIntegrationAdapter.createPayment(any())).thenReturn(payment)

        val result = createOrderService.requestOrder(orderRequest)
        verify(orderRepositoryPort).saveOrder(any())
        assertEquals(result,payment.paymentQrCodeData)
    }
}