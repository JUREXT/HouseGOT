package com.czech.housegot.data

import com.czech.housegot.network.ApiService
import com.czech.housegot.utils.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ApiServiceTest {

    private lateinit var apiService: ApiService
    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url(Constants.BASE_URL))
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get houses is successful`() = runBlocking {

        val pageSize = 20

        val response = apiService.getHouses(page = 1, pageSize = pageSize)

        Assert.assertEquals(response.body()?.size, pageSize)
        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_OK)
    }

    @Test
    fun `get house detail is successful`() = runBlocking {

        val response = apiService.getHouseDetail(houseId = 1)

        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_OK)
    }

    @Test
    fun `house detail id is invalid`() = runBlocking {

        val response = apiService.getHouseDetail(houseId = 0)

        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_NOT_FOUND)
    }

    @Test
    fun `get character is successful`() = runBlocking {

        val response = apiService.getCharacterDetail(charId = 1)

        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_OK)
    }

    @Test
    fun `character id is invalid`() = runBlocking {

        val response = apiService.getCharacterDetail(charId = 0)

        Assert.assertEquals(response.code(), HttpURLConnection.HTTP_NOT_FOUND)
    }

}