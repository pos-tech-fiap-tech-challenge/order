package br.com.techchallenge.order_microsservice.order.adapters.external.product


import br.com.techchallenge.order_microsservice.order.core.entities.Product
import org.springframework.stereotype.Service

@Service
class ProductAdapter(
    val productApi:ProductConfig
): ProductPort
 {
    override fun getProduct(productId: String): Product {
        return productApi.getProduct(productId).toEntity()
    }
 }