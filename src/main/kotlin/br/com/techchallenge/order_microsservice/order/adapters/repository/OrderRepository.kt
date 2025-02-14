package br.com.techchallenge.order_microsservice.order.adapters.repository

import br.com.techchallenge.order_microsservice.order.adapters.repository.entities.OrderEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: MongoRepository<OrderEntity, String>{
    fun findByCustomerAndProgress(id: String, progress: String ): List<OrderEntity>
    fun findByProgressIn(progress:List<String>): List<OrderEntity>
}