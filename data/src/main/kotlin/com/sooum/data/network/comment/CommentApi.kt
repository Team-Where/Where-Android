package com.sooum.data.network.comment

import com.sooum.data.network.comment.request.AddCommentRequest
import com.sooum.data.network.comment.request.DeleteCommentRequest
import com.sooum.data.network.comment.request.EditCommentRequest
import com.sooum.domain.model.CommentListItem
import com.sooum.domain.model.CommentSimple
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentApi {

    @POST("api/place/comment")
    suspend fun addPlaceComment(
        @Body data: AddCommentRequest
    ): Response<CommentSimple>

    @PUT("api/place/comment")
    suspend fun editPlaceComment(
        @Body data: EditCommentRequest
    ): Response<CommentSimple>

    @HTTP(method = "DELETE", path = "api/place/comment", hasBody = true)
    suspend fun deletePlaceComment(
        @Body data: DeleteCommentRequest
    ): Response<Any>

    @GET("api/place/comment/{id}")
    suspend fun getPlaceCommentList(
        @Path("id") placeId: Int
    ): Response<List<CommentListItem>>
}