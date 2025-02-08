package br.com.techchallenge.order_microsservice.order.adapters.external.product

import br.com.techchallenge.order_microsservice.order.adapters.external.product.DTO.ProductResponse

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("product-service", url = "\${product.service.url}")
interface ProductConfig {
    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: String): ProductResponse
}