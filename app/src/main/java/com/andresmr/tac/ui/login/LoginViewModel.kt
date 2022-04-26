package com.andresmr.tac.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresmr.tac.data.LoginRepository
import com.andresmr.tac.utils.ErrorMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.hisp.dhis.android.core.D2Manager
import org.hisp.dhis.android.core.maintenance.D2Error

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository(D2Manager.getD2())
    val uiState = MutableStateFlow(LoginViewModelState(isLoading = true))

    init {
        launchDataLoad {
            uiState.value = loginRepository.login()?.let {
                LoginViewModelState(false)
            } ?: LoginViewModelState(false, ErrorMessage("Something went wrong..."))
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
//                _spinner.value = true
                block()
            } catch (error: Exception) {
//                _snackBar.value = error.message
                if (error.cause is D2Error) {
                    val d2Error = error.cause as D2Error
                    val message = d2Error.errorComponent().toString() + " " +
                        d2Error.httpErrorCode() + " " + d2Error.errorCode()
                    Log.e("LOGIN", message)
                }

            } finally {
//                _spinner.value = false
            }
        }
    }
}
