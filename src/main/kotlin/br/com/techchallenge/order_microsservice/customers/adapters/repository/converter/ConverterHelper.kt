package br.com.techchallenge.order_microsservice.customers.adapters.repository.converter

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.adapters.repository.entities.CustomerEntity
import java.util.*


fun CustomerEntity.toDomain() =
    with(this){
        Customer(
            id = UUID.fromString(id),
            name = name,
            emailAddress = emailAddress,
            cpf = cpf
        )
    }

fun Customer.toEntity() =
    with(this){
        CustomerEntity(
            id = id.toString(),
            cpf = cpf,
            name = name,
            emailAddress = emailAddress
        )
    }

