package com.sooum.domain.model

sealed class ApiResult<out T> {
    /**
     * 일반적인 성공 응답
     */
    data class Success<out T>(val data: T) : ApiResult<T>()

    /**
     * 성공 응답이지만 body가 빈경우
     */
    data object SuccessEmpty : ApiResult<Nothing>()

    /**
     * 실패
     */
    sealed class Fail : ApiResult<Nothing>() {
        /**
         * 실패를 수신한 경우
         */
        data class Error(val code: Int, val message: String?) : Fail()

        /**
         * 응답에 예외가 발생한 경우
         */
        data class Exception(val e: Throwable) : Fail()
    }
}

inline fun <reified T : Any> ApiResult<T>.onSuccess(action: (data: T) -> Unit) {
    if (this is ApiResult.Success) action(data)
}

inline fun <reified T : Any> ApiResult<T>.onError(action: (code: Int, message: String?) -> Unit) {
    if (this is ApiResult.Fail.Error) action(code, message)
}

inline fun <reified T : Any> ApiResult<T>.onException(action: (e: Throwable) -> Unit) {
    if (this is ApiResult.Fail.Exception) action(e)
}