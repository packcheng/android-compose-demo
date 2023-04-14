package com.packcheng.demo.survey.signinsignup

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/4/14 14:19
 */
sealed class SignUpEvent {
    data class SingUp(val email: String, val password: String) : SignUpEvent()
    object SignIn : SignUpEvent()
    object SignInAsGuest : SignUpEvent()
    object NavigateBack : SignUpEvent()
}

@Composable
fun SignUp(onNavigateEvent: (SignUpEvent) -> Unit) {

}