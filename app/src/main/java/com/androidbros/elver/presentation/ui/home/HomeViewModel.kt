package com.androidbros.elver.presentation.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.Emergency
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class HomeViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val dataConfirmation = MutableLiveData<Boolean>()

    fun sendEmergencyNotification(context: Context, location: String) {
        sendEmergencyNotificationToFirebase(context, location, auth.uid!!)
    }

    private fun sendEmergencyNotificationToFirebase(
        context: Context,
        location: String,
        uuid: String
    ) {
        db.collection("Emergency").document()

        val randomUUID = UUID.randomUUID().toString()

        val emergency = Emergency(
            location,
            randomUUID,
            uuid
        )

        db.collection("Emergency").document(randomUUID).set(emergency)
            .addOnCompleteListener { success ->
                if (success.isSuccessful) {
                    dataConfirmation.value = true
                }
            }.addOnFailureListener { error ->
                Toast.makeText(
                    context,
                    error.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

}