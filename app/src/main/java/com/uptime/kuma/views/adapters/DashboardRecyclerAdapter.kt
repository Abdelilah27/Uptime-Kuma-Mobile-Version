package com.uptime.kuma.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.uptime.kuma.R
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem
import com.uptime.kuma.utils.RecyclerClickInterface
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DashboardRecyclerAdapter(val context: Context, private val listener: RecyclerClickInterface) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.ItemViewHolder>() {

    private var myList: List<MonitorStatusItem> = listOf<MonitorStatusItem>()

    fun setData(data: List<MonitorStatusItem>) {
        myList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashbord_recylcer_item, parent, false)
        return ItemViewHolder(binding)
    }

    private fun formatData(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        var time: Long
        var now: Long
        var ago = ""
        try {
            time = sdf.parse(date).getTime()
            now = System.currentTimeMillis()
            ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                .toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ago
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myList[position].let {
            val formatDate = formatData(it.time!!)
            holder.nom.text = it.name
            holder.slug.text = it.name!!.toUpperCase().subSequence(0, 2)
            holder.date.text = formatDate
            holder.msg.text = it.msg
            holder.id.text = it.monitorID.toString()
            when (it.status) {
                0 -> {
                    holder.status.text = getActivity(context)?.getText(R.string.offline)
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.dashbord_fragment_hors_ligne
                        )
                    )
                    holder.ProfileCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.dashbord_fragment_hors_ligne
                        )
                    )
                }
                1 -> {
                    holder.status.text = getActivity(context)?.getText(R.string.online)
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.main_color
                        )
                    )
                    holder.ProfileCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.main_color
                        )
                    )
                }
                2 -> {
                    holder.status.text = getActivity(context)?.getText(R.string.attente)
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.attente
                        )
                    )
                    holder.ProfileCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.attente
                        )
                    )
                }
            }
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        var nom: TextView = view.findViewById(R.id.dashbord_servername_tv)
        var slug: TextView = view.findViewById(R.id.dashbord_slug_tv)
        var date: TextView = view.findViewById(R.id.dashbord_time_tv)
        var msg: TextView = view.findViewById(R.id.dashbord_message_tv)
        var status: TextView = view.findViewById(R.id.dashbord_status_tv)
        var card: CardView = view.findViewById(R.id.card_view_status)
        var ProfileCard: CardView = view.findViewById(R.id.profile_card)
        var id: TextView = view.findViewById(R.id.dashbord_id_text)
        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                myList[position].let { monitorStatusItem ->
                    listener.onItemClick(monitorStatusItem.monitorID!!)
                }
            }
        }
    }

    override fun getItemCount(): Int = myList.size
}