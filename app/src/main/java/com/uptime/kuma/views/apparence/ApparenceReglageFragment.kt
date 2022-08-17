package com.uptime.kuma.views.apparence

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentApparenceReglageBinding
import com.uptime.kuma.views.mainActivity.MainActivity


class ApparenceReglageFragment : Fragment(R.layout.fragment_apparence_reglage) {

    companion object {
        lateinit var language: String
    }

    private lateinit var binding: FragmentApparenceReglageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApparenceReglageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {
            switchDarkMode.isChecked = MainActivity.saveData.lightMode == "true"
            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    MainActivity.saveData.lightMode = "true"
                    restartApplication()
                } else {
                    MainActivity.saveData.lightMode = "false"
                    restartApplication()
                }
            }
        }

        val list = resources.getStringArray(R.array.list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, list)
        // Inflate the layout for this fragment
        binding.auto.setAdapter(arrayAdapter)
        binding.auto.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            when (binding.auto.text.toString()) {
                "English" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "en")
                    MainActivity.languageSettings.language = "en"
                }
                "French" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "fr")
                    MainActivity.languageSettings.language = "fr"
                }
                "Anglais" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "en")
                    MainActivity.languageSettings.language = "en"
                }
                "Francais" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "fr")
                    MainActivity.languageSettings.language = "fr"
                }

            }
        })

        return root
    }

    private fun restartApplication() {
        val intent = requireActivity().baseContext.packageManager.getLaunchIntentForPackage(
            requireActivity().baseContext.packageName
        )
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Process.killProcess(Process.myPid())
        System.exit(0)
    }


}