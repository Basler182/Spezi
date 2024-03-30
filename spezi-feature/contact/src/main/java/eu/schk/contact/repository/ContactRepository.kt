package eu.schk.contact.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import java.util.UUID

interface ContactRepository {
    fun getContact(): Contact
}

class DefaultContactRepository : ContactRepository {
    override fun getContact(): Contact {
        return Contact(
            UUID.randomUUID(),
            null,
            "John Doe",
            "CEO",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Company Inc.",
            "1234 Main Street, 12345 City",
            listOf(
                ContactOption(
                    UUID.randomUUID(),
                    "Call",
                    "+49 123 456 789",
                    Icons.Default.Call,
                    ContactOptionType.CALL
                ),
                ContactOption(
                    UUID.randomUUID(),
                    "Email",
                    "test@gmail.com",
                    Icons.Default.Email,
                    ContactOptionType.EMAIL
                ),
                ContactOption(
                    UUID.randomUUID(),
                    "Website",
                    "https://www.google.com",
                    Icons.Default.Info,
                    ContactOptionType.WEBSITE
                )
            )
        )
    }
}