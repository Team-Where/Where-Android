package com.sooum.data.network.auth.request

import kotlinx.serialization.Serializable


/**
 * 회원가입시 사용하는 model
 * @param[email] 이메일
 * @param[password] 비밀번호
 * @param[name] 이름
 * @param[profileImage] 프로필 이미지
 */
@Serializable
data class SignUpRequest (
    val email: String,
    val password: String,
    val nickName: String,
    val profileImage: String
)