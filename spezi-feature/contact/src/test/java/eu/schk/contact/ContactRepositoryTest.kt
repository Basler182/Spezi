package eu.schk.contact

import eu.schk.contact.repository.ContactOptionType
import eu.schk.contact.repository.DefaultContactRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultContactRepositoryTest {

    private lateinit var repository: DefaultContactRepository

    @Before
    fun setup() {
        repository = DefaultContactRepository()
    }

    @Test
    fun defaultContactRepository_loadsContact() {
        val contact = repository.getContact()
        assertEquals(contact.name, "John Doe")
        assertEquals(contact.title, "CEO")
        assertEquals(contact.description, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        assertEquals(contact.organization, "Company Inc.")
        assertEquals(contact.address, "1234 Main Street, 12345 City")
        assertEquals(contact.options.size, 3)
        assertEquals(contact.options[0].name, "Call")
        assertEquals(contact.options[0].value, "+49 123 456 789")
        assertEquals(contact.options[0].optionType, ContactOptionType.CALL)
    }
}