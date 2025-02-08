package br.com.techchallenge.order_microsservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class OrderMicrosserviceApplication

fun main(args: Array<String>) {
	runApplication<OrderMicrosserviceApplication>(*args)
}
