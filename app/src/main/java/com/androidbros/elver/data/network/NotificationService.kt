package com.androidbros.elver.data.network

import com.androidbros.elver.model.Notification
import retrofit2.http.GET

interface NotificationService {

    @GET("emergency-messages")
    suspend fun getWizards(): List<Notification>

}