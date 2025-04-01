package com.sooum.data.network

import com.sooum.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response

fun <T> safeFlow(
    apiFunc: suspend () -> Response<T>
): Flow<ApiResult<T>> = flow {
    try {
        emit(ApiResult.Loading)
        val response = apiFunc.invoke()
        if (response.isSuccessful) {
            val body = response.body() ?: throw java.lang.NullPointerException()
            emit(ApiResult.Success(body))
        } else {
            emit(ApiResult.Fail.Error(response.code(), response.message()))
        }
    } catch (e: HttpException) {
        emit(ApiResult.Fail.Error(code = e.code(), message = e.message()))
    } catch (e: Exception) {
        emit(ApiResult.Fail.Exception(e))
    }
}