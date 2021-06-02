package ie2a_2200078.eventwork05.dao

import androidx.room.*
import ie2a_2200078.eventwork05.entities.GalleryNote
import ie2a_2200078.eventwork05.entities.GalleryNoteWithFile
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryNoteDAO {

    @Insert
    suspend fun insert(galleryNote: GalleryNote) : Long

    @Update
    suspend fun update(galleryNote: GalleryNote)

    @Delete
    suspend fun delete(galleryNote: GalleryNote)

    @Query("select * from gallery_notes")
    fun findAll(): Flow<List<GalleryNote>>

    @Transaction
    @Query("select * from gallery_notes")
    fun findAllWithFiles(): Flow<List<GalleryNoteWithFile>>

    @Query("select * from gallery_notes where id=:id")
    suspend fun find(id: Long) : GalleryNote
}