package lt.sutemos.kodai.Utils;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lt.sutemos.kodai.Models.Irasas;

public class Util {
    public static final int ACTION_NEW              = 0;
    public static final int ACTION_EDIT             = 1;


    public static final int REQUEST_CREATE_ENTRY    =100;
    public static final int REQUEST_EDIT_ENTRY      =101;
    public static final int REQUEST_IMPORT_CSV_FILE =102;
    public static final int REQUEST_EXPORT_CSV_FILE =103;



    /*
     * generates dummy codes for nowz
     */
    public static List<Irasas> generateDummyData(){

        List<Irasas> irasai = new ArrayList<>();
        irasai.add(new Irasas("neradau kodai.txt",   "failo SD korteleje", "" +
                "Irasyti kodai.txt faila i SD korteles saknini kataloga, kuriame CSV formatu turi buti irasyta:\n" +
                "\"Adresas\", \"Kodas\", \"papildoma informacija\"\n" +
                "taip pat programai reikia leidimo skaityti is SD korteles"));

        for (int i = 1; i<=25; i++){
            irasai.add(new Irasas("Bijūno " + i, i + " kart pabelst", ""));
        }
        irasai.add(new Irasas("Bijūno 26",   " 26 kart paperst", ""));
        return irasai;
    }

    public static List<Irasas> loadEntriesFromCSV(File file){
        List<Irasas> returnList = new ArrayList<>();

        Log.d("CSV file:", file.toString());
        int id = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(file.toString()));
            String[] nextLine;
            List l;
            while ((nextLine = reader.readNext()) != null) {
                String address;
                String code;
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

    public static List<Irasas> loadEntriesFromURI(Context context, Uri uri){

        Log.v("Util","started");
        if (context == null || uri == null){
            return null;
        }
        Log.v("Util","not null");

        int id = 0;
        List<Irasas> returnList = new ArrayList<>();
        InputStream inputStream;

        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            Log.v("Util","got inputStream");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("Not Found","File not found");
            return null;
        }

        Log.v("Util","inputStream is not null");


        try {
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String address;
                String code;
                String comment = null;
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
            inputStream.close();
            return returnList;

        } catch (IOException e) {
            Log.d("loadEntriesFromCSV:", "IO Exception "+ e.getCause());
            e.printStackTrace();
        }


        return null;
    }


}
