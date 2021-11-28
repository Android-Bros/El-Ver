package com.androidbros.elver.presentation.ui.requirement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.Requirement
import com.androidbros.elver.util.Constants.REQUIREMENTS
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RequirementViewModel : ViewModel() {

    private val db = Firebase.firestore

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun shareHealthRequirement(
        location: String,
        howManyPeople: String,
        clothes: Boolean,
        foodAndWater: Boolean,
        cleaningMaterial: Boolean,
        tent: Boolean,
        blanket: Boolean
    ) {
        shareRequirementHealth(
            location,
            howManyPeople,
            clothes,
            foodAndWater,
            cleaningMaterial,
            tent,
            blanket
        )
    }

    private fun shareRequirementHealth(
        location: String,
        howManyPeople: String,
        clothes: Boolean,
        foodAndWater: Boolean,
        cleaningMaterial: Boolean,
        tent: Boolean,
        blanket: Boolean
    ) {
        animation.value = true

        val uuid = UUID.randomUUID().toString()

        val time = Timestamp.now()

        val healthRequirement = Requirement(
            location,
            howManyPeople,
            clothes,
            foodAndWater,
            cleaningMaterial,
            tent,
            blanket,
            uuid,
            time
        )

        db.collection(REQUIREMENTS).document(uuid).set(healthRequirement)
            .addOnCompleteListener { success ->
                if (success.isSuccessful) {
                    dataConfirmation.value = true
                    animation.value = false
                }
            }.addOnFailureListener { errorMessage ->
                animation.value = false
                error.value = errorMessage.localizedMessage
            }

    }

}