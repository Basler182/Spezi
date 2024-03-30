package eu.schk.contact

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import eu.schk.contact.repository.Contact
import eu.schk.contact.repository.ContactOption
import eu.schk.contact.repository.ContactOptionType
import eu.schk.contact.repository.ContactRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.UUID

class ContactScreenTest {

    private val mockContactRepository: ContactRepository = mock()
    private lateinit var viewModel: ContactViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        whenever(mockContactRepository.getContact()).thenReturn(createContact())
        viewModel = ContactViewModel(mockContactRepository)
    }

    @Test
    fun contactView_displaysContactName() {

        composeTestRule.setContent {
            ContactView(viewModel = ContactViewModel(mockContactRepository))
        }

        composeTestRule.onNodeWithText(mockContactRepository.getContact().name).assertExists()
    }


    @Test
    fun contactView_displaysContactOptions() {

        composeTestRule.setContent {
            ContactView(viewModel = ContactViewModel(mockContactRepository))
        }

        mockContactRepository.getContact().options.forEach { option ->
            composeTestRule.onNodeWithText(option.name).assertExists()
        }
    }

    @Test
    fun contactView_displaysContactTitle() {
        composeTestRule.setContent {
            ContactView(viewModel = ContactViewModel(mockContactRepository))
        }

        composeTestRule.onNodeWithText(mockContactRepository.getContact().title).assertExists()
    }

    @Test
    fun contactView_displaysContactDescription() {
        composeTestRule.setContent {
            ContactView(viewModel = ContactViewModel(mockContactRepository))
        }

        composeTestRule.onNodeWithText(mockContactRepository.getContact().description)
            .assertExists()
    }

    private fun createContact(): Contact {
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
                    "",
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