package eu.schk.task.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.schk.designsystem.SpeziTheme
import eu.schk.designsystem.White
import eu.schk.task.TaskScreenViewModel
import eu.schk.task.component.TaskCard
import eu.schk.task.repository.DefaultTaskRepository

@Composable
fun TaskScreen(viewModel: TaskScreenViewModel = viewModel()) {
    val taskItems by viewModel.taskItems.collectAsState()

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(White),
    ) {
        Column {
            Text(
                text = "Task List",
                fontWeight = FontWeight.Bold,
            )
            LazyColumn {
                items(items = taskItems ) { taskItem ->
                    TaskCard(taskItem, onItemCheckedChange = { viewModel.toggleTaskItem(it) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskScreenPreview() {
    SpeziTheme {
        TaskScreen(TaskScreenViewModel(DefaultTaskRepository()))
    }
}