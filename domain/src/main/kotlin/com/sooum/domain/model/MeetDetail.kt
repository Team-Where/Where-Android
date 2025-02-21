package com.sooum.domain.model

data class MeetDetail(
    val id: Long,
    val title: String,
    val description: String,
    val image: String,
    val year: Int,
    val month: Int,
    val day: Int,
) {
    val date: String
        get() {
            val st = StringBuilder()
            st.append(year)
            st.append(".")

            if (month < 10) {
                st.append(0)
                st.append(month)
            } else {
                st.append(month)
            }
            st.append(".")

            if (day < 10) {
                st.append(0)
                st.append(day)
            } else {
                st.append(day)
            }

            return st.toString()
        }
}