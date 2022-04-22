package com.andresmr.tac.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {

    val uiState = MutableStateFlow(LoginViewModelState(isLoading = true))
}
