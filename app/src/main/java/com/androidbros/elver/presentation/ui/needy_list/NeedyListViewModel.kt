package com.androidbros.elver.presentation.ui.needy_list

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.Requirement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NeedyListViewModel : ViewModel() {

    private val db = Firebase.firestore
    private var needyList: ArrayList<Requirement> = arrayListOf()

    val animation = MutableLiveData<Boolean>()
    val dataConfirmation = MutableLiveData<Boolean>()
    val listOfRequirement = MutableLiveData<ArrayList<Requirement>>()

    init {
        getNeedyListFromFirebase()
    }

    /*fun getNeedyList() {
        return getNeedyListFromFirebase()
    }*/

    private fun getNeedyListFromFirebase() {
        animation.value = true
        db.collection("Requirements").get().addOnSuccessListener { documents ->
            needyList.clear()
            for (document in documents) {
                val data = Requirement(
                    document["location"] as String,
                    document["howManyPeople"] as String,
                    document["clothes"] as Boolean,
                    document["foodAndWater"] as Boolean,
                    document["cleaningMaterial"] as Boolean,
                    document["tent"] as Boolean,
                    document["blanket"] as Boolean,
                    document["uuid"] as String,
                )
                needyList.add(data)
                listOfRequirement.value = needyList
            }
            animation.value = false
        }/*.addOnFailureListener {
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }*/
    }

}