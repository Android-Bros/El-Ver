package com.androidbros.elver.presentation.ui.needy_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidbros.elver.model.Requirement
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
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

    private fun getNeedyListFromFirebase() {
        db.collection("Requirements").orderBy(
            "uploadTime",
            Query.Direction.DESCENDING
        ).addSnapshotListener { snapshot, exception ->
            if (snapshot != null) {
                if (!snapshot.isEmpty) {
                    needyList.clear()
                    val documents = snapshot.documents
                    for (document in documents) {
                        val location = document.get("location") as String
                        val howManyPeople = document.get("howManyPeople") as String
                        val clothes = document.get("clothes") as Boolean
                        val foodAndWater = document.get("foodAndWater") as Boolean
                        val cleaningMaterial = document.get("cleaningMaterial") as Boolean
                        val tent = document.get("tent") as Boolean
                        val blanket = document.get("blanket") as Boolean
                        val uuid = document.get("uuid") as String
                        val uploadTime = document.get("uploadTime") as Timestamp
                        val requirement = Requirement(
                            location,
                            howManyPeople,
                            clothes,
                            foodAndWater,
                            cleaningMaterial,
                            tent,
                            blanket,
                            uuid,
                            uploadTime
                        )
                        needyList.add(requirement)
                    }
                    listOfRequirement.value = needyList
                }
            }

        }
    }

}