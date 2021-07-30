package com.example.findtodo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.findtodo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://www.boredapi.com"


class MainActivity : AppCompatActivity() {

    private val urlImagesMap = mutableMapOf<String, String>(
        "education" to "https://clck.ru/WTtLX",
        "social" to "https://clck.ru/WTtQB",
        "recreational" to "https://clck.ru/WTtSo",
        "diy" to "https://clck.ru/WTtVU",
        "charity" to "https://firebasestorage.googleapis.com/v0/b/take-to-do-7c7ba.appspot.com/o/210616074028TZBH.png?alt=media&token=7c737fd7-37df-4a7b-bdc2-deb77b607533",
        "relaxation" to "https://firebasestorage.googleapis.com/v0/b/take-to-do-7c7ba.appspot.com/o/EU_MdjIWAAAZ5t-.png?alt=media&token=0b216782-1682-438f-afa1-f49d4d3bae79",
        "cooking" to "https://firebasestorage.googleapis.com/v0/b/take-to-do-7c7ba.appspot.com/o/65805332.png?alt=media&token=f393738c-adc1-4beb-85e0-e29d911aada7",
        "music" to "https://firebasestorage.googleapis.com/v0/b/take-to-do-7c7ba.appspot.com/o/depositphotos_8480205-stock-illustration-music-doodle.png?alt=media&token=a26aa6c8-cca0-4bc8-b286-212acef11720",
        "busywork" to "https://firebasestorage.googleapis.com/v0/b/take-to-do-7c7ba.appspot.com/o/teaching-busy-work.png?alt=media&token=9305d23e-2cef-4154-83b4-2cce8a3367c8"

    )

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        getCurrentData()


        binding.generateButton.setOnClickListener() {
            getCurrentData()
        }


    }

    private fun getCurrentData() {

        val api: ApiRequests? = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)


        GlobalScope.launch(Dispatchers.IO) {

            val response = api!!.getJob().awaitResponse()

            if (response.isSuccessful) {

                val data = response.body()!!
                Log.d(TAG, data.activity)


                withContext(Dispatchers.Main) {

                    textJob.visibility = View.VISIBLE
                    jobImage.visibility = View.VISIBLE
                    textJob.text = data.activity
                    Glide.with(this@MainActivity).load(urlImagesMap[data.type]).into(jobImage)

                }

            }

        }
    }


}