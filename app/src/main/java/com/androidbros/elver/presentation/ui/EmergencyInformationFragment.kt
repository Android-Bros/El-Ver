package com.androidbros.elver.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androidbros.elver.R
import com.androidbros.elver.databinding.FragmentEmergencyInformationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class EmergencyInformationFragment : Fragment() {

    private var _binding: FragmentEmergencyInformationBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmergencyInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavView.visibility = View.GONE

        binding.buttonGotIt.setOnClickListener {
            findNavController().navigate(R.id.action_emergencyInformationFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomNavView.visibility = View.VISIBLE
        _binding = null
    }
}

