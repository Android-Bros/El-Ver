package com.androidbros.elver.data.repository

import com.androidbros.elver.data.RemoteDataSource
import com.androidbros.elver.model.Notification
import javax.inject.Inject

class ElVerRepository @Inject constructor(private val remote: RemoteDataSource) {

    suspend fun getNotification(): List<Notification> {
        return remote.getNotification()
    }
}