package br.com.techchallenge.order_microsservice.order.controller

import br.com.techchallenge.order_microsservice.order.controller.request.OrderRequest
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.OrderProgress
import br.com.techchallenge.order_microsservice.order.core.usecase.GetOrderService
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.CreateOrderUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.ListOrderUseCase
import br.com.techchallenge.order_microsservice.order.core.usecase.interfaces.UpdateOrderProgressUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(OrderController.BASE_URL)
class OrderController(
    private val serviceCreateOrderUseCase: CreateOrderUseCase,
    private val serviceGetOrderProgressUseCase: GetOrderService,
    private val orderUseCase: ListOrderUseCase,
    private val serviceUpdateOrderProgressUseCase: UpdateOrderProgressUseCase
) : OrderProgressApi {

    @GetMapping
    override fun listOrderByProgressAndCustomer(
        @RequestParam(required = false) progress: OrderProgress,
        @RequestParam(required = false) cpf: String
    ): ResponseEntity<List<Order>> {
        val orderList: List<Order> = orderUseCase.listOrderByProgressAndCustomer(progress, cpf)
        return ResponseEntity.ok(orderList)
    }

    @PostMapping
    fun createOrder(@RequestBody orderRequest: OrderRequest): ResponseEntity<ByteArray> {
        return ResponseEntity.ok(serviceCreateOrderUseCase.requestOrder(orderRequest))
    }

    @PutMapping("/{orderSnackId}")
    override fun updateOrderSnackProgress(
        @RequestBody orderProgress: OrderProgress,
        @PathVariable("orderSnackId") orderSnackId: UUID
    ): ResponseEntity<Void> {
        serviceUpdateOrderProgressUseCase.updateProgress(orderProgress, orderSnackId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/{orderSnackId}")
    override fun getOrderSnackProgress(@PathVariable("orderSnackId") orderSnackId: UUID): ResponseEntity<OrderProgress> {
        val result: OrderProgress = serviceGetOrderProgressUseCase.getOrderProgress(orderSnackId)
        return ResponseEntity.ok(result)
    }

    companion object {
        const val BASE_URL = "/lanchonete/orders"
    }
}