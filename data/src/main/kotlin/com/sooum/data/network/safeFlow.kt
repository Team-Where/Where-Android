package com.sooum.data.network

import com.sooum.domain.model.ApiResult
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import retrofit2.HttpException
import retrofit2.Response

fun <T> safeFlow(
    apiFunc: suspend () -> Response<T>
): Flow<ApiResult<T>> = flow {
    val result = runCatching { apiFunc.invoke() }
    result.onSuccess { response ->
        if (!currentCoroutineContext().isActive) return@flow
        if (response.isSuccessful) {
            val body = response.body() ?: throw NullPointerException()
            emit(ApiResult.Success(body))
        } else {
            emit(ApiResult.Fail.Error(response.code(), response.message()))
        }
    }.onFailure { e ->
        e.printStackTrace()
        if (!currentCoroutineContext().isActive) return@flow
        when (e) {
            is HttpException -> emit(ApiResult.Fail.Error(e.code(), e.message()))
            else -> emit(ApiResult.Fail.Exception(e))
        }
    }
}