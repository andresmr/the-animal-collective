package com.andresmr.tac.ui.login

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 * Displays the login route
 *
 * @param uiState the data to show on the screen
 */
@Composable
fun LoginScreen(
    uiState: LoginViewModelState
) {
    Text(text = uiState.errorMessage?.message ?: if (uiState.isLoading) "Loading..." else "Done!")
}
