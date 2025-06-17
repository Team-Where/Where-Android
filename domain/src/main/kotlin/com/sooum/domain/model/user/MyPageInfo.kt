package com.sooum.domain.model.user

data class MyPageInfo(
    var email: String = "",
    var nickname: String = "",
    var imageSrc: String? = null,
    var existsByNickName: Boolean = false
)