package ie2a_2200078.eventwork05.viewmodels

import androidx.lifecycle.*
import ie2a_2200078.eventwork05.MyApp
import ie2a_2200078.eventwork05.dao.GalleryNoteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class Mode {
    FAVORITE, ALL
}
class GalleryPostsViewModel(
    private val galleryNoteDAO: GalleryNoteDAO,
    mode: Mode
) : ViewModel(){

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: MyApp, private val mode: Mode) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GalleryPostsViewModel(app.database.galleryNoteDAO(), mode) as T
        }
    }

    val galleryNotes = when(mode) {
        Mode.ALL -> galleryNoteDAO.findAllWithFiles()
        Mode.FAVORITE -> galleryNoteDAO.findAllWhereFavorites()
    }

    private var _currentPhotoIndexes: Map<Long, Int> = emptyMap()


    fun toggleFavorite(id: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val note = galleryNoteDAO.find(id)
                galleryNoteDAO.update(note.copy(isFavorite = !note.isFavorite))
            }
        }
    }

    fun setCurrentPhotoIndex(noteId: Long, currentPageIndex: Int) {
        val map = _currentPhotoIndexes.toMutableMap()
        map[noteId] = currentPageIndex
        this._currentPhotoIndexes = map
    }

    fun getCurrentPhotoIndex(noteId: Long): Int {
        return _currentPhotoIndexes[noteId] ?: 0
    }
}