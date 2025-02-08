package br.com.techchallenge.order_microsservice.customers.adapters.repository

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer

interface CustomerRepositoryPort {
    fun getCustomerById(customerId: String ): Customer?
    fun getCustomerByCpf(cpf: String):Customer?
    fun saveCustomer(customer: Customer): Customer
}