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
 * 장소가 없을때 장소 추가 하단에 노출되는 툴팁
 */
class PlaceAddBalloonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return createBalloon(context) {
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(BalloonSizeSpec.WRAP)

            setText("가장 먼저 장소를 공유해 보세요!")
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setTextSize(12f)
            ResourcesCompat.getFont(context, R.font.pretend_regular)?.let { setTextTypeface(it) }

            setArrowSize(10)
            setArrowOrientation(ArrowOrientation.TOP)
            setArrowPosition(0.92f)

            setMarginRight(66)
            setPaddingVertical(8)
            setPaddingHorizontal(10)

            setCornerRadius(6f)
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_scale_800))
            setBalloonAnimation(BalloonAnimation.OVERSHOOT)

            setLifecycleOwner(lifecycle)
        }
    }
}