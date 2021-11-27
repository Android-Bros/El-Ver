package com.androidbros.elver.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidbros.elver.databinding.NotificationListItemBinding
import com.androidbros.elver.model.Notification

class NotificationListAdapter : RecyclerView.Adapter<NotificationListAdapter.MyViewHolder>() {

    var notifications = listOf<Notification>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(private val binding: NotificationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notification) {
            binding.apply {
                textViewMessageTitle.text = item.messageTitle
                textViewMessage.text = item.message
                textViewOrganization.text = item.organization
                textViewCreatedAt.text = item.createdAt
            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NotificationListItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = notifications[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = notifications.size

}