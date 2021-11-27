package com.androidbros.elver.model

data class Requirement(
    val location: String,
    val howManyPeople: String,
    val clothes: Boolean,
    val foodAndWater: Boolean,
    val cleaningMaterial: Boolean,
    val tent: Boolean,
    val blanket: Boolean,
    val uuid: String
)