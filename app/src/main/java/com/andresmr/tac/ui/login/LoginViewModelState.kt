package com.andresmr.tac.ui.login

import com.andresmr.tac.utils.ErrorMessage

data class LoginViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: ErrorMessage? = null
)
