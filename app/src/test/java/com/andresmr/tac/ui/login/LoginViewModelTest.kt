package com.andresmr.tac.ui.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.andresmr.tac.data.LoginRepository
import com.andresmr.tac.ui.login.Status.LOGGED_IN
import com.andresmr.tac.ui.login.Status.NOT_LOGGED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hisp.dhis.android.core.user.User
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val repository: LoginRepository = mock()

    @Mock
    private val user: User = mock {
        on { firstName() } doReturn "admin"
    }

    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenStartsLoadsUserShouldNotBeLoggedIn() = runTest {
        //Given user is not logged in
        `when`(repository.isUserLogged()).thenReturn(false)

        //When check is user is logged
        viewModel = LoginViewModel(repository)

        //Then user is not logged
        assert(viewModel.uiState.value.status == NOT_LOGGED)
    }

    @Test
    fun whenStartsLoadsUserShouldBeLoggedIn() = runTest {
        //Given user is not logged in
        `when`(repository.isUserLogged()).thenReturn(true)
        `when`(repository.getUser()).thenReturn(user)

        //When check is user is logged
        viewModel = LoginViewModel(repository)

        //Then user is not logged
        assert(viewModel.uiState.value.status == LOGGED_IN)
    }
}