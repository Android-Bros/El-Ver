package com.androidbros.elver.presentation.ui.requirement

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
import com.androidbros.elver.databinding.FragmentRequirementBinding

class RequirementFragment : Fragment() {

    private val args: RequirementFragmentArgs by navArgs()
    private lateinit var viewModel: RequirementViewModel
    private var _binding: FragmentRequirementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequirementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val location = args.location
        viewModel = ViewModelProvider(requireActivity()).get(RequirementViewModel::class.java)

        binding.buttonRequirementRequest.setOnClickListener {
            if (binding.editTextPeople.text.isNotEmpty()) {
                val howManyPeople: String = binding.editTextPeople.text.toString()
                val clothes: Boolean = binding.checkBoxClothes.isChecked
                val foodAndWater: Boolean = binding.checkBoxFoodWater.isChecked
                val cleaningMaterial: Boolean = binding.checkBoxCleaningMaterials.isChecked
                val tent: Boolean = binding.checkBoxTent.isChecked
                val blanket: Boolean = binding.checkBoxBlanket.isChecked
                viewModel.shareHealthRequirement(
                    requireContext(),
                    location,
                    howManyPeople,
                    clothes,
                    foodAndWater,
                    cleaningMaterial,
                    tent,
                    blanket
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
                    findNavController().navigate(R.id.action_requirementFragment_to_aidSelectFragment)
                    Toast.makeText(
                        requireContext(),
                        R.string.help_request_received,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}