package ru.tesseract

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.koin.core.context.GlobalContext
import ru.tesseract.api.onSuccess
import ru.tesseract.login.api.LoginApi

private val koin get() = GlobalContext.get()
private val loginState get() = koin.get<LoginState>()
private val loginApi get() = koin.get<LoginApi>()
private var token: String? = null
private const val TestLogin = "vspochernin"
private const val TestPassword = "qwe123"

fun startLoggedOut() = runTest {
    loginState.resetToken()
    launchActivity<MainActivity>()
}

fun startLoggedIn() = runTest {
    loginState.setToken(getToken(), LoginMethod.Tesseract)
    launchActivity<MainActivity>()
}

private fun getToken(): String {
    token?.let { return it }
    val response = runBlocking {
        loginApi.login(TestLogin, TestPassword)
    }
    response.onSuccess {
        token = it.token
        return it.token
    }
    error("Couldn't acquire test token from API")
}
