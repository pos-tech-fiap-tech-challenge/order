package br.com.techchallenge.order_microsservice.customers.controller

import br.com.techchallenge.order_microsservice.customers.controller.DTO.CustomerRequest
import br.com.techchallenge.order_microsservice.customers.core.entities.Customer
import br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces.GetCustomerIdUseCase
import br.com.techchallenge.order_microsservice.customers.core.usecase.interfaces.SaveCustomerUseCase
import br.com.techchallenge.order_microsservice.order.controller.OrderController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(CustomerController.BASE_URL)
class CustomerController(
    private val customerUseCase: SaveCustomerUseCase,
    private val getCustomerService: GetCustomerIdUseCase,
    private val customerPresenterRest: CustomerPresenterRest
) {
    companion object{
        const val BASE_URL = "/lanchonete/customers"
    }

    @PostMapping
    fun createCustomer(@RequestBody customer: CustomerRequest): ResponseEntity<Void> {
        val createdCustomer: Customer = customerUseCase.saveCustomer(customer)
        return customerPresenterRest.generateCustomerCreatedResponse(createdCustomer)
    }

    @GetMapping
    fun getCustomerByCpf(cpf: String): ResponseEntity<Customer> {
        val result: Customer = getCustomerService.getCustomerByCpf(cpf)
        return ResponseEntity.ok(result)
    }
}