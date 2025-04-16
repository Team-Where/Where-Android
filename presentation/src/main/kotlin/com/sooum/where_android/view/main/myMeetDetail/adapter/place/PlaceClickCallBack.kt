package com.sooum.where_android.view.main.myMeetDetail.adapter.place

interface PlaceClickCallBack {
    fun likeChange(placeId: Int)
    fun startMapUri(uriString: String, marketPackage: String)
}

fun PlaceClickCallBack.startNaverMapUri(uriString: String) =
    startMapUri(uriString, "com.nhn.android.nmap")

fun PlaceClickCallBack.startKakaoMapUri(uriString: String) =
    startMapUri(uriString, "net.daum.android.map")