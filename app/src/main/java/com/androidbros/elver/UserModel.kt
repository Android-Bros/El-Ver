package com.androidbros.elver

import com.google.firebase.Timestamp

data class UserModel(
    val name: String,
    val surname: String,
    val email: String,
    val userUid: String,
    val phoneNumber: String,
    var profileImage: String?,
    var profileImageName: String?,
    val registrationTime: Timestamp
)