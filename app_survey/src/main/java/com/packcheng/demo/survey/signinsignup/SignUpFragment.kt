package com.packcheng.demo.survey.signinsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.packcheng.demo.survey.R
import com.packcheng.demo.survey.Screen
import com.packcheng.demo.survey.navigate
import com.packcheng.demo.survey.theme.AppSurveyTheme

/**
 * 注册页
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/4/13 16:45
 */
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels { SignUpViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.SignUp)
            }
        }

        return ComposeView(requireContext()).apply {
            id = R.id.sign_up_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                AppSurveyTheme {
                    SignUp(onNavigateEvent = { onNavigateEvent ->
                        when (onNavigateEvent) {
                            is SignUpEvent.SingUp -> {
                                viewModel.signUp(onNavigateEvent.email, onNavigateEvent.password)
                            }
                            SignUpEvent.SignIn -> {
                                viewModel.signIn()
                            }
                            SignUpEvent.SignInAsGuest -> {
                                viewModel.signInAsGuest()
                            }
                            SignUpEvent.NavigateBack -> {
                                activity?.onBackPressedDispatcher?.onBackPressed()
                            }
                        }
                    })
                }
            }
        }
    }
}