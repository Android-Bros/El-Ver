package com.androidbros.elver.data

import com.androidbros.elver.data.network.NotificationService
import com.androidbros.elver.model.Notification
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofit: NotificationService) {

    suspend fun getNotification(): List<Notification> {
        return retrofit.getNotification()
    }
}