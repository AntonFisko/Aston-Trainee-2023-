package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding
import java.util.UUID


class MainActivity : AppCompatActivity(), AddContactDialogFragment.AddContactListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        userAdapter = ContactAdapter { contact ->
            openEditContactDialog(contact)
        }

        binding.recyclerView.adapter = userAdapter
        binding.floatingActionButton.setOnClickListener {
            AddContactDialogFragment().show(supportFragmentManager, "AddContactDialogFragment")
        }

        val userList = mutableListOf<Contact>()

        for (i in 1..3) {
            val user = Contact(
                getRandomId(),
                getRandomName(),
                getRandomLastName(),
                getRandomPhoneNumber()
            )
            userList.add(user)
        }
        userAdapter.submitList(userList)
    }

    private fun getRandomName(): String {
        val userList = listOf(
            "Иван",
            "Петр",
            "Сергей",
            "Александр",
            "Николай",
            "Андрей",
            "Александр",
        )
        val randomIndex = (userList.indices).random()
        return userList[randomIndex]
    }

    private fun getRandomLastName(): String {
        val userList = listOf(
            "Иванов",
            "Петров",
            "Сергеев",
            "Александров",
            "Николайов",
            "Андреев",
            "Александров",

            )
        val randomIndex = (userList.indices).random()
        return userList[randomIndex]
    }

    private fun getRandomId(): String = UUID.randomUUID().toString()

    private fun getRandomPhoneNumber(): String {
        val userList = listOf(
            "+79999999999",
            "+79999999991",
            "+79999999992",
            "+79999999993",
            "+79999999994",
            "+79999999995",
            "+79999999996",
        )
        val randomIndex = (userList.indices).random()
        return userList[randomIndex]
    }

    override fun onContactAdded(contact: Contact) {
        val currentList = userAdapter.currentList.toMutableList()
        currentList.add(contact)
        userAdapter.submitList(currentList)
    }

    fun openEditContactDialog(contact: Contact) {
        val dialogFragment = AddContactDialogFragment.newInstance(
            contact.firstName,
            contact.lastName,
            contact.id,
            contact.phoneNumber
        )
        dialogFragment.show(supportFragmentManager, "EditContactDialogFragment")
    }
}