package com.sooum.data.network.schedule

import com.sooum.data.network.schedule.request.AddScheduleRequest
import com.sooum.data.network.schedule.request.DeleteScheduleRequest
import com.sooum.data.network.schedule.request.EditScheduleRequest
import com.sooum.domain.model.Schedule
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScheduleApi {

    @POST("api/schedule")
    suspend fun addSchedule(
        @Body data: AddScheduleRequest
    ): Response<Schedule>

    @Headers("Cache-Control: max-age=60")
    @GET("api/schedule/{id}")
    suspend fun getSchedule(
        @Path("id") scheduleId: Int
    ): Response<Schedule>

    @PUT("api/schedule")
    suspend fun editSchedule(
        @Body data: EditScheduleRequest
    ): Response<Schedule>

    @DELETE("api/schedule")
    suspend fun deleteSchedule(
        @Body data: DeleteScheduleRequest
    ): Response<Any>
}