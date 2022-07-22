package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.databinding.ItemStatusGroupBinding
import com.uptime.kuma.models.MonitorItem
import com.uptime.kuma.models.StatusGroup

class StatusGroupAdapter(val context: Context, val listParent: List<StatusGroup>) :
    ListAdapter<StatusGroup,
            StatusGroupAdapter
            .StatusGroupViewHolder>(DiffCallBack()) {
    inner class StatusGroupViewHolder(
        private val biding: ItemStatusGroupBinding, val context:
        Context
    ) : RecyclerView.ViewHolder(biding.root) {

        fun bind(item: StatusGroup) {
            biding.apply {
                titleStatusGroup.text = listParent[adapterPosition].title
                biding.itemGroupStatusRecycler.apply {
                    setCallItemRecycler(
                        biding.itemGroupStatusRecycler,
                        listParent[adapterPosition].listOfServers
                    )
                }
            }
        }

    }

    private fun setCallItemRecycler(recyclerView: RecyclerView, list: List<MonitorItem>) {
        val adapter = ItemStatusGroupAdapter(context, list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusGroupViewHolder {
        val biding = ItemStatusGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StatusGroupViewHolder(biding, context)
    }

    override fun onBindViewHolder(holder: StatusGroupViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    class DiffCallBack : DiffUtil.ItemCallback<StatusGroup>() {
        override fun areItemsTheSame(oldItem: StatusGroup, newItem: StatusGroup): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StatusGroup, newItem: StatusGroup): Boolean {
            return oldItem.title == newItem.title
        }

    }
}