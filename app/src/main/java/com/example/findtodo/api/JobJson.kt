package com.example.findtodo.api

data class JobJson(
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
)