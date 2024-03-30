package eu.schk.contact

import androidx.lifecycle.ViewModel
import eu.schk.contact.repository.Contact
import eu.schk.contact.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ContactViewModel(contactRepository: ContactRepository) : ViewModel() {
    private val _contact = MutableStateFlow(contactRepository.getContact())
    val contact: StateFlow<Contact> = _contact
}