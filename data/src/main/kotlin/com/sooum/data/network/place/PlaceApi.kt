package com.sooum.data.network.place

import com.sooum.data.network.place.request.AddCommentRequest
import com.sooum.data.network.place.request.AddPlaceRequest
import com.sooum.data.network.place.request.DeleteCommentRequest
import com.sooum.data.network.place.request.DeletePlaceRequest
import com.sooum.data.network.place.request.EditCommentRequest
import com.sooum.data.network.place.request.LikePlaceRequest
import com.sooum.data.network.place.request.PickPlaceRequest
import com.sooum.domain.model.Comment
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.CommentSimple
import com.sooum.domain.model.Meet
import com.sooum.domain.model.Place
import com.sooum.domain.model.PlacePickStatus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlaceApi {

    @POST("api/place")
    suspend fun addPlace(
        @Body data: AddPlaceRequest
    ): Response<Place>

    @GET("api/place/{id}")
    suspend fun getPlace(
        @Path("id") placeId: Int,
    ): Response<Place>

    @DELETE("api/place")
    suspend fun deletePlace(
        @Body data: DeletePlaceRequest
    ): Response<Any>

    @POST("api/place/pick")
    suspend fun pickPlace(
        @Body data: PickPlaceRequest
    ): Response<PlacePickStatus>

    @POST("api/place/like")
    suspend fun likePlace(
        @Body data: LikePlaceRequest
    ): Response<PlacePickStatus>

    @POST("api/place/comment")
    suspend fun addPlaceComment(
        @Body data: AddCommentRequest
    ): Response<Comment>

    @PUT("api/place/comment")
    suspend fun editPlaceComment(
        @Body data: EditCommentRequest
    ): Response<CommentSimple>

    @DELETE("api/place/comment")
    suspend fun deletePlaceComment(
        @Body data: DeleteCommentRequest
    ): Response<Any>

    @GET("api/place/comment/{id}")
    suspend fun getPlaceCommentList(
        @Path("id") placeId: Int
    ): Response<List<CommentListItem>>
}