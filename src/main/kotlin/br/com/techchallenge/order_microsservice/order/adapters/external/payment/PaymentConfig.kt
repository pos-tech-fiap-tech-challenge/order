package br.com.techchallenge.order_microsservice.order.adapters.external.payment

import br.com.techchallenge.order_microsservice.order.adapters.external.payment.DTO.PaymentOrderRequest
import br.com.techchallenge.order_microsservice.order.adapters.external.payment.DTO.PaymentResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("payment-service",  url = "\${payment.service.url}")
interface PaymentConfig {
    @PostMapping
    fun createPayment(@RequestBody paymentOrderRequest: PaymentOrderRequest): ByteArray
}