package com.sooum.domain.model.user

data class MyPageInfo(
    val email: String = "",
    val nickname: String = "",
    val imageSrc: String? = null,
    val existsByNickName: Boolean = false
)