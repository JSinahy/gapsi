package com.laraguzman.gapsiecommerce.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Test 2
class EcommerceApiInstance {
    companion object {
        val BASE_URL : String = "https://00672285.us-south.apigw.appdomain.cloud/demo-gapsi/"
    }

    fun GetInstance() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}