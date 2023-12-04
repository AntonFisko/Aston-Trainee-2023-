package com.example.recyclerview

import java.util.UUID

data class Contact(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val phoneNumber : String
)
