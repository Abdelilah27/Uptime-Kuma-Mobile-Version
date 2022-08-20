package com.uptime.kuma.views.adapters

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
import com.uptime.kuma.models.monitor.Monitor

class MonitorItemAllServersAdapter(val context: Context, val listener: OnClickLister) :
    ListAdapter<Monitor, MonitorItemAllServersAdapter.ItemViewHolder>(DiffCallBack()) {
    inner class ItemViewHolder(
        private val biding: ItemAllServersFragmentBinding, val context:
        Context
    ) : RecyclerView
    .ViewHolder(biding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Monitor) {
            biding.apply {
                idText.text = item.id.toString()
                percentText.text = "100%" // TODO
                titleText.text = item.name
                allServersSlugTv.text = item.name.toUpperCase().subSequence(0, 2)
                when (item.active) {
                    0 -> {
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_no_active_item_all_server_fragment
                            )
                        )
                        profileCardServers.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_no_active_item_all_server_fragment
                            )
                        )
                    }
                    1 -> {
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_active_item_all_server_fragment
                            )
                        )
                        profileCardServers.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_active_item_all_server_fragment
                            )
                        )
                    }
                    else -> {
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .attente
                            )
                        )
                        profileCardServers.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .attente
                            )
                        )
                    }

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

    class DiffCallBack : DiffUtil.ItemCallback<Monitor>() {
        override fun areItemsTheSame(oldItem: Monitor, newItem: Monitor): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Monitor, newItem: Monitor): Boolean {
            return oldItem == newItem
        }

    }

    interface OnClickLister {
        fun onItemClick(position: Int)
    }


}