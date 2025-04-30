package com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback

/**
 * 장소에서 클릭 액션 콜백
 */
interface PlaceClickCallBack {
    /**
     * 좋아요 토클
     */
    fun likeChange(placeId: Int)

    /**
     * 장소 버튼 눌럿을떄 액션
     */
    fun startMapUri(uriString: String, marketPackage: String)

    /**
     * 장소를 눌렀을때
     */
    fun clickPlace(placeId: Int)
}

/**
 * 네이버 uri 오픈 요청시
 */
fun PlaceClickCallBack.startNaverMapUri(uriString: String) =
    startMapUri(uriString, "com.nhn.android.nmap")

/**
 * kakao uri 오픈 요청시
 */
fun PlaceClickCallBack.startKakaoMapUri(uriString: String) =
    startMapUri(uriString, "net.daum.android.map")