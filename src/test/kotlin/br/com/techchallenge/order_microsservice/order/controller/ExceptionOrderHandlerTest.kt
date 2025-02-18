package br.com.techchallenge.order_microsservice.order.controller

import br.com.techchallenge.order_microsservice.order.core.exception.OrderNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class ExceptionOrderHandlerTest {

    @InjectMocks
    private lateinit var exceptionOrderHandler: ExceptionOrderHandler

    @Test
    fun `should handle OrderNotFoundException`() {
        val exception = OrderNotFoundException("Order not found")
        val response: ResponseEntity<String> = exceptionOrderHandler.handleOrderNotFoundExceptions(exception)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals("Order not found", response.body)
    }

}