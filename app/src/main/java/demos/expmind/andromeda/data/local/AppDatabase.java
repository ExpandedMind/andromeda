package demos.expmind.andromeda.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {VideoEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract VideosDao videosDao();

}
