package br.com.techchallenge.order_microsservice.customers.adapters.repository

import br.com.techchallenge.order_microsservice.customers.adapters.repository.entities.CustomerEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : MongoRepository<CustomerEntity, String>{
    fun findByCpf(cpf:String): CustomerEntity?
}
