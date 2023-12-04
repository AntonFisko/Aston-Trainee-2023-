package com.example.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

            val newContact = Contact(
                UUID.randomUUID().toString(),
                firstName,
                lastName,
                phoneNumber
            )

            val activity = activity as? AddContactListener
            activity?.onContactAdded(newContact)
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val KEY_FIRST_NAME = "firstName"
        private const val KEY_LAST_NAME = "lastName"
        private const val KEY_ID = "id"
        private const val KEY_PHONE_NUMBER = "phoneNumber"

        fun newInstance(
            firstName: String,
            lastName: String,
            id: String,
            phoneNumber: String
        ): AddContactDialogFragment {
            return AddContactDialogFragment().withArguments {
                putString(KEY_FIRST_NAME, firstName)
                putString(KEY_LAST_NAME, lastName)
                putString(KEY_ID, id)
                putString(KEY_PHONE_NUMBER, phoneNumber)
            }
        }
    }

    interface AddContactListener {
        fun onContactAdded(contact: Contact)
    }
}

