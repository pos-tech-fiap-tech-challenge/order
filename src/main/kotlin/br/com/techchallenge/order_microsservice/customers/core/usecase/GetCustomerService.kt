package br.com.techchallenge.order_microsservice.customers.core.usecase

import br.com.techchallenge.order_microsservice.customers.adapters.repository.CustomerAdapter
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerNotFoundException
import br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces.GetCustomerIdUseCase
import org.springframework.stereotype.Service

@Service
class GetCustomerService(private val customerAdapter: CustomerAdapter): GetCustomerIdUseCase {
    override fun getCustomerByCpf(cpf: String): Customer =
        customerAdapter.getCustomerByCpf(cpf)?:throw CustomerNotFoundException("Não existe customer com o cpf: $cpf")


    override fun getCustomerById(customerId: String): Customer =
        customerAdapter.getCustomerById(customerId) ?: throw CustomerNotFoundException("Não existe customer com o customerId: $customerId")

    fun cpfExists(cpf: String):Boolean =
        customerAdapter.getCustomerByCpf(cpf) != null

    fun customerIdExist(customerId: String):Boolean =
        customerAdapter.getCustomerById(customerId) != null
}