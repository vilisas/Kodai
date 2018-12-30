package lt.sutemos.kodai.Models;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import lt.sutemos.kodai.Services.CodeList;

public class KodaiViewModel extends ViewModel {

    private CodeList kodai;
    private String filter;

    public KodaiViewModel(){
        kodai = new CodeList();
//        kodai.load();
    }

    public void load(){
        kodai.load();
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




    // As with presenter, we implement standard lifecycle methods from the view
    // in case we need to do anything with our mode during those events.
    public void onCreate() { }
    public void onPause() { }
    public void onResume() { }
    public void onDestroy() { }


}
