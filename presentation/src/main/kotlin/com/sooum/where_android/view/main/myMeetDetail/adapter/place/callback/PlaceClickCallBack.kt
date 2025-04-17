package com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback

/**
 * 장소에서 클릭 액션 콜백
 */
interface PlaceClickCallBack {
    fun likeChange(placeId: Int)
    fun startMapUri(uriString: String, marketPackage: String)
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