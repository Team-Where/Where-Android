package com.sooum.where_android.util

import android.os.Build
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDate
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {
    // 해당 연도와 월의 모든 날짜 리스트 반환 (빈 칸 포함)
    fun getDaysOfMonthWithOffset(year: Int, month: Int): List<LocalDate?> {
        val firstDay = LocalDate(year, month, 1)
        val firstDayOfWeek = firstDay.dayOfWeek.ordinal // 0(월) ~ 6(일) → 한국 기준 일요일이 6

        val days = mutableListOf<LocalDate?>()

        // 빈 칸 추가 (일요일부터 시작해야 하므로 shift 조정)
        repeat((firstDayOfWeek + 1) % 7) { days.add(null) }

        // 날짜 추가
        var currentDay = firstDay
        val nextMonth = if (month == 12) 1 else month + 1
        val nextYear = if (month == 12) year + 1 else year
        val firstDayOfNextMonth = LocalDate(nextYear, nextMonth, 1)

        while (currentDay < firstDayOfNextMonth) {
            days.add(currentDay)
            currentDay = currentDay.plus(1, DateTimeUnit.DAY)
        }

        return days
    }

    fun getWeekString(
        localDate: LocalDate,
        local :Locale = Locale.KOREA
    ): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, local)
        } else {
            val calendar = Calendar.getInstance().apply {
                set(
                    /* year = */ localDate.year,
                    /* month = */ localDate.monthNumber - 1,
                    /* date = */ localDate.dayOfMonth
                )
            }

            // Format the day of the week in a specific locale
            val sdf = SimpleDateFormat("EEEE", local)
            val dayName = sdf.format(calendar.time)

            return  dayName
        }


    }
}