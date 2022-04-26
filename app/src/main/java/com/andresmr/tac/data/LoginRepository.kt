package com.andresmr.tac.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hisp.dhis.android.core.D2
import org.hisp.dhis.android.core.user.User

class LoginRepository(private val d2: D2) {

    suspend fun login(): User? = withContext(Dispatchers.IO) {
        d2.userModule().blockingLogIn(
            "admin",
            "district",
            "http://10.0.2.2:8080"
        )
    }
}