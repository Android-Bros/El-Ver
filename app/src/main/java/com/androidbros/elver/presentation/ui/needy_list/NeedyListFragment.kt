package com.androidbros.elver.presentation.ui.needy_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidbros.elver.adapter.NeedyListAdapter
import com.androidbros.elver.databinding.FragmentNeedyListBinding
import com.androidbros.elver.model.Requirement

class NeedyListFragment : Fragment() {

    private var _binding: FragmentNeedyListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NeedyListViewModel
    private lateinit var adapter: NeedyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNeedyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())

        adapter = NeedyListAdapter(this)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity()).get(NeedyListViewModel::class.java)
        observeData()
    }

    private fun observeData() {
        viewModel.animation.observe(viewLifecycleOwner, { value ->
            value?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.listOfRequirement.observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.requirement = it
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}