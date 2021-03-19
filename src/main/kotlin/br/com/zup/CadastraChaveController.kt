package br.com.zup

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/api/cadastra/chave")
class CadastraChaveController {

    @Post
    fun cadastra(@Body request: CadastraChaveRequest) {

        println(request)

    }

}