package ie2a_2200078.eventwork05.domain

import ie2a_2200078.eventwork05.Content
import ie2a_2200078.eventwork05.State
import ie2a_2200078.eventwork05.client.FileUploader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex

import ie2a_2200078.eventwork05.client.CreateNoteBody
import ie2a_2200078.eventwork05.client.GalleryNoteAPIClient
import ie2a_2200078.eventwork05.client.throwErrors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

data class Note(
    val id: Long,
    val title: String,
    val description: String?,
    val files: List<FileProperty>,
    val accountId: Long,
    val account: Account,
    val isPrivate: Boolean,
)

data class CreateNote(
    val title: String,
    val description: String?,
    val files: List<CreateFile>
)

data class CreateFile(
    val path: String,
    val name: String,
)


data class NotesState(
    val notes: Map<Long, Note>
)

data class TimelineState(
    val noteIds: List<Long>,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)


class NoteStore(
    private val apiClient: GalleryNoteAPIClient,
    private val fileUploader: FileUploader,
    ) {

    private val mutex = Mutex()
    private val _notesState = MutableStateFlow(NotesState(mapOf()))
    private val _timelineState = MutableStateFlow(TimelineState(emptyList()))
    private val _myNotesState = MutableStateFlow(TimelineState(emptyList()))


    suspend fun fetchTimeline() {
        runCatching {
            apiClient.findTimeline().throwErrors()
        }.onFailure {
            mutex.withLock {
                _timelineState.value = _timelineState.value.copy(
                    error = it
                )
            }
        }.onSuccess {
            mutex.withLock {
                _timelineState.value = _timelineState.value.copy(
                    noteIds = it.body()!!.map {
                        it.id
                    }
                )
            }
        }
    }


    suspend fun fetchMyNotes() {
        runCatching {
            apiClient.findMyNotes().throwErrors()
        }.onFailure {
            mutex.withLock {
                _myNotesState.value = _timelineState.value.copy(
                    error = it
                )
            }
        }.onSuccess {
            mutex.withLock {
                _notesState.value = _notesState.value.copy(
                    notes = _notesState.value.notes.toMutableMap().also { map ->
                        map.putAll(it.body()!!.map {
                            it.id to it.toNote()
                        }.toMap())
                    }
                )
                _myNotesState.value = _timelineState.value.copy(
                    noteIds = it.body()!!.map {
                        it.id
                    }
                )
            }
        }
    }

    suspend fun create(createNote: CreateNote) : Note{
        val files = withContext(Dispatchers.IO) {
            createNote.files.map {
                fileUploader.upload(it.name, path = it.path)
            }
        }

        val body = CreateNoteBody(
            title = createNote.title,
            description = createNote.description,
            fileIds = files.map {
                it.id
            }
        )
        val res = apiClient.createNote(body).throwErrors()
        val note = res.body()?.toNote()!!
        onNoteCreated(note)
        return note
    }

    fun getTimelineState(): Flow<State<List<Note>>> {
        return combine(_notesState, _timelineState) { notesState, timelineState ->
            val notes = timelineState.noteIds.mapNotNull {
                notesState.notes[it]
            }
            val content = if(notes.isEmpty()) Content.NotExit<List<Note>>() else Content.Exit<List<Note>>(notes)
            when {
                timelineState.isLoading -> {
                    State.Loading(content)
                }
                timelineState.error == null -> {
                    State.Fixed(content)
                }
                else -> {
                    State.Error(content,timelineState.error)
                }
            }
        }
    }

    fun getMyNotesState(): Flow<State<List<Note>>> {
        return combine(_notesState, _myNotesState) { notesState, timelineState ->
            val notes = timelineState.noteIds.mapNotNull {
                notesState.notes[it]
            }
            val content = if(notes.isEmpty()) Content.NotExit<List<Note>>() else Content.Exit<List<Note>>(notes)
            when {
                timelineState.isLoading -> {
                    State.Loading(content)
                }
                timelineState.error == null -> {
                    State.Fixed(content)
                }
                else -> {
                    State.Error(content,timelineState.error)
                }
            }
        }
    }

    private suspend fun onNoteCreated(note: Note){
        mutex.withLock {
            this._notesState.value = this._notesState.value.copy(
                notes = this._notesState.value.notes.toMutableMap().also{ notes ->
                    notes[note.id] = note
                }
            )
            _myNotesState.value = _myNotesState.value.copy(
                noteIds = _myNotesState.value.noteIds.toMutableList().also { list ->
                    list.add(0, note.id)
                }
            )
            _timelineState.value = _timelineState.value.copy(
                noteIds = _timelineState.value.noteIds.toMutableList().also { list ->
                    list.add(0, note.id)
                }
            )
        }
    }


}
