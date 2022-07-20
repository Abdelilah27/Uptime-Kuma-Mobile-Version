package com.uptime.kuma.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.databinding.ItemAllServersFragmentBinding
import com.uptime.kuma.models.MonitorItem

class MonitorItemAllServersAdapter(val context: Context, val listener: OnClickLister) :
    ListAdapter<MonitorItem, MonitorItemAllServersAdapter.ItemViewHolder>(DiffCallBack()) {

    inner class ItemViewHolder(
        private val biding: ItemAllServersFragmentBinding, val context:
        Context
    ) : RecyclerView
    .ViewHolder(biding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(item: MonitorItem) {
            biding.apply {
                percentText.text = item.percent
                titleText.text = item.title
                if (item.isOnline) {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_no_active_item_all_server_fragment
                        )
                    )
                } else {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_active_item_all_server_fragment
                        )
                    )
                }

            }
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val biding = ItemAllServersFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(biding, context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
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

    interface OnClickLister {
        fun onItemClick(position: Int)
    }


}