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
        if (currentRequirement.clothes) {
            holder.itemBinding.textViewClothes.text = "Giyim: Evet"
        } else {
            holder.itemBinding.textViewClothes.text = "Giyim: Hayır"
        }
        if (currentRequirement.foodAndWater) {
            holder.itemBinding.textViewFoodWater.text = "Gıda ve Su: Evet"
        } else {
            holder.itemBinding.textViewFoodWater.text = "Gıda ve Su: Hayır"
        }
        if (currentRequirement.cleaningMaterial) {
            holder.itemBinding.textViewCleaningMaterial.text = "Hijyen Malzemeleri: Evet"
        } else {
            holder.itemBinding.textViewCleaningMaterial.text = "Hijyen Malzemeleri: Hayır"
        }
        if (currentRequirement.tent) {
            holder.itemBinding.textViewTent.text = "Çadır: Evet"
        } else {
            holder.itemBinding.textViewTent.text = "Çadır: Hayır"
        }
        if (currentRequirement.blanket) {
            holder.itemBinding.textViewBlanket.text = "Uyku Tulumu veya Battaniye: Evet"
        } else {
            holder.itemBinding.textViewBlanket.text = "Uyku Tulumu veya Battaniye: Hayır"
        }

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