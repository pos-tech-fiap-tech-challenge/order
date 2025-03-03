package br.com.techchallenge.order_microsservice.customers.controller

import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerAlreadyExistsException
import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionCustomerHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException::class)
    fun handleNotFoundExceptions(ex: RuntimeException ): ResponseEntity<String> =
        ResponseEntity<String>(ex.message, HttpStatus.NOT_FOUND)
    @ResponseStatus(HttpStatus.CONFLICT)

    @ExceptionHandler(CustomerAlreadyExistsException::class)
    fun handleConflictExceptions(ex: RuntimeException ): ResponseEntity<String> =
        ResponseEntity<String>(ex.message, HttpStatus.CONFLICT)
}