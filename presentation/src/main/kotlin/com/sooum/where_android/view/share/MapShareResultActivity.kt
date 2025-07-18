package com.sooum.where_android.view.share

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.sooum.domain.model.ShareResult
import com.sooum.where_android.WhereApp
import com.sooum.where_android.view.main.MainActivity
import com.sooum.where_android.view.main.myMeetDetail.MyMeetActivity
import kotlinx.serialization.json.Json


/**
 * 앱 실행중 여부를 체크하고 공유받은 데이터를 MyMeetActivity로 전달하기 위한 화면
 */
class MapShareResultActivity : Activity() {

    companion object {
        const val SHARE_RESULT = "shareResult"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = WhereApp.currentActivity?.get()

        when (activity) {
            is MyMeetActivity -> {
                runActivity(MyMeetActivity::class.java)
            }

            null,
            is MainActivity -> {
                runActivity(MainActivity::class.java)
            }
        }

        ActivityCompat.finishAffinity(this)
    }

    private fun runActivity(dest: Class<*>) {
        intent.parseMapShare()?.let { result ->
            val shareResultText = Json.encodeToString(result)
            startActivity(
                Intent(this, dest).apply {
                    putExtras(
                        Bundle().apply {
                            putString(SHARE_RESULT, shareResultText)
                        }
                    )
                }
            )
        }
    }

    /**
     * Intent 처리 관련 확장 함수
     */

    private fun Intent.parseMapShare(): ShareResult? {

        /**
         * [네이버 지도]
         * 신사소곱창 성수점
         * 서울 성동구 연무장길 5-9 1층, 2층
         * https://naver.me/G9r60mBj
         */

        /**
         * [카카오맵] 신사소곱창 성수점
         * 서울 성동구 연무장길 5-9 1-2층 (성수동2가)
         *
         * https://kko.kakao.com/thLO0uJMtd
         */
        if (this.action == Intent.ACTION_SEND && this.type == "text/plain") {
            val sharedText: String = this.getStringExtra(Intent.EXTRA_TEXT) ?: ""
            Log.d("JWH", "map Share\n$sharedText")
            return parseShareResult(sharedText)
        }

        return null
    }

    private fun parseShareResult(text: String): ShareResult? {
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }

        return when {
            lines.any { it.contains("[네이버 지도]") } -> {
                ShareResult(
                    source = "네이버",
                    placeName = lines.getOrNull(1)?.removePrefix("* ") ?: "",
                    address = lines.getOrNull(2) ?: "",
                    link = lines.find { it.startsWith("https://naver.me") } ?: ""
                )
            }

            lines.any { it.contains("[카카오맵]") } -> {
                ShareResult(
                    source = "카카오",
                    placeName = lines.getOrNull(0)?.substringAfter("] ") ?: "",
                    address = lines.getOrNull(1) ?: "",
                    link = lines.find { it.startsWith("https://kko.kakao.com") } ?: ""
                )
            }

            else -> null
        }
    }
}

