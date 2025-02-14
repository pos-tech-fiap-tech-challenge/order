package br.com.techchallenge.order_microsservice.customers.adapters.repository

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.adapters.repository.converter.toDomain
import br.com.techchallenge.order_microsservice.customers.adapters.repository.converter.toEntity
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CustomerAdapter(
    val customerRepository : CustomerRepository
): CustomerRepositoryPort
 {
    override fun getCustomerById(customerId: String): Customer? {
        return customerRepository.findById(customerId).getOrNull()?.toDomain()
    }
    override fun getCustomerByCpf(cpf: String): Customer? {
        return customerRepository.findByCpf(cpf)?.toDomain()
    }

     override fun saveCustomer(customer: Customer):Customer {
         val customerEntity =  customer.toEntity()
         return customerRepository.save(customerEntity).toDomain()
     }

 }