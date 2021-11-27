package com.androidbros.elver.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androidbros.elver.R
import com.androidbros.elver.databinding.FragmentLoginBinding
import com.androidbros.elver.presentation.ui.FlowActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            if (dataControl()) {
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextUserPassword.text.toString()

                viewModel.loginConfirmationStatus(email, password, requireContext())
                observeData()
            } else {
                Toast.makeText(requireContext(), R.string.fill_in_the_blanks, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.textViewSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun observeData() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layout.visibility = View.INVISIBLE
                } else {
                    binding.layout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.dataConfirmation.observe(viewLifecycleOwner, { dataConfirm ->
            dataConfirm?.let { confirm ->
                if (confirm) {
                    activity?.let {
                        val intent = Intent(it, FlowActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
        })
    }

    private fun dataControl(): Boolean =
        binding.editTextEmail.text.isNotEmpty() && binding.editTextUserPassword.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}