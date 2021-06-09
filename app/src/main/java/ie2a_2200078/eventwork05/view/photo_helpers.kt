package ie2a_2200078.eventwork05.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object UriImageHelper {
    @JvmStatic
    @BindingAdapter("setSimpleImageUri")
    fun ImageView.setSimpleImageUri(uri: String?){
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(this)
    }
}