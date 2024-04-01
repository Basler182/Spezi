package eu.schk.task.repository

data class Task
    (
    val text: String,
    var isChecked: Boolean = false
)