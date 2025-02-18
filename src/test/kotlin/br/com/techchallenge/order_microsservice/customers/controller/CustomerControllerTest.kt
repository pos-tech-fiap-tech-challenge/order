
package br.com.techchallenge.order_microsservice.customers.controller

import br.com.techchallenge.order_microsservice.customers.controller.DTO.CustomerRequest
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.usecase.GetCustomerService
import br.com.techchallenge.order_microsservice.customers.core.usecase.GetCustomerServiceTest
import br.com.techchallenge.order_microsservice.customers.core.usecase.SaveCustomerService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.mockito.kotlin.whenever
import java.net.URI
import java.util.*

@ExtendWith(MockitoExtension::class)
class CustomerControllerTest {

    @Mock
    private lateinit var getCustomerService: GetCustomerService

    @Mock
    private lateinit var saveCustomerService: SaveCustomerService

    @Mock
    private lateinit var customerPresenterRest: CustomerPresenterRest

    @InjectMocks
    private lateinit var customerController: CustomerController


    @Test
    fun `should return customer by id`() {
        val customerId = UUID.randomUUID()
        val cpf = "12345678900"
        val customer = Customer(customerId, "John Doe", "gmail@gmail.com", "12345678900",)
        whenever(getCustomerService.getCustomerByCpf(cpf)).thenReturn(customer)

        val response: ResponseEntity<Customer> = customerController.getCustomerByCpf(cpf)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(customer, response.body)
    }


    @Test
    fun `should save customer`() {
        val customerId = UUID.randomUUID()
        val customerRequest = CustomerRequest("John Doe", "12345678900", "gmail@gmail.com")
        val customer = Customer(customerId, "John Doe", "gmail@gmail.com", "12345678900")
        whenever(customerPresenterRest.generateCustomerCreatedResponse(customer)).thenReturn(ResponseEntity.created(
            URI("id")
        ).build())
        whenever(saveCustomerService.saveCustomer(customerRequest)).thenReturn(customer)
        val response: ResponseEntity<Void> = customerController.createCustomer(customerRequest)
        assertNotNull(response)
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }
}
