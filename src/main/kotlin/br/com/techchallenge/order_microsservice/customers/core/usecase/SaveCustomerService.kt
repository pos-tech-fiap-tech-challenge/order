package br.com.techchallenge.order_microsservice.customers.core.usecase

import br.com.techchallenge.order_microsservice.customers.adapters.repository.CustomerRepositoryPort
import br.com.techchallenge.order_microsservice.customers.controller.DTO.CustomerRequest
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerAlreadyExistsException
import br.com.techchallenge.order_microsservice.customers.core.usecase.converter.toDomain
import br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces.SaveCustomerUseCase
import org.springframework.stereotype.Service

@Service
class SaveCustomerService(
    val getCustomerService: GetCustomerService,
    val customerAdapter: CustomerRepositoryPort
): SaveCustomerUseCase {
    override fun saveCustomer(customerRequest: CustomerRequest): Customer {
        if(getCustomerService.cpfExists(customerRequest.cpf))
            throw CustomerAlreadyExistsException("Customer com cpf: ${customerRequest.cpf} já existe!");
        val customer = customerRequest.toDomain()
        return customerAdapter.saveCustomer(customer)
    }
}