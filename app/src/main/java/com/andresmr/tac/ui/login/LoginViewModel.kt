package com.andresmr.tac.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresmr.tac.data.LoginRepository
import com.andresmr.tac.ui.login.Status.ERROR
import com.andresmr.tac.ui.login.Status.LOGGED_IN
import com.andresmr.tac.ui.login.Status.LOGGING_IN
import com.andresmr.tac.ui.login.Status.NOT_LOGGED
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.hisp.dhis.android.core.D2Manager
import org.hisp.dhis.android.core.maintenance.D2Error

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository(D2Manager.getD2())
    val uiState = MutableStateFlow(LoginViewModelState())

    init {
        launchDataLoad {
            uiState.value = if (loginRepository.isUserLogged()) {
                LoginViewModelState(
                    status = LOGGED_IN,
                    message = "Hello ${loginRepository.getUser().firstName()}"
                )
            } else {
                LoginViewModelState(status = NOT_LOGGED)
            }
        }
    }

    fun onLoginClick() {
        uiState.value = LoginViewModelState(status = LOGGING_IN)

        launchDataLoad {
            val user = loginRepository.login()
            uiState.value = LoginViewModelState(
                status = LOGGED_IN,
                message = "Hello ${user.firstName()}"
            )
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                uiState.value = uiState.value.copy(isLoading = true)
                block()
            } catch (error: Exception) {
                if (error.cause is D2Error) {
                    val d2Error = error.cause as D2Error
                    val message = d2Error.errorComponent().toString() + " " +
                        d2Error.httpErrorCode() + " " + d2Error.errorCode()
                    Log.e("LOGIN_VIEW", message)
                    uiState.value = LoginViewModelState(
                        status = ERROR,
                        message = message
                    )
                } else {
                    uiState.value = LoginViewModelState(
                        status = ERROR,
                        message = error.message.toString()
                    )
                }

            } finally {
                uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }
}
