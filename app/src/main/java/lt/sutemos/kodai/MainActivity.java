package lt.sutemos.kodai;


import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import lt.sutemos.kodai.Adapters.MyAdapter;
import lt.sutemos.kodai.Model.Irasas;
import lt.sutemos.kodai.Utils.KeyboardTools;
import lt.sutemos.kodai.ViewModelProviders.KodaiViewModel;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ImageButton searchButton;
    private EditText searchEditText;
    private ImageButton clearTextButton;
    private KodaiViewModel kodaiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kodaiViewModel = ViewModelProviders.of(this).get(KodaiViewModel.class);

        searchButton = (ImageButton) findViewById(R.id.imageButtonSearch);
        clearTextButton = (ImageButton) findViewById(R.id.imageButtonClearText);
        searchEditText = (EditText) findViewById(R.id.editTextSearch);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        irasai = (List<Irasas>) new CodeList();

//        kodai = new CodeList();
        adapter = new MyAdapter(this, kodaiViewModel.get());
        recyclerView.setAdapter(adapter);


        clearTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                search();
            }
        });


        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search();
                    return true;
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            search();
            }
        });
    }
    protected void search(){
        List<Irasas> irasai = kodaiViewModel.find(searchEditText.getText().toString());
        if (irasai != null) {
            adapter = new MyAdapter(getApplicationContext(), irasai);
            recyclerView.setAdapter(adapter);
        }
        KeyboardTools.hide(this);

    }
}
