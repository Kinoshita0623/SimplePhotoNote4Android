package ie2a_2200078.eventwork05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ie2a_2200078.eventwork05.databinding.ActivityMainBinding
import ie2a_2200078.eventwork05.view.GalleryNoteWithFileListAdapter
import ie2a_2200078.eventwork05.viewmodels.GalleryPostsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val viewModel = ViewModelProvider(this, GalleryPostsViewModel.Factory(application as MyApp))[GalleryPostsViewModel::class.java]

        val adapter = GalleryNoteWithFileListAdapter(
            {
                viewModel.toggleFavorite(it.galleryNote.id)
            },
            this,
                viewModel,
        )

        binding.galleryPostsListView.adapter = adapter
        binding.galleryPostsListView.layoutManager = LinearLayoutManager(this)
        viewModel.galleryNotes.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)

        binding.createFab.setOnClickListener {
            startActivity(Intent(this@MainActivity, GalleryNoteEditorActivity::class.java))
        }
    }
}