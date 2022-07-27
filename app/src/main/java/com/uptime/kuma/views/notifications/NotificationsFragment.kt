package com.uptime.kuma.views.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentNotificationsBinding
import com.uptime.kuma.models.NotificationItem
import com.uptime.kuma.views.adapters.NotificationRecyclerAdapter


class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=com.uptime.kuma.databinding.FragmentNotificationsBinding.bind(view)

        val notificationRecyclerAdapter=NotificationRecyclerAdapter()
        binding.apply {
            notificationRecycler.apply {
                adapter=notificationRecyclerAdapter
                layoutManager=LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                notificationRecyclerAdapter.submitList(getData())
            }
        }
    }

    private fun getData():List<NotificationItem>{
        val data  = arrayListOf<NotificationItem>()
        data.add(
            NotificationItem("Notification Email",true),
        )
        data.add(
            NotificationItem("Mobiblanc.World",true)
        )
        data.add(
            NotificationItem("Notification Google Chat Devops Notification Google Chat Devops Notification Google Chat Devops",false)
        )
        data.add(
            NotificationItem("Notification Rocket.chat",true)
        )
        return data
    }
}