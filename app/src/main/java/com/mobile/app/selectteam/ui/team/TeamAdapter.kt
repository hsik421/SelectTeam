package com.mobile.app.selectteam.ui.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.clicks
import com.mobile.app.selectteam.data.local.UserData
import com.mobile.app.selectteam.databinding.ItemTeamBinding
import com.mobile.app.selectteam.ui.main.MainViewModel

class TeamAdapter(private val mainViewModel : MainViewModel) : ListAdapter<UserData, TeamAdapter.VIewHolder>(diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<UserData>(){
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem.idx == newItem.idx

            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VIewHolder(ItemTeamBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: VIewHolder, position: Int) {
        with(holder.binding){
            viewModel = mainViewModel
            pos = position
            user = getItem(position)
            executePendingBindings()
        }
    }
    class VIewHolder(val binding : ItemTeamBinding) : RecyclerView.ViewHolder(binding.root)
}