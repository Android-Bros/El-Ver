package com.androidbros.elver.presentation.ui.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androidbros.elver.R
import com.androidbros.elver.databinding.FragmentThirdScreenBinding
import com.androidbros.elver.util.SharedPref

class ThirdScreenFragment : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mySharedPref = SharedPref(requireContext())

        binding.buttonFinish.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            isOnBoardingFinish()
        }
    }

    private fun isOnBoardingFinish(){
        mySharedPref.setOnBoardingState(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}