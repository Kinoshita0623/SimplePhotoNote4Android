package ie2a_2200078.eventwork05

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationBarMenuView
import ie2a_2200078.eventwork05.databinding.ActivityMainBinding
import ie2a_2200078.eventwork05.view.GalleryNoteWithFileListAdapter
import ie2a_2200078.eventwork05.viewmodels.GalleryPostsViewModel
import ie2a_2200078.eventwork05.viewmodels.Mode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentBase) as NavHostFragment

        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
        binding.createFab.setOnClickListener {
            startActivity(Intent(this, GalleryNoteEditorActivity::class.java))
        }
    }
}