package com.androidbros.elver.presentation.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun loginConfirmationStatus(email: String, password: String) {
        firebaseLogin(email, password)
    }

    private fun firebaseLogin(email: String, password: String) {
        animation.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { process ->
            if (process.isSuccessful) {
                dataConfirmation.value = true
                animation.value = false
            }
        }.addOnFailureListener { errorMessage ->
            animation.value = false
            error.value = errorMessage.localizedMessage
        }
    }

}