package com.sooum.domain.usecase.user

import com.sooum.domain.model.ActionResult
import com.sooum.domain.provider.FcmTokenProvider
import com.sooum.domain.repository.UserRepository
import javax.inject.Inject

class UpdateFcmToken @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val userRepository: UserRepository,
    private val fcmTokenProvider: FcmTokenProvider
) {
    suspend operator fun invoke(fcmToken: String): ActionResult<Unit> {
        val userId = getLoginUserIdUseCase()
        if (userId == null) {
            //By Pass
            return ActionResult.Fail("User Id is null")
        } else {
            if (fcmTokenProvider.checkNotificationAllowed()) {
                //알림이 허용된 경우에만 업로드 한다.
                val savedFcmToken = fcmTokenProvider.getSavedFcmToken()
                if (savedFcmToken != fcmToken) {
                    //기존 토큰 값과 다른 경우에만 업데이트 요청
                    val result = userRepository.registerToken(userId, fcmToken)
                    if (result is ActionResult.Success) {
                        //신규 토큰으로 저장
                        fcmTokenProvider.setSavedFcmToken(fcmToken)
                        return ActionResult.Success(Unit)
                    }
                }
            }
            return ActionResult.Fail("")
        }
    }
}