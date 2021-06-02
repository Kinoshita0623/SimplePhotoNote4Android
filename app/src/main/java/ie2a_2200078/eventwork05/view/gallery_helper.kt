package ie2a_2200078.eventwork05.view

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import ie2a_2200078.eventwork05.R

@BindingAdapter("isLiked")
fun ImageButton.setLikeButtonState(isLiked: Boolean?) {

    if(isLiked == true) {

        this.setImageResource(R.drawable.ic_baseline_red_favorite_24)
    }else{
        this.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }
}