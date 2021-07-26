package com.example.findtodo

import com.example.findtodo.api.JobJson
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    @GET("/api/activity/")
    fun getJob(): Call<JobJson>
}