package com.uptime.kuma.views.bienvenue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uptime.kuma.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BienvenueFragment : Fragment(R.layout.fragment_bienvenue) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.getSupportActionBar()!!.hide()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}