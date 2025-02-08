package br.com.techchallenge.order_microsservice.customers.core.usecase.converter

import br.com.techchallenge.order_microsservice.customers.controller.DTO.CustomerRequest
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import java.util.*

fun CustomerRequest.toDomain(): Customer =
    with(this) {
        Customer(
            id = UUID.randomUUID(),
            name= name,
            cpf = cpf,
            emailAddress = emailAddress
        )
    }