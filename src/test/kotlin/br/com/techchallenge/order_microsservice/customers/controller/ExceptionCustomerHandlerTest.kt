package br.com.techchallenge.order_microsservice.customers.controller

import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerAlreadyExistsException
import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ExceptionCustomerHandlerTest {

    private val exceptionCustomerHandler = ExceptionCustomerHandler()

    @Test
    fun `should return 404 when CustomerNotFoundException is thrown`() {
        val exception = CustomerNotFoundException("Customer not found")
        val response: ResponseEntity<String> = exceptionCustomerHandler.handleNotFoundExceptions(exception)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Customer not found", response.body)
    }

    @Test
    fun `should return 409 when CustomerAlreadyExistsException is thrown`() {
        val exception = CustomerAlreadyExistsException("Customer already exists")
        val response: ResponseEntity<String> = exceptionCustomerHandler.handleConflictExceptions(exception)

        assertEquals(HttpStatus.CONFLICT, response.statusCode)
        assertEquals("Customer already exists", response.body)
    }
}