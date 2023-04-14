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
 * 登录页
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/4/13 16:45
 */
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels { SignInViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.SignIn)
            }
        }

        return ComposeView(requireContext()).apply {
            // In order for savedState to work, the same ID needs to be used for all instances.
            id = R.id.sign_in_fragment

            setContent {
                AppSurveyTheme {
                    SignIn(onNavigateEvent = { event ->
                        when (event) {
                            is SignInEvent.SingIn -> {
                                viewModel.signIn(event.email, event.password)
                            }
                            SignInEvent.SignUp -> {
                                viewModel.signUp()
                            }
                            SignInEvent.SignInAsGuest -> {
                                viewModel.signInAsGuest()
                            }
                            SignInEvent.NavigateBack -> {
                                activity?.onBackPressedDispatcher?.onBackPressed()
                            }
                        }
                    }
                    )
                }
            }
        }
    }
}