package com.androidbros.elver.model


import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("messageTitle")
    val messageTitle: String,
    @SerializedName("organization")
    val organization: String
)