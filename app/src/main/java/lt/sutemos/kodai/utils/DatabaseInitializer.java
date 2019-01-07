package lt.sutemos.kodai.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import lt.sutemos.kodai.database.AppDatabase;
import lt.sutemos.kodai.database.Code;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Code addCode(final AppDatabase db, Code user) {
        db.codeDao().insertAll(user);

        return user;
    }

    private static void populateWithTestData(AppDatabase db) {
        Code code = new Code();
        code.setAddress("Bijuno 158");
        code.setCode("tris kartus pabelst");
        code.setInfo("nera duru");
        addCode(db, code);


        List<Code> userList = db.codeDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}