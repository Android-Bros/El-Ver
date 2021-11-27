package com.androidbros.elver.presentation.ui.requirement

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.Requirement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RequirementViewModel : ViewModel() {

    private val db = Firebase.firestore

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()

    fun shareHealthRequirement(
        context: Context,
        location: String,
        howManyPeople: String,
        clothes: Boolean,
        foodAndWater: Boolean,
        cleaningMaterial: Boolean,
        tent: Boolean,
        blanket: Boolean
    ) {
        shareRequirementHealth(
            context,
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
        context: Context,
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

        val healthRequirement = Requirement(
            location, howManyPeople, clothes, foodAndWater, cleaningMaterial, tent, blanket, uuid
        )

        db.collection("Requirements").document(uuid).set(healthRequirement)
            .addOnCompleteListener { success ->
                if (success.isSuccessful) {
                    dataConfirmation.value = true
                    animation.value = false
                }
            }.addOnFailureListener { error ->
                animation.value = false
                Toast.makeText(
                    context,
                    error.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }

    }

}