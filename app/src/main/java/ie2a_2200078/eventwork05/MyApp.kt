package ie2a_2200078.eventwork05

import android.app.Application
import androidx.room.Room
import ie2a_2200078.eventwork05.database.DataBase

class MyApp : Application(){

    lateinit var database: DataBase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, DataBase::class.java, "gallery_app_db")
            .build()


    }
}