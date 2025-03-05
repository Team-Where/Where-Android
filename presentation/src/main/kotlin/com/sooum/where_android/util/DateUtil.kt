package com.sooum.where_android.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

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
}