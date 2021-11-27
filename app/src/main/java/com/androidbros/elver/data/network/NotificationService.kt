package com.androidbros.elver.data.network

import com.androidbros.elver.model.Notification
import retrofit2.http.GET

interface NotificationService {

    @GET("emergency-messasdages")
    suspend fun getNotification(): List<Notification>

}