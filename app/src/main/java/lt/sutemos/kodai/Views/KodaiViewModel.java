package lt.sutemos.kodai.Views;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import lt.sutemos.kodai.Model.Irasas;

public class KodaiViewModel extends ViewModel {
    private List<Irasas> irasai;

    public KodaiViewModel(){

    }

    // As with presenter, we implement standard lifecycle methods from the view
    // in case we need to do anything with our model during those events.
    public void onCreate() { }
    public void onPause() { }
    public void onResume() { }
    public void onDestroy() { }


}
