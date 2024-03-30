package eu.schk.contact

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.schk.contact.components.ContactOptionCard
import eu.schk.contact.components.NavigationCard
import eu.schk.contact.repository.DefaultContactRepository
import eu.schk.designsystem.SpeziTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContactView(modifier: Modifier = Modifier, viewModel: ContactViewModel = viewModel()) {
    val contact by viewModel.contact.collectAsState()
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Surface(color = MaterialTheme.colorScheme.background) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    contact.icon ?: Icons.Default.AccountBox,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier.size(48.dp)
                                )
                                Column {
                                    Text(
                                        text = contact.name,
                                        style = MaterialTheme.typography.headlineLarge
                                    )
                                    Text(
                                        text = contact.title,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = contact.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                contact.options.forEach { option ->
                                    ContactOptionCard(option = option)
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            NavigationCard(address = contact.address)
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun PrevContactScreen() {
    SpeziTheme {
        val contactRepository = DefaultContactRepository()
        val contactViewModel = ContactViewModel(contactRepository)
        ContactView(Modifier, contactViewModel)
    }
}