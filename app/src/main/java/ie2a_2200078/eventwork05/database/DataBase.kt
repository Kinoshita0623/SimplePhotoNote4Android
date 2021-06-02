package ie2a_2200078.eventwork05.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ie2a_2200078.eventwork05.dao.GalleryFileDAO
import ie2a_2200078.eventwork05.dao.GalleryNoteDAO
import ie2a_2200078.eventwork05.entities.GalleryFile
import ie2a_2200078.eventwork05.entities.GalleryNote
import ie2a_2200078.eventwork05.entities.GalleryNoteWithFile

@Database(
    entities = [
        GalleryFile::class,
        GalleryNote::class,
    ],
    version = 1,
    exportSchema = true,

)
abstract class DataBase : RoomDatabase(){

    abstract fun galleryNoteDAO(): GalleryNoteDAO

    abstract fun galleryFileDAO(): GalleryFileDAO
}