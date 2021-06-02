package ie2a_2200078.eventwork05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ie2a_2200078.eventwork05.databinding.ActivityGalleryNoteEditorBinding
import ie2a_2200078.eventwork05.viewmodels.GalleryPostEditorViewModel

class GalleryNoteEditorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGalleryNoteEditorBinding>(this, R.layout.activity_gallery_note_editor)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this, GalleryPostEditorViewModel.Factory(application as MyApp))[GalleryPostEditorViewModel::class.java]
        binding.galleryPostEditorViewModel = viewModel

        binding.saveFab.setOnClickListener {
            viewModel.save()
        }

        viewModel.isSuccess.observe(this){
            if(it == true) {
                finish()
            }
        }

    }
}