package ie2a_2200078.eventwork05.viewmodels

import androidx.lifecycle.*
import ie2a_2200078.eventwork05.MyApp
import ie2a_2200078.eventwork05.dao.GalleryNoteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryPostsViewModel(
    private val galleryNoteDAO: GalleryNoteDAO
) : ViewModel(){

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: MyApp) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GalleryPostsViewModel(app.database.galleryNoteDAO()) as T
        }
    }

    val galleryNotes = galleryNoteDAO.findAllWithFiles()

    private val _currentPhotoIndexes = MutableLiveData<Map<Long, Int>>()
    val currentPhotoIndexes: LiveData<Map<Long, Int>> = _currentPhotoIndexes


    fun toggleFavorite(id: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val note = galleryNoteDAO.find(id)
                galleryNoteDAO.update(note.copy(isFavorite = !note.isFavorite))
            }
        }
    }

    fun setCurrentPhotoIndex(noteId: Long, currentPageIndex: Int) {
        val map = (_currentPhotoIndexes.value ?: emptyMap()).toMutableMap()
        map[noteId] = currentPageIndex
        this._currentPhotoIndexes.value = map
    }

    fun getCurrentPhotoIndex(noteId: Long): Int {
        return (_currentPhotoIndexes.value?: emptyMap())[noteId] ?: 0
    }
}