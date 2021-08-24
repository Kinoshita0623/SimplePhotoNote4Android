package ie2a_2200078.eventwork05.domain

import ie2a_2200078.eventwork05.State
import kotlinx.coroutines.flow.Flow

data class Note(
    val id: Long,
    val title: String,
    val description: String?,
    val files: List<FileProperty>,
    val accountId: Long,
    val account: Account
)

data class CreateNote(
    val title: String,
    val description: String?,
    val files: List<String>
)
interface NoteRepository {

    fun timeline(): Flow<State<List<Note>>>

    fun myNotes(): Flow<State<List<Note>>>

    suspend fun create(createNote: CreateNote) : Note

}