package com.sooum.where_android.view.myMeetDetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sooum.domain.model.SelectedPlace
import com.sooum.where_android.databinding.ItemSelectedPlaceBinding

class SelectedPlaceListAdapter() : RecyclerView.Adapter<SelectedPlaceListAdapter.MyView>() {
    private var selectedPlaceList:  List<SelectedPlace> = emptyList()

    inner class MyView(private val binding: ItemSelectedPlaceBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(selectedPlaceModel: SelectedPlace) {
            binding.textPlaceName.text = selectedPlaceModel.restaurantName
            binding.textAddress.text = selectedPlaceModel.restaurantAddress
            binding.textCommentNumber.text = selectedPlaceModel.commentCount.toString()
            binding.textLikeNumber.text = selectedPlaceModel.heartCount.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {
        val view = ItemSelectedPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(selectedPlaceList[position])
    }

    override fun getItemCount(): Int {
        return selectedPlaceList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<SelectedPlace>) {
        selectedPlaceList = list
        notifyDataSetChanged()
    }
}