package com.uptime.kuma.Adapters

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

class DashbordRecyclerAdapter(val context: Context):ListAdapter<DashbordItems,DashbordRecyclerAdapter.ItemViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =DashbordRecylcerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem=getItem(position)
        holder.bind(currentItem)
    }


    inner class ItemViewHolder(private val binding: DashbordRecylcerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(dashbordItems: DashbordItems){
            binding.apply {
                dashbordServernameTv.text=dashbordItems.name
                if (dashbordItems.status) {
                    dashbordStatusTv.text = "En ligne"
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .main_color
                        )
                    )
                }else {
                    dashbordStatusTv.text = "Hors ligne"
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .dashbord_fragment_hors_ligne
                        )
                    )                }
                dashbordTimeTv.text=dashbordItems.date
                dashbordMessageTv.text=dashbordItems.message
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DashbordItems>(){
        override fun areItemsTheSame(oldItem: DashbordItems, newItem: DashbordItems) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DashbordItems, newItem: DashbordItems) = oldItem == newItem

    }
}