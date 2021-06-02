package ie2a_2200078.eventwork05.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
class GalleryNoteWithFile {
    @Embedded
    lateinit var galleryNote: GalleryNote

    @Relation(parentColumn = "id", entityColumn = "gallery_note_id", entity = GalleryFile::class)
    lateinit var files: List<GalleryFile>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GalleryNoteWithFile

        if (galleryNote != other.galleryNote) return false
        if (files != other.files) return false

        return true
    }

    override fun hashCode(): Int {
        var result = galleryNote.hashCode()
        result = 31 * result + files.hashCode()
        return result
    }
}