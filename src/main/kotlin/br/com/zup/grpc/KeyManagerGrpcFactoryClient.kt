package br.com.zup.grpc

import br.com.zup.*
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
open class KeyManagerGrpcFactoryClient(@GrpcChannel(value = "keyManager") val channel: ManagedChannel) {

    @Singleton
    fun cadastraChave() = KeyManagerCadastraGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun excluiChave() = KeyManagerExcluiGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun buscaID() = KeyManagerBuscaChavePorIDGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun buscaPorChave() = KeyManagerBuscaChaveGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChaves() = KeyManagerListaChavesGrpcServiceGrpc.newBlockingStub(channel)

}