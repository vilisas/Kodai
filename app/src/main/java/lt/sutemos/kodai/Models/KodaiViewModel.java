package lt.sutemos.kodai.Models;

import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;

import java.util.List;

import lt.sutemos.kodai.Services.CodeList;
import lt.sutemos.kodai.database.AppDatabase;

public class KodaiViewModel extends ViewModel {

    private CodeList kodai;
    private String filter;
    private AppDatabase db;
    public KodaiViewModel(){
        kodai = new CodeList();
//        kodai.load();
    }

    public CodeList getKodai() {
        return kodai;
    }


    public List<Irasas> get(){
        return kodai.find(filter);
    }

    public List<Irasas> find(String filterText){
        setFilter(filterText);
        return get();
    }
    public void setFilter(String filterText){
        this.filter = filterText;
    }


    public void add(String adresas, String kodas, String info){
        kodai.add(adresas,kodas, info);
    }
    public boolean delete(int id){
        return kodai.delete(id);
    }

    public boolean update(Irasas irasas){
        return(kodai.update(irasas));
    }
    public void setKodai(List<Irasas> kodai){
        this.kodai.setIrasai(kodai);
    }
    public void addKodai(List<Irasas> irasai){
        this.kodai.getIrasai().addAll(irasai);
    }


    public AppDatabase getDb() {
        return db;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }

    // As with presenter, we implement standard lifecycle methods from the view
    // in case we need to do anything with our mode during those events.
    public void onCreate() { }
    public void onPause() { }
    public void onResume() { }
    public void onDestroy() { }


    public void clear() {
        kodai = new CodeList();
    }
}
