package com.andresmr.tac.ui.login

data class LoginViewModelState(
    val status: Status = Status.NOT_LOGGED,
    val isLoading: Boolean = false,
    val message: String = ""
)

enum class Status {
    NOT_LOGGED,
    LOGGED_IN,
    ERROR,
    LOGGING_IN
}
