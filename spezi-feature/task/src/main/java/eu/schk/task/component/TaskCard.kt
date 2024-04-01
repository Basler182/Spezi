package eu.schk.task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import eu.schk.designsystem.RectangleBlue
import eu.schk.designsystem.SpeziTheme
import eu.schk.task.repository.Task


@Composable
fun TaskCard(taskItem: Task, onItemCheckedChange: (Task) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(RectangleBlue),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(RectangleBlue),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val checked = remember {
                mutableStateOf(taskItem.isChecked)
            }
            Text(
                text = taskItem.text,
                Modifier
                    .padding(start = 4.dp)
                    .weight(1f),
                textDecoration =
                if (checked.value) TextDecoration.LineThrough else TextDecoration.None
            )
            Checkbox(
                checked = checked.value,
                onCheckedChange = { isChecked ->
                    checked.value = isChecked
                    onItemCheckedChange(taskItem)
                }
            )
        }

    }
}

@Preview
@Composable
fun TaskCardPreview(@PreviewParameter(TaskItemProvider::class) taskItem: Task) {
    SpeziTheme {
        TaskCard(taskItem = taskItem, onItemCheckedChange = {})
    }
}

class TaskItemProvider : PreviewParameterProvider<Task> {
    override val values: Sequence<Task> = sequenceOf(
        Task("Complete Symptom Survey [KCCQ-12] for your care team")
    )
}