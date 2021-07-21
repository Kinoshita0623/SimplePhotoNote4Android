package ie2a_2200078.eventwork05

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ie2a_2200078.eventwork05.databinding.ActivityGalleryNoteEditorBinding
import ie2a_2200078.eventwork05.entities.GalleryFile
import ie2a_2200078.eventwork05.view.FileListener
import ie2a_2200078.eventwork05.view.PickedImagesListAdapter
import ie2a_2200078.eventwork05.viewmodels.GalleryPostEditorViewModel
import java.text.SimpleDateFormat
import java.util.*

class GalleryNoteEditorActivity : AppCompatActivity() {
    private lateinit var viewModel: GalleryPostEditorViewModel

    private var takePictureUri: Uri? = null
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

        binding.takeAPictureButton.setOnClickListener {
            takePicture()

        }
        viewModel.isSuccess.observe(this) {
            if(it) {
                finish()
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

    private fun takePicture() {
        val uri = createImageFile()
        takePictureUri = uri

        requestTakePictureListener.launch(uri)

    }

    private val requestReadExternalStoragePermissionResultListener = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it) {
            showFilePicker()
        }
    }



    private fun createImageFile(): Uri {
        val timestamp = SimpleDateFormat.getDateTimeInstance().format(Date())
        val fileName = "img_$timestamp"
        val resolver = contentResolver
        val contentValues = ContentValues().also { v ->
            v.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            v.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                v.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/photo_app")
            }
        }

        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    private val requestTakePictureListener = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        val uri = takePictureUri
        if(it && uri != null) {
            viewModel.addFile(uri.toString())
        }
    }

    private val pickFileResultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val uri = it.data?.data
        val path = uri?.toString()
        if(path != null) {
            Log.d("EditorActivity", "path:$path")
            val flags =  Intent.FLAG_GRANT_READ_URI_PERMISSION
            applicationContext.contentResolver.takePersistableUriPermission(uri, flags)
            viewModel.addFile(path)
        }
    }
}