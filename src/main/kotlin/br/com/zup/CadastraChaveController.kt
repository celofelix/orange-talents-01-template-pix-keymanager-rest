package br.com.zup

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/api/cadastra/pix")
class CadastraChaveController(private val cadastraPixClient: KeyManagerCadastraGrpcServiceGrpc.KeyManagerCadastraGrpcServiceBlockingStub) {

    @Post
    fun cadastra(@Body request: CadastraChaveRequest) {

        println(request)

    }

}