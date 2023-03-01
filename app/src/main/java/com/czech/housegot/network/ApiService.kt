package com.czech.housegot.network

import com.czech.housegot.models.HouseDetail
import com.czech.housegot.models.Houses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("houses")
    suspend fun getHouses(
        @Query("page") page:Int,
        @Query("pageSize") pageSize: Int
    ): Response<List<Houses>>

    @GET("houses/{id}")
    suspend fun getHouseDetail(
        @Path("id") houseId:Int,
    ): Response<HouseDetail>
}