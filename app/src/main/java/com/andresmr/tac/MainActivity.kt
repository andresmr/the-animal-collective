package com.andresmr.tac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.andresmr.tac.data.LoginRepository
import com.andresmr.tac.ui.login.LoginScreen
import com.andresmr.tac.ui.login.LoginViewModel
import com.andresmr.tac.ui.theme.TheAnimalCollectiveTheme
import org.hisp.dhis.android.core.D2Manager

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = LoginViewModel(LoginRepository(D2Manager.getD2()))
        setContent {
            TheAnimalCollectiveTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginActivityScreen(loginViewModel = loginViewModel)
                }
            }
        }
    }
}

@Composable
fun LoginActivityScreen(loginViewModel: LoginViewModel) {
    val uiState by loginViewModel.uiState.collectAsState()
    LoginScreen(
        uiState = uiState,
        onLoginClick = loginViewModel::onLoginClick
    )
}