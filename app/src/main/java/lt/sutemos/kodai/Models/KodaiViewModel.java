package lt.sutemos.kodai.Models;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import lt.sutemos.kodai.Services.CodeList;

public class KodaiViewModel extends ViewModel {

    private CodeList kodai;
    private String filter;

    public KodaiViewModel(){
        kodai = new CodeList();
        kodai.load();
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


    public void add(String adresas, String kodas){
        kodai.add(adresas,kodas);
    }
    public boolean delete(int id){
        return kodai.delete(id);
    }





    // As with presenter, we implement standard lifecycle methods from the view
    // in case we need to do anything with our model during those events.
    public void onCreate() { }
    public void onPause() { }
    public void onResume() { }
    public void onDestroy() { }


}
