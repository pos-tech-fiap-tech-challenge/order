package br.com.techchallenge.order_microsservice.customers.controller

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@Service
class CustomerPresenterRest {
    fun generateCustomerCreatedResponse(customer: Customer): ResponseEntity<Void> {
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.id).toUri()
        ).build()
    }
}