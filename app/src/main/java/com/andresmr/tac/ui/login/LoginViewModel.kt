package com.andresmr.tac.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresmr.tac.data.LoginRepository
import com.andresmr.tac.utils.ErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.hisp.dhis.android.core.maintenance.D2Error

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()
    val uiState = MutableStateFlow(LoginViewModelState(isLoading = true))

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    uiState.value = loginRepository.login()?.let {
                        LoginViewModelState(false)
                    } ?: LoginViewModelState(false, ErrorMessage("Something went wrong..."))
                } catch (e: Exception) {
                    if (e.cause is D2Error) {
                        val d2Error = e.cause as D2Error
                        Log.e(
                            "LOGIN",
                            d2Error.errorComponent()
                                .toString() + " " + d2Error.httpErrorCode() + " " + d2Error.errorCode()
                        )
                    }
                }
            }
        }
    }
}
