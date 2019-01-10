package lt.sutemos.kodai.database;

/**
 * Database Entry
 * Created by Vilius Bilinkevicius on 2019.01
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Code implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="address")
    private String address;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "info")
    private String info;

    public Code() {}
    public Code(String adresas, String kodas, String info) {
        this.address = adresas;
        this.code = kodas;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
