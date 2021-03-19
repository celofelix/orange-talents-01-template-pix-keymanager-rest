package br.com.zup

data class CadastraChaveRequest(
    val clienteId: String,
    val tipoDeChave: String,
    val chave: String,
    val tipoDeConta: String
) {


}
