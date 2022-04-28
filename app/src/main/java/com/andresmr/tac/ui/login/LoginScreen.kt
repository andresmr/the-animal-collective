package com.andresmr.tac.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andresmr.tac.ui.theme.TheAnimalCollectiveTheme

/**
 * Displays the login route
 *
 * @param uiState the data to show on the screen
 */
@Composable
fun LoginScreen(
    uiState: LoginViewModelState,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Status: ${uiState.status}"
        )
        Text(
            text = "Message: ${uiState.message}"
        )
        if (uiState.status == Status.NOT_LOGGED || uiState.status == Status.ERROR) {
            Button(
                onClick = { onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Login")
            }
        }

        if (uiState.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val uiState = LoginViewModelState()
    TheAnimalCollectiveTheme {
        LoginScreen(uiState = uiState, {})
    }
}