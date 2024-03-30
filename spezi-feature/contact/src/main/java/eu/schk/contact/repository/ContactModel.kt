package eu.schk.contact.repository

import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

data class Contact(
    val id: UUID,
    val icon: ImageVector?,
    var name: String,
    val title: String,
    val description: String,
    val organization: String,
    val address: String,
    val options: List<ContactOption>
)

data class ContactOption(
    val id: UUID, val name: String, val value: String, val icon: ImageVector?, val optionType :ContactOptionType
)

enum class ContactOptionType {
    CALL, EMAIL, WEBSITE
}