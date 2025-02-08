package br.com.techchallenge.order_microsservice.order.core.entities

enum class OrderProgress(status: String ) {
    RECEIVED("RECEBIDO"), IN_PREPARATION("EM PREPARAÇÃO"),READY("PRONTO"),FINISHED("FINALIZADO")
}