package lt.sutemos.kodai.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Code.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabaseInstance;
    public abstract CodeDao codeDao();
    public static AppDatabase getAppDatabase(Context context){
        if (appDatabaseInstance == null) {
            appDatabaseInstance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "kodai")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return appDatabaseInstance;

    }
    public static void destroyInstance(){
        appDatabaseInstance = null;
    }

}
