package com.androidbros.elver.presentation.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androidbros.elver.R
import com.androidbros.elver.databinding.FragmentHomeBinding
import com.androidbros.elver.util.SharedPref

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        binding.imageViewEmergencyInformation.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_emergencyInformationFragment)
        }

        binding.imageViewEmergency.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.send_emergency)
            builder.setMessage(R.string.are_you_sure_send_emergency_message)
            builder.setCancelable(true)
            builder.setNegativeButton(R.string.no) { _, _ -> }
            builder.setPositiveButton(R.string.yes) { _, _ ->
                val location = sharedPref.getCurrentLocation()
                viewModel.sendEmergencyNotification(location!!)
                viewModel.dataConfirmation.observe(viewLifecycleOwner, { confirm ->
                    confirm?.let {
                        if (confirm) {
                            Toast.makeText(requireContext(), R.string.success, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
                viewModel.error.observe(viewLifecycleOwner, { error ->
                    error?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                })
            }
            builder.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}