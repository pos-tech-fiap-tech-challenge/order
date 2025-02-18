package br.com.techchallenge.order_microsservice.order.adapters.repository.converter

import br.com.techchallenge.order_microsservice.customers.adapters.repository.converter.toDomain
import br.com.techchallenge.order_microsservice.customers.adapters.repository.converter.toEntity
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.order.adapters.repository.entities.DeliveryEntity
import br.com.techchallenge.order_microsservice.order.adapters.repository.entities.OrderEntity
import br.com.techchallenge.order_microsservice.order.adapters.repository.entities.OrderItemEntity
import br.com.techchallenge.order_microsservice.order.adapters.repository.entities.PaymentEntity
import br.com.techchallenge.order_microsservice.order.core.entities.*
import java.util.*

fun OrderEntity.toDomain(): Order =
    with(this){
        Order(
            orderId = UUID.fromString(id),
            customer = customer.toDomain(),
            items =  orderItems.map{it -> it.toDomain()},
            createdAt = createdAt,
            progress = OrderProgress.valueOf(progress),
            payment =  payment?.toDomain(),
            delivery = delivery?.toDomain(),
            productionStartDate = productionStartDate
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
            customer = customer.toEntity(),
            orderItems =  items.map{it.toEntity()},
            createdAt = createdAt,
            progress = progress.name,
            payment = payment?.toEntity(),
            delivery = delivery?.toEntity(),
            productionStartDate = productionStartDate
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

fun PaymentEntity.toDomain(): PaymentData =
    with(this){
        PaymentData(
            paymentProgress = paymentProgress,
            paymentDate = paymentDate
        )
    }

fun DeliveryEntity.toDomain(): DeliveryData =
    with(this){
        DeliveryData(
            deliveryDateTime = deliveryDateTime,
            expectedDeliveryDate = expectedDeliveryDate
        )
    }

fun PaymentData.toEntity(): PaymentEntity =
    with(this){
        PaymentEntity(
            paymentProgress = paymentProgress,
            paymentDate = paymentDate
        )
    }

fun DeliveryData.toEntity(): DeliveryEntity =
    with(this){
        DeliveryEntity(
            deliveryDateTime = deliveryDateTime,
            expectedDeliveryDate = expectedDeliveryDate
        )
    }

