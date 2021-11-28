package com.androidbros.elver.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.Emergency
import com.androidbros.elver.util.Constants.EMERGENCY
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class HomeViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val dataConfirmation = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun sendEmergencyNotification(location: String) {
        sendEmergencyNotificationToFirebase(location, auth.uid!!)
    }

    private fun sendEmergencyNotificationToFirebase(
        location: String,
        uuid: String
    ) {
        db.collection(EMERGENCY).document()

        val randomUUID = UUID.randomUUID().toString()

        val emergency = Emergency(
            location,
            randomUUID,
            uuid
        )

        db.collection(EMERGENCY).document(randomUUID).set(emergency)
            .addOnCompleteListener { success ->
                if (success.isSuccessful) {
                    dataConfirmation.value = true
                }
            }.addOnFailureListener { errorMessage ->
                error.value = errorMessage.localizedMessage
            }
    }

}