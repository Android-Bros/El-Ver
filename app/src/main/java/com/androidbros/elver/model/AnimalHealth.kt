package com.androidbros.elver.model

data class AnimalHealth(
    val location: String,
    val animalType: String,
    val burn: Boolean,
    val damage: Boolean,
    val tremor: Boolean,
    val uuid: String
)
