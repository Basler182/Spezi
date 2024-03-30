package eu.schk.contact.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.schk.contact.repository.ContactOption
import eu.schk.contact.repository.ContactOptionType
import eu.schk.designsystem.SpeziTheme
import java.util.UUID

@Composable
fun ContactOptionCard(option: ContactOption) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .width(90.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    option.optionType.name,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(
                    option.icon ?: Icons.Default.Email,
                    contentDescription = option.name
                )
            }
            Text(
                text = option.name,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun ContactOptionCardPreview() {
    SpeziTheme {
        ContactOptionCard(
            option = ContactOption(
                id = UUID.randomUUID(),
                name = "Email",
                value = "test@test.de",
                icon = Icons.Default.Email,
                optionType = ContactOptionType.EMAIL
            )
        )
    }
}