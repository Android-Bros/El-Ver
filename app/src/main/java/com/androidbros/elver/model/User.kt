package com.androidbros.elver.model

import com.google.firebase.Timestamp

data class User(
    val name: String,
    val surname: String,
    val email: String,
    val userUid: String,
    val phoneNumber: String,
    var profileImage: String?,
    var profileImageName: String?,
    val registrationTime: Timestamp
)