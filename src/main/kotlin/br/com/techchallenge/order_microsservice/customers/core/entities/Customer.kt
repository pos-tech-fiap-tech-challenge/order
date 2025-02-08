package br.com.techchallenge.order_microsservice.customers.core.entities

import java.util.UUID


data class Customer(
    var id: UUID,
    var name:String,
    var emailAddress: String,
    var cpf:String
)