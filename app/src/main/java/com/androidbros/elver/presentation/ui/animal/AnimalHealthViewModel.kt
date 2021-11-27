package com.androidbros.elver.presentation.ui.animal

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.AnimalHealth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AnimalHealthViewModel : ViewModel() {

    private val db = Firebase.firestore

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()

    fun shareAnimalHealth(
        context: Context,
        location: String,
        animalType: String,
        burn: Boolean,
        damage: Boolean,
        tremor: Boolean
    ) {
        shareAnimalHealthWithFirebase(context, location, animalType, burn, damage, tremor)
    }

    private fun shareAnimalHealthWithFirebase(
        context: Context,
        location: String,
        animalType: String,
        burn: Boolean,
        damage: Boolean,
        tremor: Boolean
    ) {
        animation.value = true

        val uuid = UUID.randomUUID().toString()

        val animal = AnimalHealth(
            location, animalType, burn, damage, tremor, uuid
        )

        db.collection("Animals").document(uuid).set(animal)
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