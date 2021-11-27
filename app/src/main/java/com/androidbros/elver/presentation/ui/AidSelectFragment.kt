package com.androidbros.elver.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.androidbros.elver.databinding.FragmentAidSelectBinding

class AidSelectFragment : Fragment() {

    private lateinit var binding: FragmentAidSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAidSelectBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardViewAnimal.setOnClickListener {
            val action =
                AidSelectFragmentDirections.actionAidSelectFragmentToAidMapFragment("animal")
            Navigation.findNavController(it).navigate(action)
        }

        binding.cardViewPeople.setOnClickListener {
            val action =
                AidSelectFragmentDirections.actionAidSelectFragmentToAidMapFragment("requirement")
            Navigation.findNavController(it).navigate(action)
        }

    }

}