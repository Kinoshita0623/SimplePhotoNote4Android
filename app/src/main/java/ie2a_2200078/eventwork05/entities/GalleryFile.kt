package ie2a_2200078.eventwork05.entities

import androidx.room.*

@Entity(
    tableName = "gallery_files",
    foreignKeys = [ForeignKey(entity = GalleryNote::class, parentColumns = ["id"], childColumns = ["gallery_note_id"], onDelete = ForeignKey.CASCADE)]
)
data class GalleryFile(
    val path: String,
    @ColumnInfo(name = "gallery_note_id") val galleryNoteId: Long,
    @PrimaryKey(autoGenerate = true) var id: Long = 0
) {

}