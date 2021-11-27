package com.androidbros.elver.presentation.ui.register

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class RegisterViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()

    fun registrationConfirmationStatus(
        email: String, password: String,
        name: String, surname: String,
        phoneNumber: String, selectedImage: Uri?, context: Context
    ) {
        firebaseRegistrationConfirmation(
            email,
            password,
            name,
            surname,
            phoneNumber,
            selectedImage,
            context
        )
    }

    private fun firebaseRegistrationConfirmation(
        email: String, password: String, name: String, surname: String, phoneNumber: String,
        selectedImage: Uri?, context: Context
    ) {
        animation.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { firebaseMailRegistration ->
                if (firebaseMailRegistration.isSuccessful) {
                    val registrationTime = Timestamp.now()
                    val activeUserUid = auth.currentUser?.uid
                    if (selectedImage != null) {
                        var imageReferenceLink: String?
                        val profileImageName: String?
                        val reference = storage.reference
                        val uuid = UUID.randomUUID()
                        profileImageName = "${uuid}.jpeg"
                        val imageReference = reference.child("Images").child(profileImageName)
                        imageReference.putFile(selectedImage).addOnSuccessListener {
                            val uploadedImageReference =
                                reference.child("Images").child(profileImageName)
                            uploadedImageReference.downloadUrl.addOnSuccessListener { uri ->
                                imageReferenceLink = uri.toString()
                                if (imageReferenceLink != null) {
                                    val user = User(
                                        name,
                                        surname,
                                        email,
                                        activeUserUid!!,
                                        phoneNumber,
                                        imageReferenceLink,
                                        profileImageName,
                                        registrationTime
                                    )
                                    db.collection("Users").document(activeUserUid).set(user)
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
                        }.addOnFailureListener { exception ->
                            animation.value = false
                            Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        val user = User(
                            name,
                            surname,
                            email,
                            activeUserUid!!,
                            phoneNumber,
                            null,
                            null,
                            registrationTime
                        )
                        db.collection("Users")
                            .document(activeUserUid).set(user).addOnCompleteListener { success ->
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
            }.addOnFailureListener { error ->
                animation.value = false
                Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

}