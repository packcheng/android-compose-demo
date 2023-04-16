package com.packcheng.demo.survey.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.packcheng.demo.survey.R
import com.packcheng.demo.survey.theme.AppSurveyTheme

/**
 * 问卷页
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/4/13 16:45
 */
class SurveyFragment : Fragment() {

    private val viewModel: SurveyViewModel by viewModels {
        SurveyViewModelFactory(PhotoUriManager(requireContext().applicationContext))
    }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { photoSaved ->
            if (photoSaved) {
                viewModel.onImageSaved()
            }
        }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            id = R.id.survey_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                AppSurveyTheme {
                    val state = viewModel.uiState.observeAsState().value ?: return@AppSurveyTheme
                    AnimatedContent(
                        targetState = state,
                        transitionSpec = {
                            fadeIn() + slideIntoContainer(
                                towards = AnimatedContentScope
                                    .SlideDirection.Up,
                                animationSpec = tween(ANIMATION_SLIDE_IN_DURATION)
                            ) with
                                    fadeOut(animationSpec = tween(ANIMATION_FADE_OUT_DURATION))
                        }
                    ) { targetState ->
                        when (targetState) {
                            is SurveyState.Questions -> SurveyQuestionsScreen(
                                questions = targetState,
                                shouldAskPermissions = viewModel.askForPermissions,
                                onDoNotAskForPermissions = { },
                                onAction = { id, action -> handleSurveyAction(id, action) },
                                onDonePressed = { viewModel.computeResult(targetState) },
                                onBackPressed = { activity?.onBackPressedDispatcher?.onBackPressed() })
                            is SurveyState.Result -> SurveyResultScreen(result = targetState,
                                onDonePressed = {
                                    activity?.onBackPressedDispatcher?.onBackPressed()
                                })
                        }
                    }
                }
            }
        }
    }

    private fun handleSurveyAction(questionId: Int, actionType: SurveyActionType) {
        when (actionType) {
            SurveyActionType.PICK_DATE -> showDatePicker(questionId)
            SurveyActionType.TAKE_PHOTO -> takeAPhoto()
            SurveyActionType.SELECT_CONTACT -> selectContact(questionId)
        }
    }

    private fun showDatePicker(questionId: Int) {
        val date = viewModel.getCurrentDate(questionId = questionId)
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(date)
            .build()
        picker.show(requireActivity().supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            picker.selection?.let { selectedDate ->
                viewModel.onDatePicked(questionId, selectedDate)
            }
        }
    }

    private fun takeAPhoto() {
        takePicture.launch(viewModel.getUriToSaveImage())
    }

    @Suppress("UNUSED_PARAMETER")
    private fun selectContact(questionId: Int) {
        // TODO: unsupported for now
    }

    companion object {
        private const val ANIMATION_SLIDE_IN_DURATION = 600
        private const val ANIMATION_FADE_OUT_DURATION = 200
    }
}