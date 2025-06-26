package com.sooum.where_android.view.hamburger.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.NotificationItem
import com.sooum.where_android.databinding.ItemNotificationBinding
import com.sooum.where_android.util.NotificationDiffUtil
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class NotificationRecyclerView(
    private val items: List<NotificationItem>
) : ListAdapter<NotificationItem, NotificationRecyclerView.ViewHolder>(NotificationDiffUtil) {

    inner class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationItem) = with(binding) {
            textTitle.text = item.title
            textDescription.text = item.description
            textTime.text = formatReceiveTime(item.receiveTime)

        }

        private fun formatReceiveTime(time: String): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val now = LocalDateTime.now()

            return try {
                val receivedTime = LocalDateTime.parse(time, formatter)
                val duration = Duration.between(receivedTime, now)

                when {
                    duration.toMinutes() < 1 -> "지금"
                    duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
                    duration.toHours() < 24 -> "${duration.toHours()}시간 전"
                    receivedTime.toLocalDate() == now.minusDays(1).toLocalDate() -> "어제"
                    duration.toDays() < 7 -> "${duration.toDays()}일 전"
                    else -> receivedTime.toLocalDate().toString()
                }
            } catch (e: Exception) {
                time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
