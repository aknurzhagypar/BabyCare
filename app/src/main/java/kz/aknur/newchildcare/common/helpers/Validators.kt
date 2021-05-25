package kz.aknur.newchildcare.common.helpers

import java.util.regex.Pattern

object Validators {
    private const val MIN_PASSWORD_LENGTH = 5
    private val CORRECT_LOGIN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
    )

    fun validateLogin(login: String): Boolean {
        return when {
            login.isBlank() -> false
            !CORRECT_LOGIN.matcher(login).matches() -> false
            else -> true
        }
    }

    fun validatePassword(password: String): Boolean {
        return when {
            password.isBlank() -> false
            password.length < MIN_PASSWORD_LENGTH -> false
            else -> true
        }
    }

}