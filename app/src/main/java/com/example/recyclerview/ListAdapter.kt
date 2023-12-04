package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.databinding.ItemUserBinding

private lateinit var binding: ItemUserBinding

class ContactAdapter : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: Contact) {
            binding.textViewFirstName.text = user.firstName
            binding.textViewLastName.text = user.lastName
            binding.textPhoneNumber.text = user.phoneNumber
        }
    }

    private class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}