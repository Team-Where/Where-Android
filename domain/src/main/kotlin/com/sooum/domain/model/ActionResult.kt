package com.sooum.domain.model

sealed class ActionResult<out T> {
    data class Success<out T>(val data: T) : ActionResult<T>()
    data class Fail(val msg: String) : ActionResult<Nothing>()
}