package br.com.techchallenge.order_microsservice.customers.core.usecase

import br.com.techchallenge.order_microsservice.customers.adapters.repository.CustomerAdapter
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.exceptions.CustomerNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class GetCustomerServiceTest {

    @Mock
    private lateinit var customerAdapter: CustomerAdapter

    @InjectMocks
    private lateinit var getCustomerService: GetCustomerService

    @Test
    fun `should return customer when found by CPF`() {
        val cpf = "12345678900"
        val customer = Customer(UUID.randomUUID(), "John Doe", "gmail@gmail.com", cpf)
        whenever(customerAdapter.getCustomerByCpf(cpf)).thenReturn(customer)

        val result = getCustomerService.getCustomerByCpf(cpf)

        assertNotNull(result)
        assertEquals(customer, result)
    }

    @Test
    fun `should throw CustomerNotFoundException when customer not found by CPF`() {
        val cpf = "12345678900"
        whenever(customerAdapter.getCustomerByCpf(cpf)).thenReturn(null)

        val exception = assertThrows<CustomerNotFoundException> {
            getCustomerService.getCustomerByCpf(cpf)
        }

        assertEquals("Não existe customer com o cpf: $cpf", exception.message)
    }

    @Test
    fun `should return customer when found by ID`() {
        val customerId = UUID.randomUUID().toString()
        val customer = Customer(UUID.fromString(customerId), "John Doe", "gmail@gmail.com", "12345678900")
        whenever(customerAdapter.getCustomerById(customerId)).thenReturn(customer)

        val result = getCustomerService.getCustomerById(customerId)

        assertNotNull(result)
        assertEquals(customerId, result.id.toString())
    }

    @Test
    fun `should throw CustomerNotFoundException when customer not found by ID`() {
        val customerId = UUID.randomUUID().toString()
        whenever(customerAdapter.getCustomerById(customerId)).thenReturn(null)

        val exception = assertThrows<CustomerNotFoundException> {
            getCustomerService.getCustomerById(customerId)
        }

        assertEquals("Não existe customer com o customerId: $customerId", exception.message)
    }

    @Test
    fun `should return true if CPF exists`() {
        val cpf = "12345678900"
        val customer = Customer(UUID.randomUUID(), "John Doe", "gmail@gmail.com", cpf)
        whenever(customerAdapter.getCustomerByCpf(cpf)).thenReturn(customer)

        val result = getCustomerService.cpfExists(cpf)

        assertTrue(result)
    }

    @Test
    fun `should return false if CPF does not exist`() {
        val cpf = "12345678900"
        whenever(customerAdapter.getCustomerByCpf(cpf)).thenReturn(null)

        val result = getCustomerService.cpfExists(cpf)

        assertFalse(result)
    }

    @Test
    fun `should return true if customer ID exists`() {
        val customerId = UUID.randomUUID().toString()
        val customer = Customer(UUID.fromString(customerId), "John Doe", "gmail@gmail.com", "12345678900")
        whenever(customerAdapter.getCustomerById(customerId)).thenReturn(customer)

        val result = getCustomerService.customerIdExist(customerId)

        assertTrue(result)
    }

    @Test
    fun `should return false if customer ID does not exist`() {
        val customerId = UUID.randomUUID().toString()
        whenever(customerAdapter.getCustomerById(customerId)).thenReturn(null)

        val result = getCustomerService.customerIdExist(customerId)

        assertFalse(result)
    }
}