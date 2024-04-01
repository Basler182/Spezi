package eu.schk.task.repository


interface TaskRepository {
    fun getTaskModels(): List<Task>
}
class DefaultTaskRepository : TaskRepository {
    override fun getTaskModels(): List<Task> {
        return listOf(
            Task("Complete Blood Pressure Measurement"),
            Task("Complete Weight Measurement", true),
            Task("Complete Symptom Survey [KCCQ-12] for your care team")
        ).sortedBy { it.isChecked }
    }

}
