package ie2a_2200078.eventwork05.viewmodels

import androidx.lifecycle.*
import ie2a_2200078.eventwork05.MyApp
import ie2a_2200078.eventwork05.dao.GalleryFileDAO
import ie2a_2200078.eventwork05.dao.GalleryNoteDAO
import ie2a_2200078.eventwork05.entities.GalleryFile
import ie2a_2200078.eventwork05.entities.GalleryNote
import ie2a_2200078.eventwork05.view.FileListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryPostEditorViewModel(
    val galleryNoteDAO: GalleryNoteDAO,
    val galleryFileDAO: GalleryFileDAO
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val app: MyApp
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GalleryPostEditorViewModel(app.database.galleryNoteDAO(), app.database.galleryFileDAO()) as T
        }
    }

    val title = MutableLiveData<String>()


    val description = MutableLiveData<String>()

    private val _pickedImages = MutableLiveData<List<GalleryFile>>()
    val pickedImages: LiveData<List<GalleryFile>> = _pickedImages

    private val _saveError = MutableLiveData<Throwable>()
    val saveError: LiveData<Throwable> = _saveError

    private val _isSuccess = MutableLiveData<Boolean>(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun save() {
        val n = title.value
        val description = this.description.value
        if(n.isNullOrBlank()) {
            return
        }
        val note = GalleryNote(
            n,
            description
        )

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                galleryNoteDAO.insert(note)
            }.onSuccess {
                _isSuccess.postValue(true)
            }.onFailure {
                _saveError.postValue(it)
            }


        }



    }

    fun addFile(path: String) {
        _pickedImages.value = (_pickedImages.value?: listOf()).toMutableList().also {
            it.add(GalleryFile(path, 0))
        }
    }

}