package com.example.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.recyclerview.databinding.DialogAddContactBinding
import java.util.UUID

class AddContactDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {
            val firstName = binding.editTextFirstName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val phoneNumber = binding.editTextPhoneNumber.text.toString()

            // Проверка на пустые поля
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()) {
                val contact = Contact(UUID.randomUUID(), firstName, lastName, phoneNumber)

                val activity = activity
                if (activity is AddContactListener) {
                    activity.onContactAdded(contact)
                }
                dismiss()
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    interface AddContactListener {
        fun onContactAdded(contact: Contact)
    }
}