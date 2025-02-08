package br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces

import br.com.techchallenge.order_microsservice.customers.controller.DTO.CustomerRequest
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer

interface SaveCustomerUseCase {
    fun saveCustomer(customerRequest : CustomerRequest): Customer
}