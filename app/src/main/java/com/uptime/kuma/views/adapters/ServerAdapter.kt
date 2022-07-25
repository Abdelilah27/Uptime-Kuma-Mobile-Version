package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.databinding.DashbordRecylcerItemBinding
import com.uptime.kuma.models.DashbordItems

class ServerAdapter(val context: Context) :
    ListAdapter<DashbordItems, ServerAdapter.ServerViewHolder>(DiffCallback()) {
    inner class ServerViewHolder(private val binding: DashbordRecylcerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dashbordItems: DashbordItems) {
            binding.apply {
                dashbordServernameTv.text = dashbordItems.name
                if (dashbordItems.status) {
                    dashbordStatusTv.text = "En ligne"
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .main_color
                        )
                    )
                } else {
                    dashbordStatusTv.text = "Hors ligne"
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .dashbord_fragment_hors_ligne
                        )
                    )
                }
                dashbordTimeTv.text = dashbordItems.date
                dashbordMessageTv.text = dashbordItems.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val binding =
            DashbordRecylcerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<DashbordItems>() {
        override fun areItemsTheSame(oldItem: DashbordItems, newItem: DashbordItems): Boolean {
            return oldItem.name == newItem.name && oldItem.status == newItem.status && oldItem.date == newItem.date && oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: DashbordItems, newItem: DashbordItems) =
            oldItem == newItem
    }
}