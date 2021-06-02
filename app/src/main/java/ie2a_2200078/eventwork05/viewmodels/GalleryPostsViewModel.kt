package ie2a_2200078.eventwork05.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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


    fun toggleFavorite(id: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val note = galleryNoteDAO.find(id)
                galleryNoteDAO.update(note.copy(isFavorite = !note.isFavorite))
            }
        }
    }
}