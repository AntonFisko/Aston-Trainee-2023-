package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding
import java.util.UUID


class MainActivity : AppCompatActivity(), AddContactDialogFragment.AddContactListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация и установка LayoutManager для RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.floatingActionButton.setOnClickListener {
            val addContactDialog = AddContactDialogFragment()
            addContactDialog.show(supportFragmentManager, "AddContactDialog")
        }

        // Создание и установка адаптера для RecyclerView
        userAdapter = ContactAdapter()
        binding.recyclerView.adapter = userAdapter

        // Получите список пользователей или загрузите из источника данных

        val userList = mutableListOf<Contact>()

// Заполнение списка 100 пользователями с фамилиями "Фамилия" и именами "Имя N", где N - номер от 1 до 100
        for (i in 1..100) {
            val user = Contact(
                getRandomId(),
                getRandomName(),
                getRandomLastName(),
                getRandomPhoneNumber()
            )
            userList.add(user)
        }

        // Передайте новый список пользователей в адаптер для обновления RecyclerView
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

    private fun getRandomId(): UUID = UUID.randomUUID()

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
        // Добавляем новый контакт в список и обновляем RecyclerView
        val currentList = userAdapter.currentList.toMutableList()
        currentList.add(contact)
        userAdapter.submitList(currentList)
    }
}