package com.packcheng.demo.survey.signinsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.packcheng.demo.survey.Screen
import com.packcheng.demo.survey.util.Event

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/4/14 23:01
 */
class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    /**
     * Consider all sign ups successful
     */
    fun signUp(email: String, password: String) {
        userRepository.signUp(email, password)
        _navigateTo.value = Event(Screen.Survey)
    }

    fun signInAsGuest() {
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Screen.Survey)
    }

    fun signIn() {
        _navigateTo.value = Event(Screen.SignIn)
    }
}

class SignUpViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}