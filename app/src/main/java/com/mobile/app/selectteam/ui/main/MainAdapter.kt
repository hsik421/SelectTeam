package com.mobile.app.selectteam.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.app.selectteam.R
import com.mobile.app.selectteam.data.local.UserData
import com.mobile.app.selectteam.databinding.ItemTeamBinding
import com.mobile.app.selectteam.ui.team.TeamAdapter

class MainAdapter : ListAdapter<UserData, MainAdapter.VIewHolder>(diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<UserData>(){
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem.idx == newItem.idx

            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem.num == newItem.num
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VIewHolder(ItemTeamBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: VIewHolder, position: Int) {
        with(holder.binding){
            pos = position
            user = getItem(position)
            executePendingBindings()
        }
    }

    class VIewHolder(val binding : ItemTeamBinding) : RecyclerView.ViewHolder(binding.root)
}