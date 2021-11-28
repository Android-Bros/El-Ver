package com.androidbros.elver.presentation.ui.animal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.AnimalHealth
import com.androidbros.elver.util.Constants.ANIMALS
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AnimalHealthViewModel : ViewModel() {

    private val db = Firebase.firestore

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun shareAnimalHealth(
        location: String,
        animalType: String,
        burn: Boolean,
        damage: Boolean,
        tremor: Boolean
    ) {
        shareAnimalHealthWithFirebase(location, animalType, burn, damage, tremor)
    }

    private fun shareAnimalHealthWithFirebase(
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

        db.collection(ANIMALS).document(uuid).set(animal)
            .addOnCompleteListener { success ->
                if (success.isSuccessful) {
                    dataConfirmation.value = true
                    animation.value = false
                }
            }.addOnFailureListener {
                animation.value = false
                error.value = it.localizedMessage
            }

    }

}