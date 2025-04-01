package com.sooum

import com.sooum.data.network.meet.MeetApi
import com.sooum.data.network.meet.request.AddMeetRequest
import com.sooum.data.network.meet.request.EditMeetRequest
import com.sooum.domain.model.Meet
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.test.assertEquals


class MeetApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MeetApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create(MeetApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    @Test
    fun `test addMeet`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(
                """
                    {
                    		"id": 123,
                    		"title": "모임 제목",
                    		"description": "모임 설명",
                    		"link": "모임 공유 링크",
                    		"image": "이미지 URL"
                    		"finished": false
                    }
                """.trimIndent()
            )
            .setResponseCode(200)

        mockWebServer.enqueue(mockResponse)

        val request = AddMeetRequest(
            "모임 제목",
            0,
            "모임 설명"
        )
        val dataPart =
            json.encodeToString(request).toRequestBody("application/json".toMediaTypeOrNull())


        val response = apiService.addMeet(dataPart, null)
        val meet = response.body()!!
        assertEquals(meet.title, "모임 제목")
        assertEquals(meet.description, "모임 설명")
    }

    @Test
    fun `test editMeet`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(
                """
                    {
                    		"id": 123,
                    		"title": "모임 제목",
                    		"description": "모임 설명",
                    		"link": "모임 공유 링크",
                    		"image": "이미지 URL"
                    		"finished": false
                    }
                """.trimIndent()
            )
            .setResponseCode(200)

        mockWebServer.enqueue(mockResponse)

        val request = EditMeetRequest(
            123,
            "모임 제목",
            "모임 설명",
            null
        )
        val dataPart =
            json.encodeToString(request).toRequestBody("application/json".toMediaTypeOrNull())


        val response = apiService.addMeet(dataPart, null)
        val meet = response.body()!!
        assertEquals(meet.title, "모임 제목")
        assertEquals(meet.description, "모임 설명")
    }
}