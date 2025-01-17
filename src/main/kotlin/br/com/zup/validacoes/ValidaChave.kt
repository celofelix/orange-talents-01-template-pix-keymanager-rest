package br.com.zup.validacoes

import br.com.zup.cadastra.CadastraChaveRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidaChaveValidator::class])
annotation class ValidaChave(
    val message: String = "Chave pix informada não é válida. Verifique se a chave corresponde ao seu tipo",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)


@Singleton
class ValidaChaveValidator : ConstraintValidator<ValidaChave, CadastraChaveRequest> {

    override fun isValid(
        value: CadastraChaveRequest?,
        annotationMetadata: AnnotationValue<ValidaChave>,
        context: ConstraintValidatorContext
    ): Boolean {

        if (value?.tipoDeChave == null) {
            return false
        }

        return value.tipoDeChave.valida(value.chave)

    }
}
