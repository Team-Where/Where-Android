package com.sooum.domain.model

/**
 * 사용자 목록을 나타내는 model
 */
data class User(
    val id: Long,
    val name: String,
    val profileImage: String = ""
)