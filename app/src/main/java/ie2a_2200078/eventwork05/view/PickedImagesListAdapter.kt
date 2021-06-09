package ie2a_2200078.eventwork05.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ie2a_2200078.eventwork05.databinding.ItemEditorsPhotoPreviewBinding
import ie2a_2200078.eventwork05.entities.GalleryFile

class PickedImagesListAdapter(
        val listener: FileListener,
        val lifecycleOwner: LifecycleOwner
) : ListAdapter<GalleryFile, PickedImagesListAdapter.VH>(Diff){

    inner class VH(val binding : ItemEditorsPhotoPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: GalleryFile) {
            binding.file = item
            binding.fileListener
            binding.lifecycleOwner = lifecycleOwner
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
                ItemEditorsPhotoPreviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }
}