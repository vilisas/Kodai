package lt.sutemos.kodai.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CodeDao {

    @Query("SELECT * FROM code")
    List<Code> getAll();

    @Query("SELECT * FROM code WHERE id IN (:codeIds)")
    List<Code> loadAllByIds(int[] codeIds);

    @Query("SELECT * FROM code WHERE address LIKE :addr")
    List<Code> findAllByAddress(String addr);

    @Insert
    void insertAll(Code... code);

    @Insert
    void insertList(List<Code> codes);

    @Delete
    void delete(Code codes);

    @Query("DELETE FROM code WHERE id = :codeId")
    void deleteById(Integer codeId);

    @Update
    void update(Code irasas);

    @Query("DELETE FROM code")
    void clearDatabase();

}
