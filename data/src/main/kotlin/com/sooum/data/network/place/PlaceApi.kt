package com.sooum.data.network.place

import com.sooum.data.network.place.request.AddPlaceRequest
import com.sooum.data.network.place.request.DeletePlaceRequest
import com.sooum.data.network.place.request.LikePlaceRequest
import com.sooum.data.network.place.request.PickPlaceRequest
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlacePickStatus
import com.sooum.domain.model.PlaceWithUsers
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface PlaceApi {

    @POST("api/place")
    suspend fun addPlace(
        @Body data: AddPlaceRequest
    ): Response<Place>

    @GET("api/place")
    suspend fun getPlaceList(
        @Query("meetingId") meetingId: Int,
        @Query("userId") userId: Int
    ): Response<List<PlaceWithUsers>>

    @HTTP(method = "DELETE", path = "api/place", hasBody = true)
    suspend fun deletePlace(
        @Body data: DeletePlaceRequest
    ): Response<String>

    @POST("api/place/pick")
    suspend fun pickPlace(
        @Body data: PickPlaceRequest
    ): Response<PlacePickStatus>

    @POST("api/place/like")
    suspend fun likePlace(
        @Body data: LikePlaceRequest
    ): Response<PlacePickStatus>
}