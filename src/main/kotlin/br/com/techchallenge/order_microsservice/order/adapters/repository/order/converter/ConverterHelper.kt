package br.com.techchallenge.order_microsservice.order.adapters.repository.order.converter

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.adapters.repository.order.entities.OrderEntity
import br.com.techchallenge.order_microsservice.order.adapters.repository.order.entities.OrderItemEntity
import br.com.techchallenge.order_microsservice.order.core.entities.*
import java.util.*

fun OrderEntity.toDomain(customer: Customer): Order =
    with(this){
        Order(
            orderId = UUID.fromString(id),
            customer = customer,
            items =  orderItems.map{it -> it.toDomain()},
            createdAt = createdAt,
            progress = OrderProgress.valueOf(progress),
            paymentProgress = PaymentProgress.valueOf(paymentProgress)
        )
    }

fun OrderItemEntity.toDomain() :Order.OrderItem =
    with(this){
        Order.OrderItem(
            orderItemId = UUID.fromString(orderItemId),
            amountPerItem = amountPerItem,
            productId = UUID.fromString(productId),
            productName =  productName,
            quantity = quantity
        )
    }



fun Order.toEntity(): OrderEntity =
    with(this){
        OrderEntity(
            id = orderId.toString(),
            customer = customer.id.toString(),
            orderItems =  items.map{it.toEntity()},
            createdAt = createdAt,
            progress = progress.name,
            paymentProgress = paymentProgress?.name ?: PaymentProgress.OPPENED.name
        )
    }

fun Order.OrderItem.toEntity()=
    with(this) {
        OrderItemEntity(
            orderItemId = orderItemId.toString(),
            amountPerItem = amountPerItem,
            productId= productId.toString(),
            productName = productName,
            quantity = quantity
        )
    }




