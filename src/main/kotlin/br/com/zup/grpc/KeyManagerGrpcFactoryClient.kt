package br.com.zup.grpc

import br.com.zup.KeyManagerBuscaChavePorIDGrpcServiceGrpc
import br.com.zup.KeyManagerCadastraGrpcServiceGrpc
import br.com.zup.KeyManagerExcluiGrpcServiceGrpc
import br.com.zup.KeyManagerListaChavesGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactoryClient(@GrpcChannel(value = "keyManager") val channel: ManagedChannel) {

    @Singleton
    fun cadastraChave() = KeyManagerCadastraGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun excluiChave() = KeyManagerExcluiGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun buscaPorChaveID() = KeyManagerBuscaChavePorIDGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChaves() = KeyManagerListaChavesGrpcServiceGrpc.newBlockingStub(channel)

}