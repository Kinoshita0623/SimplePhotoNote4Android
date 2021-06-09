package ie2a_2200078.eventwork05

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ie2a_2200078.eventwork05.databinding.ActivityGalleryNoteEditorBinding
import ie2a_2200078.eventwork05.entities.GalleryFile
import ie2a_2200078.eventwork05.view.FileListener
import ie2a_2200078.eventwork05.view.PickedImagesListAdapter
import ie2a_2200078.eventwork05.viewmodels.GalleryPostEditorViewModel

class GalleryNoteEditorActivity : AppCompatActivity() {
    private lateinit var viewModel: GalleryPostEditorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGalleryNoteEditorBinding>(this, R.layout.activity_gallery_note_editor)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, GalleryPostEditorViewModel.Factory(application as MyApp))[GalleryPostEditorViewModel::class.java]
        binding.galleryPostEditorViewModel = viewModel

        binding.saveFab.setOnClickListener {
            viewModel.save()
        }

        viewModel.isSuccess.observe(this){
            if(it == true) {
                finish()
            }
        }
        val fileListener = object : FileListener {
            override fun onDetach(file: GalleryFile) {
            }

            override fun onSelect(file: GalleryFile) {

            }
        }
        val adapter = PickedImagesListAdapter(fileListener, this)
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false).also { layoutManager ->
            binding.pickedImages.layoutManager = layoutManager
        }
        binding.pickedImages.adapter = adapter
        viewModel.pickedImages.observe(this) {
            adapter.submitList(it)
        }

        binding.pickedImageButton.setOnClickListener {
            if(!checkPermission()) {
                requestReadExternalStoragePermissionResultListener.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }else{
                showFilePicker()
            }
        }

    }

    private fun checkPermission(): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    private fun showFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        pickFileResultListener.launch(intent)
    }

    private val requestReadExternalStoragePermissionResultListener = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it) {
            showFilePicker()
        }
    }

    private val pickFileResultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val path = it.data?.data?.toString()
        if(path != null) {
            viewModel.addFile(path)
        }
    }
}