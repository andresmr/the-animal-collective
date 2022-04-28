package com.andresmr.tac.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hisp.dhis.android.core.D2
import org.hisp.dhis.android.core.user.User

class LoginRepository(
    private val d2: D2,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun login(): User = withContext(dispatcher) {
        d2.userModule().blockingLogIn(
            "admin",
            "district",
            "http://10.0.2.2:8080"
        )
    }

    suspend fun isUserLogged(): Boolean = withContext(dispatcher) {
        d2.userModule().blockingIsLogged()
    }

    suspend fun getUser(): User = withContext(dispatcher) {
        d2.userModule().user().blockingGet()
    }
}