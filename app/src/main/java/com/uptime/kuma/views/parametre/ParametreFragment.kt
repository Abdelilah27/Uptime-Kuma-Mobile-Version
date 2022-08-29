package com.uptime.kuma.views.parametre

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentParametreBinding
import com.uptime.kuma.models.parametreItem.ParametreItem
import com.uptime.kuma.utils.RestartApp
import com.uptime.kuma.utils.SessionManagement
import com.uptime.kuma.views.adapters.ParametreRecyclerAdapter
import com.uptime.kuma.views.mainActivity.MainActivity
import kotlin.system.exitProcess

class ParametreFragment : Fragment(R.layout.fragment_parametre),
    ParametreRecyclerAdapter.OnItemClickListener, RestartApp {
    lateinit var sessionManagement: SessionManagement
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentParametreBinding.bind(view)

        val parametreRecyclerAdapter = ParametreRecyclerAdapter(requireContext(), this)

        sessionManagement = SessionManagement(requireContext())
        binding.apply {
            parametreRecycler.apply {
                adapter = parametreRecyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                parametreRecyclerAdapter.submitList(getData())
            }
            buttonLogout.setOnClickListener {
                onCommencerFinished()
                sessionManagement.logOut()
                restartApplication()


            }
        }
    }

    override fun onItemClick(position: Int) {
        when (getData()[position].paramname) {
            "About" -> MainActivity.navController.navigate(R.id.aboutFragment)
            "Apparence" -> MainActivity.navController.navigate(R.id.apparenceReglageFragment)
        }
    }

    private fun getData(): List<ParametreItem> {
        val data = arrayListOf<ParametreItem>()
        data.add(ParametreItem("Apparence"))
        data.add(ParametreItem("About"))
        return data
    }

    override fun restartApplication() {
        val intent = requireActivity().baseContext.packageManager.getLaunchIntentForPackage(
            requireActivity().baseContext.packageName
        )
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }
    private fun onCommencerFinished(){
        val sharedpref = requireActivity().getSharedPreferences("Bienvenue", Context.MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.putBoolean("Finished", false)
        editor.apply()
    }

}