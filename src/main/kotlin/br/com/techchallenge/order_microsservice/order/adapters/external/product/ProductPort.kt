package br.com.techchallenge.order_microsservice.order.adapters.external.product

import br.com.techchallenge.order_microsservice.order.core.entities.Product

interface ProductPort {
    fun getProduct(productId: String): Product
}