package br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer


interface GetCustomerIdUseCase {
    fun getCustomerByCpf(cpf: String): Customer
    fun getCustomerById(customerId: String ): Customer
}