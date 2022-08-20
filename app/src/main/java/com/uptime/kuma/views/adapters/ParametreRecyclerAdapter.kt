package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.databinding.ParametreRecyclerItemBinding
import com.uptime.kuma.models.parametreItem.ParametreItem

class ParametreRecyclerAdapter (val context: Context, private val listener : OnItemClickListener) :
    ListAdapter<ParametreItem,ParametreRecyclerAdapter.ParametreViewHolder>(DifCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParametreViewHolder {
        val binding =ParametreRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return ParametreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParametreViewHolder, position: Int) {
       val currentItem=getItem(position)
        holder.bind(currentItem)
    }

    inner class ParametreViewHolder(private val binding:ParametreRecyclerItemBinding):
        RecyclerView.ViewHolder(binding.root),View.OnClickListener
    {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position:Int =adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


        fun bind(Parametre: ParametreItem){
            binding.apply {
                ParamName.text=Parametre.paramname
            }
        }
    }

     class DifCallback:DiffUtil.ItemCallback<ParametreItem>(){
        override fun areItemsTheSame(oldItem: ParametreItem, newItem: ParametreItem)
        =oldItem.paramname==newItem.paramname

        override fun areContentsTheSame(oldItem: ParametreItem, newItem: ParametreItem)
        =oldItem==newItem

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}