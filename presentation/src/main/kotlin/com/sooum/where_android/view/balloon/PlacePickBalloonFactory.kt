package com.sooum.where_android.view.balloon

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import com.sooum.where_android.R

/**
 * 장소상세에서 Pick 버튼 하단 노출되는 툴팁
 */
class PlacePickBalloonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return createBalloon(context) {
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(BalloonSizeSpec.WRAP)

            setText("이 장소로 정했다면 Pick을 눌러주세요!")
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setTextSize(14f)
            ResourcesCompat.getFont(context, R.font.pretend_regular)?.let { setTextTypeface(it) }

            setArrowSize(10)
            setArrowOrientation(ArrowOrientation.TOP)
            setArrowPosition(0.92f)

            //TODO 여기 마진을 조정해주세요
            setMarginRight(7)
            setPaddingVertical(8)
            setPaddingHorizontal(10)

            setCornerRadius(6f)
            setBackgroundColor(ContextCompat.getColor(context, R.color.main_color))
            setBalloonAnimation(BalloonAnimation.OVERSHOOT)

            setLifecycleOwner(lifecycle)
        }
    }
}