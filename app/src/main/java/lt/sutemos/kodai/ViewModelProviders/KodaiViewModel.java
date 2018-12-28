package lt.sutemos.kodai.ViewModelProviders;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import lt.sutemos.kodai.Model.Irasas;
import lt.sutemos.kodai.Services.CodeList;

public class KodaiViewModel extends ViewModel {

    private CodeList kodai;

    public KodaiViewModel(){
        kodai = new CodeList();
        kodai.load();
    }

    public void load(){
        kodai.load();
    }

    public List<Irasas> get(){
        return kodai.get();
    }

    public List<Irasas> find(String s){
        return kodai.find(s);
    }




    // As with presenter, we implement standard lifecycle methods from the view
    // in case we need to do anything with our model during those events.
    public void onCreate() { }
    public void onPause() { }
    public void onResume() { }
    public void onDestroy() { }


}
