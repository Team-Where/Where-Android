package com.sooum.data.network

import android.util.Log
import com.sooum.domain.model.ApiResult
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import retrofit2.Response

fun <T> safeFlow(
    apiFunc: suspend () -> Response<T>
): Flow<ApiResult<T>> = flow {
    val result = runCatching { apiFunc.invoke() }
    result
        .onSuccess { response ->
            if (!currentCoroutineContext().isActive) return@flow
            Log.d("Https", response.toString())
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    emit(ApiResult.SuccessEmpty)
                } else {
                    emit(ApiResult.Success(body))
                }
            } else {
                val code = response.code()
                var message = ""
                val errorString= response.errorBody()?.string()
                if (errorString != null) {
                    message = errorString
                }
                if (message.isEmpty()) {
                    message = response.message()
                }
                if (message.isEmpty()) {
                    when (code) {
                        400 -> {
                            message = "잘못된 요청입니다."
                        }
                    }
                }
                emit(ApiResult.Fail.Error(code, message))
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