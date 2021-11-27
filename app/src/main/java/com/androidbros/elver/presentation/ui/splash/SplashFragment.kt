package com.androidbros.elver.presentation.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.androidbros.elver.R
import com.androidbros.elver.databinding.FragmentSplashBinding
import com.androidbros.elver.presentation.ui.FlowActivity
import com.androidbros.elver.util.Constants.SPLASH_TIME
import com.androidbros.elver.util.SharedPref
import com.androidbros.elver.util.internetAlertDialogShow

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPref: SharedPref
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        mySharedPref = SharedPref(requireContext())

        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.autoLoginStatus()
            observeLoginStatus()
        }, SPLASH_TIME)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeInternet() {
        context?.let {
            viewModel.internetStatusCheck(it)
        }
        viewModel.internetStatus.observe(viewLifecycleOwner, Observer {
            if (it) {
                observeLocation()
            } else if (!it) {
                context?.let { it -> internetAlertDialogShow(it) }
            }
        })
    }

    private fun observeLocation() {
        context?.let {
            viewModel.getLocation(it)
        }
        viewModel.location.observe(viewLifecycleOwner, Observer {
            val lltlng = it.latitude.toString() + "," + it.longitude.toString()
            mySharedPref.setCurrentLocation(lltlng)
        })
    }

    private fun observeLoginStatus() {
        viewModel.isThereEntry.observe(viewLifecycleOwner, { value ->
            value?.let { data ->
                if (data) {
                    activity?.let {
                        val intent = Intent(requireActivity(), FlowActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                } else {
                    if (mySharedPref.loadOnBoardingState()) {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    } else {
                        findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                    }
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 2) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                observeInternet()
            } else {
                Toast.makeText(context, getString(R.string.permissionDenied), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}