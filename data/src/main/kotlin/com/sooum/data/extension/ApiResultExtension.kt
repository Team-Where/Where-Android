package com.sooum.data.extension

import com.sooum.domain.model.ApiResult

/**
 * ApiResult가 [ApiResult.Success]인 경우 data를 반환하고 이외는 에러 메시지를 반환 합니다.
 */
inline fun <T> ApiResult<T>.covertApiResultToActionResultIfSuccess(
    onSuccess: (data: T) -> Unit,
    onFail: (msg: String) -> Unit
) {
    when (this) {
        is ApiResult.Success -> {
            onSuccess(this.data)
        }

        is ApiResult.Fail.Error -> {
            if (this.code == 500) {
                onFail("서버 내부 오류")
            } else {
                onFail(this.message ?: "알수 없는 오류가 발생했습니다.")
            }
        }

        is ApiResult.Fail.Exception -> {
            onFail(this.e.localizedMessage ?: "알수 없는 예외가 발생했습니다.")
        }

        else -> {
            onFail("알수 없는 예외")
        }
    }
}


/**
 * ApiResult가 [ApiResult.SuccessEmpty]인 경우 성공 이외는 에러 메시지를 반환 합니다.
 */
inline fun <T> ApiResult<T>.covertApiResultToActionResultIfSuccessEmpty(
    onSuccess: () -> Unit,
    onFail: (msg: String) -> Unit
) {
    when (this) {
        is ApiResult.SuccessEmpty -> {
            onSuccess()
        }

        is ApiResult.Fail.Error -> {
            if (this.code == 500) {
                onFail("서버 내부 오류")
            } else {
                onFail(this.message ?: "알수 없는 오류가 발생했습니다.")
            }
        }

        is ApiResult.Fail.Exception -> {
            onFail(this.e.localizedMessage ?: "알수 없는 예외가 발생했습니다.")
        }

        else -> {
            onFail("알수 없는 예외")
        }
    }
}