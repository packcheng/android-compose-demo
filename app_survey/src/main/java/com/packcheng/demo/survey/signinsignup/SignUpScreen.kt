package com.packcheng.demo.survey.signinsignup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.packcheng.demo.survey.R
import com.packcheng.demo.survey.theme.AppSurveyTheme
import com.packcheng.demo.survey.theme.stronglyDeemphasizedAlpha
import com.packcheng.demo.survey.util.supportWideScreen

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(onNavigateEvent: (SignUpEvent) -> Unit) {
    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(topAppBarText = stringResource(id = R.string.create_account),
                onBackPressed = { onNavigateEvent(SignUpEvent.NavigateBack) })
        },
        content = { contentPadding ->
            SignInSignUpScreen(
                onSignInAsGust = { onNavigateEvent(SignUpEvent.SignInAsGuest) },
                contentPadding = contentPadding,
                modifier = Modifier.supportWideScreen()
            ) {
                Column {
                    SignUpContent(onSignUpSubmitted = { email, password ->
                        onNavigateEvent(
                            SignUpEvent.SingUp(email, password)
                        )
                    })
                }
            }
        }
    )
}

@Composable
fun SignUpContent(onSignUpSubmitted: (email: String, password: String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val passwordFocusRequester = remember { FocusRequester() }
        val passwordConfirmFocusRequester = remember { FocusRequester() }
        val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
            mutableStateOf(EmailState())
        }

        Email(emailState = emailState, onImeAction = { passwordFocusRequester.requestFocus() })
        Spacer(modifier = Modifier.height(16.dp))

        val passwordState = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password), passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = { passwordConfirmFocusRequester.requestFocus() },
            modifier = Modifier.focusRequester(passwordFocusRequester)
        )

        val confirmPasswordState = remember { ConfirmPasswordState(passwordState = passwordState) }
        val onSubmit = {
            if (emailState.isValid && passwordState.isValid && confirmPasswordState.isValid) {
                onSignUpSubmitted(emailState.text, passwordState.text)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Password(
            label = stringResource(id = R.string.confirm_password),
            passwordState = confirmPasswordState,
            onImeAction = { onSubmit() },
            modifier = Modifier.focusRequester(passwordConfirmFocusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.terms_and_conditions),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSubmit() },
            modifier = Modifier.fillMaxWidth(),
            enabled = emailState.isValid && passwordState.isValid && confirmPasswordState.isValid
        ) {
            Text(text = stringResource(id = R.string.create_account))
        }
    }
}

@Preview(widthDp = 1024)
@Composable
fun SignUpPreview() {
    AppSurveyTheme {
        SignUp {}
    }
}
