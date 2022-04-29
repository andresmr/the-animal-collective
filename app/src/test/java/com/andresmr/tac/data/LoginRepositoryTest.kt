package com.andresmr.tac.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hisp.dhis.android.core.D2
import org.hisp.dhis.android.core.user.User
import org.hisp.dhis.android.core.user.UserModule
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private val userFAKE = User.builder().uid("uid").build()
    private val userModule: UserModule = mock {
        on { user() } doReturn mock()
    }
    private val d2: D2 = mock {
        on { userModule() } doReturn userModule
    }

    private lateinit var repository: LoginRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = LoginRepository(d2, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldReturnTrueWhenUserIsLogged() = runTest {
        whenever(userModule.blockingIsLogged()) doReturn true
        assertEquals(repository.isUserLogged(), true)
    }

    @Test
    fun shouldReturnFalseWhenUserIsNotLogged() = runTest {
        whenever(userModule.blockingIsLogged()) doReturn false
        assertEquals(repository.isUserLogged(), false)
    }

    @Test
    fun shouldLoginSuccessfully() = runTest {
        whenever(userModule.blockingLogIn(any(), any(), any())) doReturn userFAKE
        assertEquals(repository.login(), userFAKE)
    }

    @Test
    fun shouldGetUser() = runTest {
        whenever(d2.userModule().user().blockingGet()) doReturn userFAKE
        assertEquals(repository.getUser(), userFAKE)
    }
}
