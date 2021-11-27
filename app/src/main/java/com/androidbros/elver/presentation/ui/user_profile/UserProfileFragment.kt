package com.androidbros.elver.presentation.ui.user_profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.androidbros.elver.R
import com.androidbros.elver.UserModel
import com.androidbros.elver.databinding.FragmentUserProfileBinding
import com.androidbros.elver.presentation.ui.MainActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel
    private var userProfileInfo: UserModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(UserProfileViewModel::class.java)

        viewModel.getProfileInfo(requireContext())
        observeData()

        binding.buttonDeleteMyAccount.setOnClickListener {
            Snackbar.make(
                it,
                R.string.are_you_want_to_delete_account,
                Snackbar.LENGTH_LONG
            ).setAction(R.string.yes) {
                viewModel.deleteAccount(requireContext())
                observeDataDeleteAccount()
            }.show()
        }

    }

    private fun observeData() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let { value ->
                if (value) {
                    binding.layout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.layout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.dataConfirmation.observe(viewLifecycleOwner, { dataConfirm ->
            dataConfirm?.let { data ->
                if (data) {
                    userProfileInfo = viewModel.userInfo
                    binding.editTextName.setText(userProfileInfo!!.name)
                    binding.editTextSurname.setText(userProfileInfo!!.surname)
                    binding.editTextEMail.setText(userProfileInfo!!.email)
                    binding.editTextPhone.setText(userProfileInfo!!.phoneNumber)
                    if (userProfileInfo!!.profileImage != null) {
                        Glide.with(requireActivity()).load(userProfileInfo!!.profileImage)
                            .placeholder(R.drawable.userprofile)
                            .into(binding.imageViewUserProfile)
                    } else {
                        binding.imageViewUserProfile.setImageDrawable(
                            ActivityCompat.getDrawable(requireContext(), R.drawable.userprofile)
                        )
                    }
                    binding.layout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.layout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeDataDeleteAccount() {
        viewModel.animation.observe(viewLifecycleOwner, { animation ->
            animation?.let { value ->
                if (value) {
                    binding.layout.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.layout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.deleteAccountState.observe(viewLifecycleOwner, { dataConfirm ->
            dataConfirm?.let { data ->
                if (data) {
                    activity?.let {
                        val intent = Intent(it, MainActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}