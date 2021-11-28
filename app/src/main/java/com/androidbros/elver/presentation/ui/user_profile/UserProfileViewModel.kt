package com.androidbros.elver.presentation.ui.user_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.User
import com.androidbros.elver.util.Constants.IMAGES
import com.androidbros.elver.util.Constants.TRY_AFTER
import com.androidbros.elver.util.Constants.USERS
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class UserProfileViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val userUid = auth.currentUser?.uid
    lateinit var userInfo: User
    private val storage = Firebase.storage

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val deleteAccountState = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getProfileInfo() {
        pullUserInfo()
    }

    fun deleteAccount() {
        deleteMyAccountFromDatabase()
    }

    private fun pullUserInfo() {
        animation.value = true
        val documentReference = db.collection(USERS).document(userUid!!)
        documentReference.get()
            .addOnSuccessListener { data ->
                if (data != null) {
                    val user = User(
                        data["name"] as String,
                        data["surname"] as String,
                        data["email"] as String,
                        data["userUid"] as String,
                        data["phoneNumber"] as String,
                        data["profileImage"] as String?,
                        data["profileImageName"] as String?,
                        data["registrationTime"] as Timestamp
                    )
                    userInfo = user
                    animation.value = false
                    dataConfirmation.value = true
                }
            }.addOnFailureListener { errorMessage ->
                animation.value = false
                error.value = errorMessage.localizedMessage
            }
    }

    private fun deleteMyAccountFromDatabase() {
        animation.value = true
        val storageRef = storage.reference
        if (userUid != null) {
            val documentReference = db.collection(USERS).document(userUid)
            documentReference.get().addOnSuccessListener { data ->
                if (data != null) {
                    val user = User(
                        data["name"] as String,
                        data["surname"] as String,
                        data["email"] as String,
                        data["userUid"] as String,
                        data["phoneNumber"] as String,
                        data["profileImage"] as String?,
                        data["profileImageName"] as String?,
                        data["registrationTime"] as Timestamp
                    )
                    if (user.profileImageName != null) {
                        storageRef.child(IMAGES).child(user.profileImageName!!)
                            .delete().addOnSuccessListener {
                                db.collection(USERS).document(userUid).delete()
                                    .addOnSuccessListener {
                                        val currentUser = Firebase.auth.currentUser!!
                                        Firebase.auth.signOut()
                                        currentUser.delete().addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                animation.value = false
                                                deleteAccountState.value = true
                                            }
                                        }.addOnFailureListener {
                                            animation.value = false
                                            error.value = it.localizedMessage
                                        }
                                    }.addOnFailureListener {
                                        animation.value = false
                                        error.value = it.localizedMessage
                                    }
                            }.addOnFailureListener {
                                animation.value = false
                                error.value = it.localizedMessage
                            }
                    } else {
                        db.collection(USERS).document(userUid).delete().addOnSuccessListener {
                            auth.currentUser!!.delete().addOnSuccessListener {
                                animation.value = false
                            }.addOnFailureListener {
                                animation.value = false
                                error.value = it.localizedMessage
                            }
                        }.addOnFailureListener {
                            animation.value = false
                            error.value = it.localizedMessage
                        }
                    }
                }
            }.addOnFailureListener {
                animation.value = false
                error.value = it.localizedMessage
            }
        } else {
            animation.value = false
            error.value = TRY_AFTER
        }
    }

}