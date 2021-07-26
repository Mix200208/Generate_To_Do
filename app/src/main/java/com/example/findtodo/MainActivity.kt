package com.example.findtodo

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*

const val BASE_URL = "https://www.boredapi.com"


class MainActivity : AppCompatActivity() {



    private val TAG = "MainActivity"

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        getCurrentData()




            val getButton = findViewById<Button>(R.id.generateButton)

            getButton.setOnClickListener(){
                getCurrentData()
            }





    }

    private fun getCurrentData() {

        val api: ApiRequests? = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO){

            val response = api!!.getJob().awaitResponse()

            if(response.isSuccessful){

                val data = response.body()!!
                Log.d(TAG, data.activity)


               withContext(Dispatchers.Main){

                   textJob.visibility = View.VISIBLE
                   jobImage.visibility = View.VISIBLE

                   textJob.text = data.activity





               }

            }


        }
    }



}