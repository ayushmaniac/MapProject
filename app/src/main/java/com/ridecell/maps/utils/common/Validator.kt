package com.ridecell.maps.utils.common

import com.ridecell.maps.R
import java.util.regex.Pattern

object Validator {

    val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private const val MIN_PASSWORD_LENGTH = 6

    fun validateLoginFields(email: String?, password: String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {
                email.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            ValidationResource.error(R.string.email_field_empty)
                        )
                    )
                !EMAIL_ADDRESS.matcher(email).matches() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            ValidationResource.error(R.string.email_field_invalid)
                        )
                    )
                else ->
                    add(Validation(Validation.Field.EMAIL, ValidationResource.success()))
            }
            when {
                password.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.PASSWORD,
                            ValidationResource.error(R.string.password_field_empty)
                        )
                    )
                password.length < MIN_PASSWORD_LENGTH ->
                    add(
                        Validation(
                            Validation.Field.PASSWORD,
                            ValidationResource.error(R.string.password_field_small_length)
                        )
                    )
                else -> add(Validation(Validation.Field.PASSWORD, ValidationResource.success()))
            }
        }


    fun validateSingupFields(email: String?, password: String?, name : String?): List<Validation> =
        ArrayList<Validation>().apply {
            when {
                name.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.NAME,
                            ValidationResource.error(R.string.empty_name)
                        ))
            }
            when {
                email.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            ValidationResource.error(R.string.email_field_empty)
                        )
                    )
                !EMAIL_ADDRESS.matcher(email).matches() ->
                    add(
                        Validation(
                            Validation.Field.EMAIL,
                            ValidationResource.error(R.string.email_field_invalid)
                        )
                    )
                else ->
                    add(Validation(Validation.Field.EMAIL, ValidationResource.success()))
            }
            when {
                password.isNullOrBlank() ->
                    add(
                        Validation(
                            Validation.Field.PASSWORD,
                            ValidationResource.error(R.string.password_field_empty)
                        )
                    )
                password.length < MIN_PASSWORD_LENGTH ->
                    add(
                        Validation(
                            Validation.Field.PASSWORD,
                            ValidationResource.error(R.string.password_field_small_length)
                        )
                    )
                else -> add(Validation(Validation.Field.PASSWORD, ValidationResource.success()))
            }
        }


}

data class Validation(val field: Field, val resource: ValidationResource<Int>) {

    enum class Field {
        EMAIL,
        PASSWORD,
        NAME
    }
}