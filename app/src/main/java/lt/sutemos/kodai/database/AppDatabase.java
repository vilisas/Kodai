package lt.sutemos.kodai.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Code.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CodeDao codeDao();
}
