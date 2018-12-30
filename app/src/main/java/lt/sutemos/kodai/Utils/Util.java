package lt.sutemos.kodai.Utils;


import android.app.Application;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.sutemos.kodai.Models.Irasas;

public class Util {
//    public enum ACTION_CODE {AC_NEW, AC_EDIT
    public static final int ACTION_NEW = 0;
    public static final int ACTION_EDIT = 1;


    public static List<Irasas> loadEntriesFromCSV(File file){
        List<Irasas> returnList = new ArrayList<>();

        Log.d("CSV file:", file.toString());
        int id = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(file.toString()));
            String[] nextLine;
            List l;
            while ((nextLine = reader.readNext()) != null) {
                String address = "";
                String code = "";
                String comment = "";

                Irasas irasas;
                if (nextLine.length >2){
                    comment = nextLine[2];
                }
                if (nextLine.length >=2){
                    address = nextLine[0];
                    code= nextLine[1];
                    irasas = new Irasas(id++, address, code, comment);
                    returnList.add(irasas);
                }
            }
            reader.close();
            return returnList;

        } catch (IOException e) {
            Log.d("loadEntriesFromCSV:", "IO Exception "+ e.getCause());
            e.printStackTrace();
        }


        return null;
    }
}
