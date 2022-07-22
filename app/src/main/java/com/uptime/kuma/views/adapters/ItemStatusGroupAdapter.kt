package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.databinding.ItemAllServersFragmentBinding
import com.uptime.kuma.models.MonitorItem

class ItemStatusGroupAdapter(val context: Context, list: List<MonitorItem>) :
    ListAdapter<MonitorItem, ItemStatusGroupAdapter.ItemStatusGroupViewHolder>(DiffCallBack()) {
    inner class ItemStatusGroupViewHolder(
        private val biding: ItemAllServersFragmentBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(biding.root) {
        fun bind(item: MonitorItem) {
            biding.apply {
                percentText.text = item.percent
                titleText.text = item.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemStatusGroupViewHolder {
        val biding = ItemAllServersFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemStatusGroupViewHolder(biding, context)
    }

    override fun onBindViewHolder(holder: ItemStatusGroupViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallBack : DiffUtil.ItemCallback<MonitorItem>() {
        override fun areItemsTheSame(oldItem: MonitorItem, newItem: MonitorItem): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: MonitorItem, newItem: MonitorItem): Boolean {
            return oldItem.title == newItem.title && oldItem.percent == newItem.percent &&
                    oldItem.isOnline == newItem.isOnline
        }

    }
}