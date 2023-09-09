package com.macs.revitalize.presentation.settings.innerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.macs.revitalize.R
import com.macs.revitalize.databinding.FragmentSettingsAboutBinding

class FragmentSettingsAbout : Fragment() {
    private var _binding: FragmentSettingsAboutBinding?= null
    private val binding: FragmentSettingsAboutBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsAboutBinding.inflate(inflater, container, false)

        binding.settingsAboutGoBack.setOnClickListener{
            this.findNavController().navigate(FragmentSettingsAboutDirections.actionFragmentSettingsAboutToSettingsFragment())
        }


        return binding.root
    }

}