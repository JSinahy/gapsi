package com.laraguzman.gapsiecommerce.data.api

import com.laraguzman.gapsiecommerce.data.models.GeneralResponse
import com.laraguzman.gapsiecommerce.data.models.PhotosEcommerceModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Header

interface EcommerceServices {
    @GET("search")
    fun searchCriteria(
            @Query("query") criteria : String,
            @Header("X-IBM-Client-Id") keyHeader: String
    ) : Call<GeneralResponse<ArrayList<PhotosEcommerceModel>>>

}