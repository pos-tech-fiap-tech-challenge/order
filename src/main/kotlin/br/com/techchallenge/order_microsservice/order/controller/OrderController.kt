package br.com.techchallenge.order_microsservice.order.controller

import br.com.techchallenge.order_microsservice.order.controller.request.OrderRequest
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress
import br.com.techchallenge.order_microsservice.order.core.usecase.GetOrderService
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.CreateOrderUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.ListOrderUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdateOrderProgressUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdatePaymentOrderUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(OrderController.BASE_URL)
class OrderController(
    private val serviceCreateOrderUseCase: CreateOrderUseCase,
    private val serviceGetOrderProgressUseCase: GetOrderService,
    private val orderUseCase: ListOrderUseCase,
    private val serviceUpdateOrderProgressUseCase: UpdateOrderProgressUseCase,
    private val serviceUpdatePayment: UpdatePaymentOrderUseCase
) : OrderProgressApi {

    @GetMapping("/progress/")
    override fun listOrderByProgressAndCustomer(
        @RequestParam(required = false) progress: OrderProgress,
        @RequestParam(required = false) cpf: String
    ): ResponseEntity<List<Order>> {
        val orderList: List<Order> = orderUseCase.listOrderByProgressAndCustomer(progress, cpf)
        return ResponseEntity.ok(orderList)
    }

    @PostMapping
    fun createOrder(@RequestBody orderRequest: OrderRequest): ResponseEntity<ByteArray> {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(serviceCreateOrderUseCase.requestOrder(orderRequest))
    }

    @PutMapping("/{orderId}")
    override fun updateOrderProgress(
        @RequestBody orderProgress: OrderProgress,
        @PathVariable("orderId") orderId: UUID
    ): ResponseEntity<Void> {
        serviceUpdateOrderProgressUseCase.updateProgress(orderProgress, orderId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/{orderId}")
    override fun getOrderProgress(@PathVariable("orderId") orderId: UUID): ResponseEntity<OrderProgress> {
        val result: OrderProgress = serviceGetOrderProgressUseCase.getOrderProgress(orderId)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/payments/{orderId}")
    override fun updateOrderPayment(@PathVariable("orderId") orderId: UUID, @RequestBody paymentProgress: PaymentProgress ):  ResponseEntity<String>{
        serviceUpdatePayment.updatePayment(orderId, paymentProgress)
        return ResponseEntity("Status do pagamento atualizado com sucesso para $paymentProgress", HttpStatus.OK )
    }

    companion object {
        const val BASE_URL = "/lanchonete/orders"
    }

    @GetMapping
    override fun getUnfinishedOrders(): List<Order>{
        return orderUseCase.listOrderByProgress(
            listOf(OrderProgress.READY,OrderProgress.IN_PREPARATION,OrderProgress.RECEIVED)
        )
    }
}