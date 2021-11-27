package com.androidbros.elver.presentation.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidbros.elver.data.repository.ElVerRepository
import com.androidbros.elver.model.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val elVerRepository: ElVerRepository) :
    ViewModel() {

    private var _notification: MutableLiveData<List<Notification>> =
        MutableLiveData()
    val notification: LiveData<List<Notification>>
        get() = _notification


    init {
        getNotification()
    }

    private fun getNotification() {
        viewModelScope.launch {
            _notification.value = elVerRepository.getNotification()
        }
    }

}