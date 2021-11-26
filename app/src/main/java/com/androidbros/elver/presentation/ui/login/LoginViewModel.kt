package com.androidbros.elver.presentation.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()

    fun loginConfirmationStatus(email: String, password: String, context: Context) {
        firebaseLogin(email, password, context)
    }

    private fun firebaseLogin(email: String, password: String, context: Context) {
        animation.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { process ->
            if (process.isSuccessful) {
                dataConfirmation.value = true
                animation.value = false
            }
        }.addOnFailureListener { error ->
            animation.value = false
            Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

}