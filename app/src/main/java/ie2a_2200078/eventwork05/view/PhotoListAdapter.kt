package ie2a_2200078.eventwork05.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie2a_2200078.eventwork05.databinding.ItemPhotoBinding
import ie2a_2200078.eventwork05.entities.GalleryFile

class PhotoListAdapter(
        val lifecycleOwner: LifecycleOwner
) : ListAdapter<GalleryFile, PhotoListAdapter.VH>(Diff){

    inner class VH(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(photo: GalleryFile) {
            Glide.with(binding.imageView)
                    .load(photo.path)
                    .into(binding.imageView)
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
                ItemPhotoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }


}

object Diff : DiffUtil.ItemCallback<GalleryFile>() {
    override fun areContentsTheSame(oldItem: GalleryFile, newItem: GalleryFile): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: GalleryFile, newItem: GalleryFile): Boolean {
        return oldItem.id == newItem.id
    }
}