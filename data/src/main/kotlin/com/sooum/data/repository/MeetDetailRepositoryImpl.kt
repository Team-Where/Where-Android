package com.sooum.data.repository

import com.sooum.domain.model.MeetDetail
import com.sooum.domain.repository.MeetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MeetDetailRepositoryImpl @Inject constructor(

) : MeetDetailRepository {
    override fun getMeetDetailList(): Flow<List<MeetDetail>> {
        return flow {
            val data = listOf(
                MeetDetail(
                    1,
                    "2024 연말파티\uD83E\uDD42",
                    "벌써 연말이다 신나게 놀아보장~~",
                    "https://s3-alpha-sig.figma.com/img/c8d6/dcd3/d0cf1a8b848a3b713165544ecf9c6479?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=krYvjufSoqEAyn8acYCSG~-QUOROkCxVJUZM0JjokvSbO2Tcp9ukcsdTS0jCIhgFtpnODglNx-S-djkLy7DLJTwmX7gYCwEixyFT71peeBIssSulRl0~dMmtr8LPjmfPHAw2uADh7e~8WZJELBuE6gultPGoNSBFhEYdIXXoLgRscwHeJgwBTBjOYFf8N9pIQSwmSP-OsBdz9~LZQUKX1CisOb8yJtTx8SrPapdSMXYNickk~zQ7PaqfAeAXxyieTIGxSlNjp8QYzQhQrWcXkAFM9Y2xfNiArxvrJIKX-XykeplJAAIcwZ6U25H3UxA6F37LN7dBh1TjXr3VEwxEmA__",
                    2025,
                    1,
                    26
                ),
                MeetDetail(
                    2,
                    "행궁동 갈 사람\uD83C\uDF42",
                    "선선해진 날씨에 같이 사람~!",
                    "https://s3-alpha-sig.figma.com/img/6ac8/c5c8/9f2f149c0501455fa4855cb04943f9fe?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=NF1~YtelsZsqCtVySJEl5vtnftoz3GCMnU7jy43fvLmj4KDx2wVBuk2XPnDXC2CyBRjjUQdU3p5viJMj2rAh6UIGhqx3NYxotA0ppXzXQGBS3o7DM63Pk1ESZ5GCtNOltrC6XixTtW7XXzzf4P3jdX0i2wKxVYK7bJ0HaYB8vhMkE3mQqUNQMvsVadSi8EAVfjaPGUfnxY9R-1HCQ2yGtBlrWWmogAE-kTNdJkk9shMw9xuinqniS4W8u0u3CO1ljw7DgDWYtrpZn6vxhelX~tWCmZql~BJzqrmytGjeB4Um2ShLRgftTUXemC7R3gZrofogNegNdzwICxb5SdFCmA__",
                    2024,
                    12,
                    11
                ),
                MeetDetail(
                    3,
                    "행궁동 갈 사람\uD83C\uDF42",
                    "선선해진 날씨에 같이 사람~!",
                    "https://s3-alpha-sig.figma.com/img/6ac8/c5c8/9f2f149c0501455fa4855cb04943f9fe?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=NF1~YtelsZsqCtVySJEl5vtnftoz3GCMnU7jy43fvLmj4KDx2wVBuk2XPnDXC2CyBRjjUQdU3p5viJMj2rAh6UIGhqx3NYxotA0ppXzXQGBS3o7DM63Pk1ESZ5GCtNOltrC6XixTtW7XXzzf4P3jdX0i2wKxVYK7bJ0HaYB8vhMkE3mQqUNQMvsVadSi8EAVfjaPGUfnxY9R-1HCQ2yGtBlrWWmogAE-kTNdJkk9shMw9xuinqniS4W8u0u3CO1ljw7DgDWYtrpZn6vxhelX~tWCmZql~BJzqrmytGjeB4Um2ShLRgftTUXemC7R3gZrofogNegNdzwICxb5SdFCmA__",
                    2025,
                    1,
                    27
                )
            ).sortedWith(
                comparator = compareBy({ -it.year }, { -it.month }, { -it.day })
            )
            emit(data)
        }
    }
}