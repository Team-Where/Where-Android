package com.sooum.where_android.view.main.myMeetDetail.adapter.place.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.PlaceItem
import com.sooum.where_android.databinding.ItemUnselectedPlaceBinding
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
            updateLikeUI(place.myLike, place.likeCount)
            with(contentArea) {
                textPlaceName.text = place.name
                textAddress.text = place.address

                heartArea.setOnClickListener {
                    placeClickCallBack?.likeChange(place.id)
                }

                btnNaverMap.setOnClickListener {
                    placeClickCallBack?.startNaverMapUri(place.naverLink)
                }

                btnKakaoMap.setOnClickListener {
                    placeClickCallBack?.startKakaoMapUri(place.kakaoLink)
                }

                textCommentNumber.text = placeItem.commentCount.toString()
            }

            if (place.together) {
                textTogether.visibility = View.VISIBLE
            } else {
                textTogether.visibility = View.GONE
            }
        }
    }

    private fun updateLikeUI(isLiked: Boolean, likeCount: Int) {
        with(binding.contentArea) {
            textLikeNumber.isSelected = isLiked
            textLike.isSelected = isLiked
            iconHeart.isSelected = isLiked
            textLikeNumber.text = if (likeCount > 0) likeCount.toString() else ""
        }
    }
}
