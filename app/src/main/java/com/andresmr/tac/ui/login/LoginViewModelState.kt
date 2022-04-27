package com.andresmr.tac.ui.login

data class LoginViewModelState(
    val status: String = "",
    val isLoading: Boolean = false,
    val message: String = ""
)
