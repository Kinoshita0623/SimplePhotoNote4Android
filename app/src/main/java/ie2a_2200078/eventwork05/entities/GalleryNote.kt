package ie2a_2200078.eventwork05.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "gallery_notes"
)
data class GalleryNote (
    val title: String,
    val description: String?,
    val isFavorite: Boolean = false,
    @PrimaryKey(autoGenerate = true)  var id: Long = 0
)