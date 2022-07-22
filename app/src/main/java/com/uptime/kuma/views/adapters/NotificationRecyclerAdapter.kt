package com.uptime.kuma.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.databinding.NotificationsRecyclerItemBinding
import com.uptime.kuma.models.NotificationItem

class NotificationRecyclerAdapter:ListAdapter<NotificationItem,NotificationRecyclerAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=NotificationsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentITem=getItem(position)
        holder.bind(currentITem)
    }

    class ViewHolder(val binding:NotificationsRecyclerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(notificationItem: NotificationItem){
            binding.apply {
                tvFragmentNotificationsName.text=notificationItem.name
                notificationSwitch.isChecked= notificationItem.isOn
            }
        }
    }

    class DiffCallback:DiffUtil.ItemCallback<NotificationItem>(){
        override fun areItemsTheSame(
            oldItem: NotificationItem,
            newItem: NotificationItem
        ): Boolean {
            return oldItem.name==newItem.name && oldItem.isOn==newItem.isOn
        }

        override fun areContentsTheSame(
            oldItem: NotificationItem,
            newItem: NotificationItem
        ): Boolean {
            return oldItem==newItem
        }
    }
}