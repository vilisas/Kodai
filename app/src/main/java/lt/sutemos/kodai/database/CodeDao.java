package lt.sutemos.kodai.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CodeDao {

    @Query("SELECT * FROM code")
    List<Code> getAll();

    @Query("SELECT * FROM code WHERE id IN (:userIds)")
    List<Code> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM code WHERE address LIKE :addr LIMIT 1")
    Code findByAddress(String addr);

    @Insert
    void insertAll(Code... users);

    @Delete
    void delete(Code user);

}
