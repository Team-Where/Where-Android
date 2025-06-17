package com.sooum.domain.usecase.user

import android.util.Log
import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.ImageAddType
import com.sooum.domain.repository.UserRepository
import com.sooum.domain.util.UriConverter
import javax.inject.Inject


class UpdateUserProfileUseCase @Inject constructor(
    private val getLoginUserIdUseCase: GetLoginUserIdUseCase,
    private val userRepository: UserRepository,
    private val uriConverter: UriConverter
) {
    suspend operator fun invoke(
        imageAddType: ImageAddType,
        prevImageSrc: String?
    ): ActionResult<String?> {
        val userId = getLoginUserIdUseCase()!!
        Log.d("JWH-A", "Type $imageAddType , $prevImageSrc")
        when (imageAddType) {
            is ImageAddType.Content -> {
                val file = uriConverter.saveContentUriToTempFile(imageAddType.uri)
                if (file == null) {
                    return ActionResult.Fail("이미지 변환에 실패했습니다.")
                } else {
                    if (prevImageSrc == null) {
                        //기존 이미지가 없던 경우
                        Log.d("JWH-A", "Type no to yes")
                        return userRepository.addProfile(userId, file)
                    } else {
                        //기존 이미지가 있던 경우
                        Log.d("JWH-A", "Type yes to yes")
                        return userRepository.editProfile(userId, file)
                    }
                }

            }

            is ImageAddType.Contents -> {
                //이 case는 없음
                return ActionResult.Fail("지원하지 않습니다")
            }

            ImageAddType.Default -> {
                return if (prevImageSrc == null) {
                    //기존 이미지가 없던 경우
                    ActionResult.Fail("지원하지 않습니다")
                } else {
                    //기존 이미지가 있던 경우
                    when (val delete = userRepository.deleteProfile(userId)) {
                        is ActionResult.Fail -> {
                            delete
                        }

                        is ActionResult.Success -> {
                            Log.d("JWH-A", "Type yes to no")
                            ActionResult.Success(null)
                        }
                    }
                }
            }
        }
    }

}