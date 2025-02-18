package br.com.techchallenge.order_microsservice.order.adapters.external.payment

import br.com.techchallenge.order_microsservice.order.adapters.external.payment.DTO.PaymentItem
import br.com.techchallenge.order_microsservice.order.adapters.external.payment.DTO.PaymentOrderRequest
import br.com.techchallenge.order_microsservice.order.adapters.external.payment.DTO.PaymentProduct
import br.com.techchallenge.order_microsservice.order.core.entities.Order
import br.com.techchallenge.order_microsservice.order.core.entities.Payment
import br.com.techchallenge.order_microsservice.order.core.entities.PaymentProgress

fun Order.toPaymentOrderRequest(): PaymentOrderRequest =
    with(this){
        PaymentOrderRequest(
            orderId = orderId.toString(),
            totalPrice = amount,
            items = items.map{it -> it.toPaymentItem()}
        )
    }
fun Order.OrderItem.toPaymentItem(): PaymentItem =
    with(this){
        PaymentItem(
            amount = quantity * amountPerItem,
            product = toPaymentProduct(),
            quantity = quantity
        )
    }

fun Order.OrderItem.toPaymentProduct() =
    PaymentProduct(
        productId = productId.toString(),
        name = productName,
        price = amountPerItem
    )
