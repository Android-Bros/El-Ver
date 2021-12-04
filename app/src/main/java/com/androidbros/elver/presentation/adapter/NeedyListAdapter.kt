package com.androidbros.elver.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.androidbros.elver.databinding.RequirementListItemBinding
import com.androidbros.elver.model.Requirement
import com.androidbros.elver.presentation.ui.needy_list.NeedyListFragmentDirections

class NeedyListAdapter(
    private val parentFragment: Fragment
) : RecyclerView.Adapter<NeedyListAdapter.NeedyListViewHolder>() {

    var requirement = listOf<Requirement>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class NeedyListViewHolder(val itemBinding: RequirementListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeedyListViewHolder {
        val binding =
            RequirementListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NeedyListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NeedyListViewHolder, position: Int) {
        val currentRequirement = requirement[position]
        holder.itemBinding.textViewBlanketState.text = currentRequirement.blanket.toString()
        holder.itemBinding.textViewClothesState.text = currentRequirement.clothes.toString()
        holder.itemBinding.textViewFoodAndWaterState.text = currentRequirement.foodAndWater.toString()
        holder.itemBinding.textViewMaterialState.text = currentRequirement.cleaningMaterial.toString()
        holder.itemBinding.textViewTentState.text = currentRequirement.tent.toString()
        holder.itemBinding.cardView.setOnClickListener {
            val action = NeedyListFragmentDirections.actionNeedyListFragmentToNeedyMapFragment(
                currentRequirement.location
            )
            parentFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return requirement.size
    }

}