package ie2a_2200078.eventwork05.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ie2a_2200078.eventwork05.entities.GalleryFile
import kotlinx.coroutines.flow.Flow


@Dao
interface GalleryFileDAO {

    @Insert
    suspend fun insertAll(list: List<GalleryFile>)

    @Query("select * from gallery_files where gallery_note_id=:id")
    fun findAllByNoteId(id: Long) : Flow<List<GalleryFile>>



}