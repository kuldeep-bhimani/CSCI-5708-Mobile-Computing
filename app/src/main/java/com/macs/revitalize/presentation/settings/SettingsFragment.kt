package com.macs.revitalize.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.LoginActivity
import com.macs.revitalize.MainActivity
import com.macs.revitalize.R
import com.macs.revitalize.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding?= null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.settingsAboutView.setOnClickListener{
            this.findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToFragmentSettingsAbout())
        }

        binding.settingsContactView.setOnClickListener {
            this.findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToFragmentSettingsContact())
        }

        val userEmail = (activity as MainActivity).userEmail
        binding.signedInUser.text = userEmail

        auth = Firebase.auth
        binding.settingsSignOutBtn.setOnClickListener{
            auth.signOut()
            val parentActivity = requireActivity() as MainActivity
            parentActivity.goToLoginActivity()
        }


        return binding.root
    }

}