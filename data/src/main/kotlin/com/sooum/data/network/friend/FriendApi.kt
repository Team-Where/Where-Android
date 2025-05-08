package com.sooum.data.network.friend

import com.sooum.data.network.comment.request.DeleteCommentRequest
import com.sooum.data.network.friend.request.BookMarkFriendRequest
import com.sooum.domain.model.Friend
import com.sooum.domain.model.FriendBookMark
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PUT
import retrofit2.http.Path

interface FriendApi {

    @GET("api/friend/{id}")
    suspend fun getFriendList(
        @Path("id") id: Int
    ): Response<List<Friend>>

    @HTTP(method = "DELETE", path = "api/friend", hasBody = true)
    suspend fun deleteFriend(
        @Body data: DeleteCommentRequest
    ): Response<String>

    @PUT("/api/friend/bookmark")
    suspend fun bookMarkFriend(
        @Body data: BookMarkFriendRequest
    ): Response<FriendBookMark>
}