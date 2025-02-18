package br.com.techchallenge.order_microsservice.order.controller

import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionOrderHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException::class)
    fun handleOrderNotFoundExceptions(ex: RuntimeException ): ResponseEntity<String> =
        ResponseEntity<String>(ex.message, HttpStatus.NOT_FOUND)
}