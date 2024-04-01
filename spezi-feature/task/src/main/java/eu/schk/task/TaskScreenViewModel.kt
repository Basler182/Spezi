package eu.schk.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.schk.task.repository.Task
import eu.schk.task.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskScreenViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private val _taskItems = MutableStateFlow<List<Task>>(emptyList())
    val taskItems: StateFlow<List<Task>> = _taskItems

    init {
        loadTaskItems()
    }

    private fun loadTaskItems() {
        viewModelScope.launch {
            _taskItems.value = taskRepository.getTaskModels()
        }
    }

    fun toggleTaskItem(item: Task) {
        println("toggleTaskItem: $item")
    }
}