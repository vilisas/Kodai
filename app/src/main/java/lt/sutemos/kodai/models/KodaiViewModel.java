package lt.sutemos.kodai.models;

/**
 * Main Activity View Model
 * Created by Vilius Bilinkevicius on 2019.01
 */

import android.arch.lifecycle.ViewModel;
import java.util.List;
import lt.sutemos.kodai.database.AppDatabase;
import lt.sutemos.kodai.database.Code;


public class KodaiViewModel extends ViewModel {

    private String filter;
    private AppDatabase appDatabase;

    public List<Code> get(){
//        return kodai.find(filter);
//        AppDatabase database = AppDatabase.getAppDatabase(super.)
        if (filter == null || String.valueOf(filter).equals("")){
            return appDatabase.codeDao().getAll();
        }
        return appDatabase.codeDao().findAllByAddress(filter);



    }

    public List<Code> find(String filterText){
        setFilter(filterText);
        return get();
    }
    public void setFilter(String filterText){
        this.filter = filterText;
    }


    public void add(String adresas, String kodas, String info){
        appDatabase.codeDao().insertAll(new Code(adresas,kodas,info));
    }
    public void add(Code code){
        appDatabase.codeDao().insertAll(code);
    }

    public void delete(int id){
        appDatabase.codeDao().deleteById(id);
    }

    public void delete(Code code){
        appDatabase.codeDao().delete(code);
    }

    public void update(Code irasas){
        appDatabase.codeDao().update(irasas);
//        return(kodai.update(irasas));
    }
    public void setKodai(List<Code> kodai){
//        this.kodai.setIrasai(kodai);
    }
    public void addKodai(List<Code> irasai){
//        kodai.add(irasai);
    }

    public void clear() {
        appDatabase.codeDao().clearDatabase();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }
}
