package lt.sutemos.kodai.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Code {
    @PrimaryKey
    public int id;

    @ColumnInfo(name="address")
    public String address;
    @ColumnInfo(name = "code")
    public String code;
    @ColumnInfo(name = "info")
    public String info;


}
