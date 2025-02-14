package br.com.techchallenge.order_microsservice.customers.adapters

import br.com.techchallenge.order_microsservice.customers.adapters.repository.CustomerAdapter
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.adapters.repository.CustomerRepository
import br.com.techchallenge.order_microsservice.customers.adapters.repository.entities.CustomerEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class CustomerAdapterTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository

    @InjectMocks
    private lateinit var customerAdapter: CustomerAdapter

    @Test
    fun `should get customer by id`() {
        val customerId = UUID.randomUUID()
        val customer = Customer(customerId, "John Doe", "gmail@gmail.com", "12345678900")
        val expectedRepositoryResponse = CustomerEntity(customerId.toString(), "John Doe", "gmail@gmail.com","12345678900" )
        whenever(customerRepository.findById(customerId.toString())).thenReturn(Optional.of(expectedRepositoryResponse))

        val result = customerAdapter.getCustomerById(customerId.toString())

        assertNotNull(result)
        assertEquals(result , customer)
    }

    @Test
    fun `should get customer by cpf`() {
        val cpf = "12345678900"
        val customer = Customer(UUID.randomUUID(), "John Doe", "gmail@gmail.com", cpf)
        val expectedRepositoryResponse = CustomerEntity(customer.id.toString(), "John Doe", "gmail@gmail.com","12345678900" )
        whenever(customerRepository.findByCpf(cpf)).thenReturn(expectedRepositoryResponse)

        val result = customerAdapter.getCustomerByCpf(cpf)

        assertNotNull(result)
        assertEquals(result , customer)
    }

    @Test
    fun `should save customer`() {
        val customer = Customer(UUID.randomUUID(), "John Doe", "gmail@gmail.com", "12345678900")
        val expectedRepositoryResponse = CustomerEntity(customer.id.toString(), "John Doe", "gmail@gmail.com","12345678900" )
        whenever(customerRepository.save(expectedRepositoryResponse)).thenReturn(expectedRepositoryResponse)

        val result = customerAdapter.saveCustomer(customer)

        assertNotNull(result)
        assertEquals(customer, result)
        verify(customerRepository,times(1)).save(expectedRepositoryResponse)
    }


}