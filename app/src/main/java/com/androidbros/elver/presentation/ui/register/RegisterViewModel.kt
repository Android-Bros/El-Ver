package com.androidbros.elver.presentation.ui.register

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.User
import com.androidbros.elver.util.Constants.IMAGES
import com.androidbros.elver.util.Constants.USERS
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
    val error = MutableLiveData<String>()

    fun registrationConfirmationStatus(
        email: String, password: String,
        name: String, surname: String,
        phoneNumber: String, selectedImage: Uri?
    ) {
        firebaseRegistrationConfirmation(
            email,
            password,
            name,
            surname,
            phoneNumber,
            selectedImage
        )
    }

    private fun firebaseRegistrationConfirmation(
        email: String, password: String, name: String, surname: String, phoneNumber: String,
        selectedImage: Uri?
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
                        val imageReference = reference.child(IMAGES).child(profileImageName)
                        imageReference.putFile(selectedImage).addOnSuccessListener {
                            val uploadedImageReference =
                                reference.child(IMAGES).child(profileImageName)
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
                                    db.collection(USERS).document(activeUserUid).set(user)
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
                        }.addOnFailureListener { exception ->
                            animation.value = false
                            error.value = exception.localizedMessage
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
                        db.collection(USERS)
                            .document(activeUserUid).set(user).addOnCompleteListener { success ->
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
            }.addOnFailureListener { errorMessage ->
                animation.value = false
                error.value = errorMessage.localizedMessage
            }
    }

}