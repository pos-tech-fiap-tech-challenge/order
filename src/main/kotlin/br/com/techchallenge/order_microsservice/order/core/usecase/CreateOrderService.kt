package br.com.techchallenge.order_microsservice.order.core.usecase

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces.GetCustomerIdUseCase
import br.com.techchallenge.order_microsservice.order.adapters.external.payment.PaymentPort
import br.com.techchallenge.order_microsservice.order.adapters.external.product.ProductPort
import br.com.techchallenge.order_microsservice.order.adapters.repository.OrderRepositoryPort
import br.com.techchallenge.order_microsservice.order.controller.request.OrderRequest
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.Payment
import br.com.techchallenge.order_microsservice.order.core.entities.Product
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.CreateOrderUseCase
import com.google.zxing.WriterException
import org.springframework.stereotype.Service
import java.io.IOException


@Service
class CreateOrderService(
    private val paymentIntegrationAdapter: PaymentPort,
    private val customerPort: GetCustomerIdUseCase,
    private val orderPort: OrderRepositoryPort,
    private val productAdapter: ProductPort
) : CreateOrderUseCase {
    override fun requestOrder(orderRequest: OrderRequest): ByteArray {
        val customer: Customer = customerPort.getCustomerById(orderRequest.customerId)
        val listOfOrderItems: List<Order.OrderItem> = orderRequest.items.map{item -> generateOrderItem(item.productId, item.qtd)  }
        val order = Order(customer=customer, items=listOfOrderItems)
        val payment: Payment = paymentIntegrationAdapter.createPayment(order)
        order.setPayment(payment)
        orderPort.saveOrder(order)
        return payment.paymentQrCodeData!!
    }

    private fun generateOrderItem(productId: String, qtd: Int): Order.OrderItem {
        val product: Product = productAdapter.getProduct(productId)
        return product.toOrderItem(qtd);
    }
}
