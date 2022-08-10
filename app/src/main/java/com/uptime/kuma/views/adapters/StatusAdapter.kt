package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uptime.kuma.databinding.ItemStatusFragmentBinding
import com.uptime.kuma.models.status.Status

class StatusAdapter(val context: Context, val listener: OnClickLister) :
    ListAdapter<Status, StatusAdapter.ViewHolder>(
        StatusAdapter.DiffCallBack()
    ) {

    inner class ViewHolder(
        private val biding: ItemStatusFragmentBinding, val context:
        Context
    ) : RecyclerView
    .ViewHolder(biding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Status) {
            biding.apply {
                titleStatus.text = item.title
                pathStatus.text = "/status/"+item.slug
                Glide.with(imageStatus)
                    .load(item.icon)
                    .into(imageStatus)
            }
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val biding = ItemStatusFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(biding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    class DiffCallBack : DiffUtil.ItemCallback<Status>() {
        override fun areItemsTheSame(oldItem: Status, newItem: Status): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Status, newItem: Status): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickLister {
        fun onItemClick(position: Int)
    }
}