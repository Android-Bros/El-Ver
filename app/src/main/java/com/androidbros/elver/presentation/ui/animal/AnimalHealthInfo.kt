package com.androidbros.elver.presentation.ui.animal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidbros.elver.R
import com.androidbros.elver.databinding.FragmentAnimalHealthInfoBinding

class AnimalHealthInfo : Fragment() {

    private val args: AnimalHealthInfoArgs by navArgs()
    private lateinit var viewModel: AnimalHealthViewModel
    private var _binding: FragmentAnimalHealthInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalHealthInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val location = args.location
        viewModel = ViewModelProvider(requireActivity()).get(AnimalHealthViewModel::class.java)

        binding.buttonAnimalHealthRequest.setOnClickListener {
            if (binding.editTextAnimalType.text.isNotEmpty()) {
                val burn: Boolean = binding.checkBoxBurn.isChecked
                val damage: Boolean = binding.checkBoxDamage.isChecked
                val tremor: Boolean = binding.checkBoxTremors.isChecked
                val animalType: String = binding.editTextAnimalType.text.toString()
                viewModel.shareAnimalHealth(
                    location,
                    animalType,
                    burn,
                    damage,
                    tremor
                )
                observeData()
            } else {
                Toast.makeText(requireContext(), R.string.fill_in_the_blanks, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun observeData() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let {
                if (it) {
                    binding.layout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.layout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
        viewModel.dataConfirmation.observe(viewLifecycleOwner, { dataConfirm ->
            dataConfirm?.let { confirm ->
                if (confirm) {
                    findNavController().navigate(R.id.action_animalHealthInfo_to_aidSelectFragment)
                    Toast.makeText(
                        requireContext(),
                        R.string.help_request_received,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        viewModel.error.observe(viewLifecycleOwner, { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}