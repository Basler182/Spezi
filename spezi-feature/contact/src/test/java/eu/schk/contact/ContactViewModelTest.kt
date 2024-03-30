package eu.schk.contact

import eu.schk.contact.repository.Contact
import eu.schk.contact.repository.ContactOption
import eu.schk.contact.repository.ContactOptionType
import eu.schk.contact.repository.ContactRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.UUID

class ContactViewModelTest {

    private val mockContactRepository: ContactRepository = mock()
    private lateinit var viewModel: ContactViewModel

    @Before
    fun setup() {
        whenever(mockContactRepository.getContact()).thenReturn(createContact())
        viewModel = ContactViewModel(mockContactRepository)
    }

    @Test
    fun contactViewModel_loadsContactFromRepository() = runTest {
        val contact = viewModel.contact.first()
        assertEquals(contact, mockContactRepository.getContact())
    }

    @Test
    fun contactViewModel_loadsContactNameFromRepository() = runTest {
        val contact = viewModel.contact.first()
        assertEquals(contact.name, mockContactRepository.getContact().name)
    }

    @Test
    fun setViewModelContactName_updatesViewModel() = runTest {
        val newName = "Kilian"
        val contact = viewModel.contact.first()
        contact.name = newName
        val updatedContact = viewModel.contact.first()
        assertEquals(updatedContact.name, newName)
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
                    null,
                    ContactOptionType.CALL
                ),
                ContactOption(
                    UUID.randomUUID(),
                    "Email",
                    "test@test.de",
                    null,
                    ContactOptionType.EMAIL
                ),
                ContactOption(
                    UUID.randomUUID(),
                    "Website",
                    "https://www.google.com",
                    null,
                    ContactOptionType.WEBSITE
                )
            )
        )
    }
}