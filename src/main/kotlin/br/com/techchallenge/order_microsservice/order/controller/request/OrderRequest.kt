package br.com.techchallenge.order_microsservice.order.controller.request

data class OrderRequest(
    val customerId: String,
    val items: List<OrderItemRequest>
){
    data class OrderItemRequest(
        var productId: String,
        var qtd: Int
    )
}