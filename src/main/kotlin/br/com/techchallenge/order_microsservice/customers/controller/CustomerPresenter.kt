package br.com.techchallenge.order_microsservice.customers.controller

import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import org.springframework.http.ResponseEntity

interface CustomerPresenter {
    fun generateCustomerCreatedResponse(customer: Customer): ResponseEntity<Void>
}
