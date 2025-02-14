package br.com.techchallenge.order_microsservice.customers.adapters.repository.entities

import org.springframework.data.mongodb.core.mapping.Document

@Document("customerDocument")
data class CustomerEntity(
    val id: String,
    val name: String,
    val emailAddress: String,
    val cpf: String
)