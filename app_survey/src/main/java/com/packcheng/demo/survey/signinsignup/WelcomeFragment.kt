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
 * 首页
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/4/13 16:45
 */
class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModels {
        WelcomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Welcome)
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                AppSurveyTheme {
                    WelcomeScreen(onEvent = { event ->
                        when (event) {
                            is WelcomeEvent.SignInSignUp -> {
                                viewModel.handleContinue(event.email)
                            }
                            WelcomeEvent.SignInAsGuest -> {
                                viewModel.signInAsGuest()
                            }
                        }
                    })
                }
            }
        }
    }
}