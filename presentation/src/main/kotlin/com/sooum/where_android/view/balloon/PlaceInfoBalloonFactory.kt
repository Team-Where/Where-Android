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
 * 장소탭에서 info를 눌렀을대 노출되는 툴팁
 */
class PlaceInfoBalloonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return createBalloon(context) {
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(BalloonSizeSpec.WRAP)

            setText("친구들과 가기로 결정한 장소 목록입니다.")
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setTextSize(12f)
            ResourcesCompat.getFont(context, R.font.pretend_regular)?.let { setTextTypeface(it) }

            setArrowSize(10)
            setArrowOrientation(ArrowOrientation.TOP)
            setArrowPosition(0.92f)

            setMarginRight(7)
            setPaddingVertical(8)
            setPaddingHorizontal(10)

            setCornerRadius(6f)
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_scale_800))
            setBalloonAnimation(BalloonAnimation.OVERSHOOT)

            setLifecycleOwner(lifecycle)
        }
    }
}