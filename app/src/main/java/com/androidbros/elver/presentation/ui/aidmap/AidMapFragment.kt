package com.androidbros.elver.presentation.ui.aidmap

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.androidbros.elver.R
import com.androidbros.elver.databinding.FragmentAidMapBinding
import com.androidbros.elver.util.SharedPref
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AidMapFragment : Fragment(), GoogleMap.OnMapLongClickListener {



    private lateinit var binding: FragmentAidMapBinding
    private lateinit var location: String
    private lateinit var mMap: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setOnMapLongClickListener(this)
        mMap = googleMap
        val mySharedPref = SharedPref(requireContext())
        val sharedloc = mySharedPref.getCurrentLocation()
        val lat = sharedloc!!.split(",")[0]
        val lon = sharedloc!!.split(",")[1]
        val location = LatLng(lat.toDouble(), lon.toDouble())
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,10f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAidMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        val bundle = arguments
        val args = AidMapFragmentArgs.fromBundle(bundle!!)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Konum")
        if (args.aidselect == "animal") {
            builder.setMessage(getString(R.string.animal))
            builder.setPositiveButton("Mevcut Konumumu Kullan") { dialog, which ->
                val mySharedPref = SharedPref(requireContext())
                val sharedloc = mySharedPref.getCurrentLocation()
                val action = AidMapFragmentDirections.actionAidMapFragmentToAnimalHealthInfo(sharedloc!!)
                Navigation.findNavController(view).navigate(action)
            }
            builder.setNegativeButton("Farkl?? Bir Konum Kullan") { dialog, which ->
            }
            builder.show()
            binding.buttonGoRequirementOrAnimal.setOnClickListener {
                val action = AidMapFragmentDirections.actionAidMapFragmentToAnimalHealthInfo(location)
                Navigation.findNavController(view).navigate(action)
            }
        } else if (args.aidselect == "requirement") {
            builder.setMessage(getString(R.string.requirement))
            builder.setPositiveButton("Mevcut Konumumu Kullan") { dialog, which ->
                val mySharedPref = SharedPref(requireContext())
                val sharedloc = mySharedPref.getCurrentLocation()
                val action = AidMapFragmentDirections.actionAidMapFragmentToRequirementFragment(sharedloc!!)
                Navigation.findNavController(view).navigate(action)
            }
            builder.setNegativeButton("Farkl?? Bir Konum Kullan") { dialog, which ->
            }
            builder.show()

            binding.buttonGoRequirementOrAnimal.setOnClickListener {
                val action = AidMapFragmentDirections.actionAidMapFragmentToRequirementFragment(location)
                Navigation.findNavController(view).navigate(action)
            }
        }

    }

    override fun onMapLongClick(p0: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0))
        location = p0.latitude.toString() + "," + p0.longitude.toString()
        binding.buttonGoRequirementOrAnimal.visibility = View.VISIBLE
    }

}