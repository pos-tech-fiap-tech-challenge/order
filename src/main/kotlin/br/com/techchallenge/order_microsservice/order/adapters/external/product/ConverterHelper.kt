package br.com.techchallenge.order_microsservice.order.adapters.external.product

import br.com.techchallenge.order_microsservice.order.adapters.external.product.DTO.ProductResponse
import br.com.techchallenge.order_microsservice.order.core.entities.Product
import br.com.techchallenge.order_microsservice.order.core.entities.ProductCategory

fun ProductResponse.toEntity() =
    with(this) {
        Product(
            productId = productId,
            name = name,
            price = price,
            category = ProductCategory.valueOf(category)
        )
    }