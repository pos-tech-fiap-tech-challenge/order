package br.com.techchallenge.order_microsservice.customers.core.usecase

import br.com.techchallenge.order_microsservice.customers.adapters.repository.CustomerAdapter
import br.com.techchallenge.order_microsservice.customers.controller.DTO.CustomerRequest
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerAlreadyExistsException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class SaveCustomerServiceTest {

    @Mock
    private lateinit var getCustomerService: GetCustomerService

    @Mock
    private lateinit var customerAdapter: CustomerAdapter

    @InjectMocks
    private lateinit var saveCustomerService: SaveCustomerService

    @Test
    fun `should save customer successfully`() {
        val customerRequest = CustomerRequest( "gmail@gmail.com", "John Doe", "12345678900" )
        val customer = Customer(UUID.randomUUID(), "John Doe", "gmail@gmail.com", "12345678900")
        whenever(getCustomerService.cpfExists(customerRequest.cpf)).thenReturn(false)
        whenever(customerAdapter.saveCustomer(any())).thenReturn(customer)

        val result = saveCustomerService.saveCustomer(customerRequest)

        assertNotNull(result)
        assertEquals(customerRequest.name, result.name)
        assertEquals(customerRequest.cpf, result.cpf)
        assertEquals(customerRequest.emailAddress, result.emailAddress)
    }

    @Test
    fun `should throw CustomerAlreadyExistsException when customer with same CPF exists`() {
        val customerRequest = CustomerRequest("John Doe", "12345678900", "gmail@gmail.com")
        val existingCustomer = Customer(UUID.randomUUID(), "John Doe", "gmail@gmail.com", "12345678900")
        whenever(getCustomerService.cpfExists(customerRequest.cpf)).thenReturn(true)

        val exception = assertThrows<CustomerAlreadyExistsException> {
            saveCustomerService.saveCustomer(customerRequest)
        }

        assertEquals("Customer com cpf: ${customerRequest.cpf} já existe!", exception.message)
    }
}