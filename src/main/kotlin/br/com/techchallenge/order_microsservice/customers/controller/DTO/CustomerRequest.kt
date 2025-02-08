package br.com.techchallenge.order_microsservice.customers.controller.DTO

class CustomerRequest(
    var emailAddress: String,
    val name: String,
    val cpf: String
)