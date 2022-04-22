package com.andresmr.tac.data

import org.hisp.dhis.android.core.D2Manager
import org.hisp.dhis.android.core.user.User

class LoginRepository {
    private val d2 = D2Manager.getD2()


    fun login(): User? = d2.userModule().blockingLogIn(
        "admin",
        "district",
        "http://10.0.2.2:8080"
    )
}