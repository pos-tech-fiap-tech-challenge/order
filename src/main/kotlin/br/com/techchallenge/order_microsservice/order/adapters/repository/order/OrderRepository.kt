package br.com.techchallenge.order_microsservice.order.adapters.repository.order

import br.com.techchallenge.order_microsservice.order.adapters.repository.order.entities.OrderEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: MongoRepository<OrderEntity, String>{
    fun findByCustomerAndProgress(id: String, progress: String ): List<OrderEntity>
}