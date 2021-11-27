package com.androidbros.elver.presentation.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidbros.elver.data.repository.ElVerRepository
import com.androidbros.elver.model.Notification
import com.androidbros.elver.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val elVerRepository: ElVerRepository) :
    ViewModel() {

    private var _notification: MutableLiveData<NetworkResult<List<Notification>>> =
        MutableLiveData()
    val notification: LiveData<NetworkResult<List<Notification>>>
        get() = _notification


    init {
        getNotification()
    }

    private fun getNotification() {
        _notification.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val allNotification = elVerRepository.getNotification()
                if (!allNotification.isNullOrEmpty()) {
                    _notification.value = NetworkResult.Success(allNotification)
                }
            } catch (e: Exception) {
                _notification.value = NetworkResult.Error(null, e.message)
            }
        }
    }

}