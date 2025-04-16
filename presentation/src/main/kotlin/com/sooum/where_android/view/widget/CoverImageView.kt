package com.sooum.where_android.view.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sooum.domain.model.Meet
import com.sooum.domain.model.MeetDetail
import com.sooum.domain.model.SimpleMeet
import com.sooum.where_android.R

@Composable
fun MeetDetail.CoverImage(
    size: Dp,
    radius: Dp
) {
    CoverImageView(
        src = image,
        size = size,
        radius = radius
    )
}

@Composable
fun Meet.CoverImage(
    size: Dp,
    radius: Dp
) {
    CoverImageView(
        src = image,
        size = size,
        radius = radius
    )
}

@Composable
fun CoverImageView(
    src: String?,
    size: Dp,
    radius: Dp,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        src,
        contentDescription = null,
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(radius)),
        contentScale = ContentScale.Crop
    ) {
        val state by painter.state.collectAsState()
        if (state is AsyncImagePainter.State.Success) {
            SubcomposeAsyncImageContent()
        } else {
            Image(
                painter = painterResource(R.drawable.image_meet_default_cover),
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
    }
}

@Preview
@Composable
fun MeetDetailCoverImagePreview() {
    Meet(
        1,
        "2024 연말파티\uD83E\uDD42",
        "벌써 연말이다 신나게 놀아보장~~",
        "https://s3-alpha-sig.figma.com/img/c8d6/dcd3/d0cf1a8b848a3b713165544ecf9c6479?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=krYvjufSoqEAyn8acYCSG~-QUOROkCxVJUZM0JjokvSbO2Tcp9ukcsdTS0jCIhgFtpnODglNx-S-djkLy7DLJTwmX7gYCwEixyFT71peeBIssSulRl0~dMmtr8LPjmfPHAw2uADh7e~8WZJELBuE6gultPGoNSBFhEYdIXXoLgRscwHeJgwBTBjOYFf8N9pIQSwmSP-OsBdz9~LZQUKX1CisOb8yJtTx8SrPapdSMXYNickk~zQ7PaqfAeAXxyieTIGxSlNjp8QYzQhQrWcXkAFM9Y2xfNiArxvrJIKX-XykeplJAAIcwZ6U25H3UxA6F37LN7dBh1TjXr3VEwxEmA__"
    ).CoverImage(size = 170.dp, radius = 10.dp)
}