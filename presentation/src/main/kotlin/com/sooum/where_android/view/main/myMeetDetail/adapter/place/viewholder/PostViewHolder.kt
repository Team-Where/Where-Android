package com.sooum.where_android.view.main.myMeetDetail.adapter.place.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.PlaceItem
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding
import com.sooum.where_android.databinding.LayoutPlaceDefaultBinding
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.PlaceClickCallBack
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.startKakaoMapUri
import com.sooum.where_android.view.main.myMeetDetail.adapter.place.callback.startNaverMapUri

/**
 * 기본 place 아이템 홀더 [ItemUnselectedPlaceBinding]을 참고
 */
class PostViewHolder(
    private val binding: ItemUnselectedPlaceBinding,
    private val placeClickCallBack: PlaceClickCallBack?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(placeItem: PlaceItem) {
        val place = placeItem.place
        with(binding) {
            contentArea.initDefaultPlace(placeItem, placeClickCallBack)
            if (place.together) {
                textTogether.visibility = View.VISIBLE
            } else {
                textTogether.visibility = View.GONE
            }
        }
    }

}

/**
 * LayoutPlaceDefaultBinding에 대한 공통 연결자 확장 함수
 */
fun LayoutPlaceDefaultBinding.initDefaultPlace(
    placeItem: PlaceItem,
    placeClickCallBack: PlaceClickCallBack?
) {
    val place = placeItem.place
    textPlaceName.text = place.name
    textAddress.text = place.address
    with(place.myLike) {
        textLikeNumber.isSelected = this
        textLike.isSelected = this
        iconHeart.isSelected = this
    }

    textLikeNumber.text = place.likeCount.toString()

    heartArea.setOnClickListener {
        placeClickCallBack?.likeChange(place.id)
    }

    btnNaverMap.setOnClickListener {
        placeClickCallBack?.startNaverMapUri(place.naverLink)
    }

    btnKakaoMap.setOnClickListener {
        placeClickCallBack?.startKakaoMapUri(place.kakaoLink)
    }

    root.setOnClickListener {
        placeClickCallBack?.clickPlace(place.id)
    }

    textCommentNumber.text = placeItem.commentCount.toString()
}