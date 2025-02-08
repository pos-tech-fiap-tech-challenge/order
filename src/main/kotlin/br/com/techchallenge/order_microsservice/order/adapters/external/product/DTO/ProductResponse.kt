package br.com.techchallenge.order_microsservice.order.adapters.external.product.DTO


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProductResponse(
    val productId:  UUID,
    var category: String,
    val name: String,
    val price: Double
)
