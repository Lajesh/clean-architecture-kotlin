package com.template.cleanarch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.template.cleanarch.base.BaseTest
import com.template.domain.usecases.auth.IAuthUseCase
import com.template.feature_onboarding.view.fragment.signin.LoginViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.inject
import java.net.HttpURLConnection

/****
 * Unit test for LogiViewModel
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 3/1/21
 * Modified on: 3/1/21
 *****/
@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class LoginViewModelTest : BaseTest() {
    private lateinit var loginViewModel: LoginViewModel

    //Inject api service created with koin
    val authUseCase: IAuthUseCase by inject()

    //Inject Mockwebserver created with koin
    val mockWebServer: MockWebServer by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    override fun setUp() {
        super.setUp()
        loginViewModel = LoginViewModel(authUseCase, mainCoroutineRule.testDispatcher)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun test_login_success_case() {
        mainCoroutineRule.testDispatcher.runBlockingTest {
            mockNetworkResponseWithFileContent("login_success.json", HttpURLConnection.HTTP_OK)
            loginViewModel.login()
            loginViewModel.addLikeCount()
            Assert.assertEquals(1, loginViewModel.getLikeCount())
            Assert.assertEquals(false, loginViewModel.data.value)
            testCoroutineScope.advanceTimeBy(2000)

            loginViewModel.errorEvent.observeForever {
                assertTrue(it.errorCode == 503)
            }
        }
    }
}