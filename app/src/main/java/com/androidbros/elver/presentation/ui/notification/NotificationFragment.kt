package com.androidbros.elver.presentation.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androidbros.elver.R
import com.androidbros.elver.adapter.NotificationListAdapter
import com.androidbros.elver.databinding.FragmentNotificationBinding
import com.androidbros.elver.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotificationListAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.notification.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    adapter.notifications = it.data!!
                    binding.progressBar.visibility = View.GONE
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    binding.apply {
                        imageViewSad.setImageResource(R.drawable.sad)
                        imageViewSad.visibility = View.VISIBLE
                        textViewErrorMessage.text = it.errorMessage
                        textViewErrorMessage.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}