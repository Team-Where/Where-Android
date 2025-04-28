package com.sooum.domain.repository

import com.sooum.domain.model.ActionResult
import com.sooum.domain.model.PlaceItem
import com.sooum.domain.model.PlaceWithUsers
import kotlinx.coroutines.flow.Flow

/**
 * 모임 상세화면에서 Place및 Comment 관련 처리
 */
interface MeetDetailPlaceRepository {
    fun getMeetPlaceList(): Flow<List<PlaceItem>>
    fun getMeetPlaceById(placeId: Int): Flow<PlaceItem?>

    /**
     * 장소를 추가합니다.
     */
    suspend fun addMeetPlace(
        meetId: Int,
        userId: Int,
        name: String,
        address: String,
    ): ActionResult<String>


    /**
     * 장소의 좋아요를 변경합니다.
     */
    suspend fun likeToggle(
        placeId: Int,
        userId: Int,
    ): ActionResult<*>


    fun loadMeetPlaceData(
        meetId: Int
    )

    /**
     * 해당 화면 탈출시
     */
    suspend fun clearPlaceData()


    /**
     * fcm 코드 101 장소추가일때 동작하는 함수 -> 이거 코드 틀린듯 수정해야할듯 meetingID에 대한 부분 수정해야함
     */
    suspend fun addPlaceFromFcm(meetId: Int, newPlace: PlaceWithUsers)

    /**
     * fcm 코드 103 장소삭제일 때 동작하는 함수
     */
    suspend fun deletePlaceFromFcm(id: Int)

    /**
     * fcm 코드 104 장소 pickStatus 변경
     */
    suspend fun updatePlaceStatusFromFcm(placeId: Int, newStatus: String)

    /**
     * fcm 코드 105 장소 좋아요 업데이트일 때 동작하는 함수
     */
    suspend fun updatePlaceLikeFromFcm(placeId: Int, placeLike: Int)
}