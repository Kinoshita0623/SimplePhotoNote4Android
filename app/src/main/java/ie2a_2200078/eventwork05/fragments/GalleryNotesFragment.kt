package ie2a_2200078.eventwork05.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie2a_2200078.eventwork05.GalleryNoteEditorActivity
import ie2a_2200078.eventwork05.MyApp
import ie2a_2200078.eventwork05.R
import ie2a_2200078.eventwork05.view.GalleryNoteWithFileListAdapter
import ie2a_2200078.eventwork05.viewmodels.GalleryPostsViewModel
import ie2a_2200078.eventwork05.viewmodels.Mode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryNotesFragment : Fragment(R.layout.fragment_gallery_notes){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, GalleryPostsViewModel.Factory(requireContext().applicationContext as MyApp, Mode.ALL))[GalleryPostsViewModel::class.java]

        val adapter = GalleryNoteWithFileListAdapter(
            {
                viewModel.toggleFavorite(it.galleryNote.id)
            },
            this,
            viewModel,
        )
        val galleryNotesView = view.findViewById<RecyclerView>(R.id.galleryNotesView)

        galleryNotesView.adapter = adapter
        galleryNotesView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.galleryNotes.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)

    }
}