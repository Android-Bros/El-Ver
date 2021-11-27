package com.androidbros.elver.presentation.ui.user_profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.R
import com.androidbros.elver.model.User
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

    fun getProfileInfo(context: Context) {
        pullUserInfo(context)
    }

    fun deleteAccount(context: Context) {
        deleteMyAccountFromDatabase(context)
    }

    private fun pullUserInfo(context: Context) {
        animation.value = true
        val documentReference = db.collection("Users").document(userUid!!)
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
            }.addOnFailureListener { error ->
                Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteMyAccountFromDatabase(context: Context) {
        animation.value = true
        val storageRef = storage.reference
        if (userUid != null) {
            val documentReference = db.collection("Users").document(userUid)
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
                        storageRef.child("Images").child(user.profileImageName!!)
                            .delete().addOnSuccessListener {
                                db.collection("Users").document(userUid).delete()
                                    .addOnSuccessListener {
                                        val currentUser = Firebase.auth.currentUser!!
                                        Firebase.auth.signOut()
                                        currentUser.delete().addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                animation.value = false
                                                Toast.makeText(
                                                    context,
                                                    R.string.deletion_success,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                deleteAccountState.value = true
                                            }
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                it.localizedMessage,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }.addOnFailureListener {
                                        Toast.makeText(
                                            context,
                                            it.localizedMessage,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }.addOnFailureListener {
                                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG)
                                    .show()
                            }
                    } else {
                        db.collection("Users").document(userUid).delete().addOnSuccessListener {
                            auth.currentUser!!.delete().addOnSuccessListener {
                                animation.value = false
                                Toast.makeText(
                                    context,
                                    R.string.deletion_success,
                                    Toast.LENGTH_LONG
                                ).show()
                            }.addOnFailureListener {
                                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }.addOnFailureListener { error ->
                Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, R.string.try_again_later, Toast.LENGTH_LONG).show()
        }
    }

}