package com.androidbros.elver

import com.google.firebase.Timestamp

data class UserModel(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    var profileImage: String?,
    var profileImageName: String?,
    val registrationTime: Timestamp
)