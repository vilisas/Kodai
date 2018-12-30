package lt.sutemos.kodai;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import lt.sutemos.kodai.Adapters.MyAdapter;
import lt.sutemos.kodai.Utils.KeyboardTools;
import lt.sutemos.kodai.Models.KodaiViewModel;
import lt.sutemos.kodai.Utils.Util;

public class MainActivity extends AppCompatActivity {
    public final int REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private EditText searchEditText;
    private ImageButton searchButton;
    private ImageButton clearTextButton;
    private ImageButton addButton;
    private KodaiViewModel kodaiViewModel;
    private boolean exitNow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kodaiViewModel = ViewModelProviders.of(this).get(KodaiViewModel.class);

        searchButton = (ImageButton) findViewById(R.id.imageButtonSearch);
        clearTextButton = (ImageButton) findViewById(R.id.imageButtonClearText);
        addButton = (ImageButton) findViewById(R.id.imageButtonAdd);

        searchEditText = (EditText) findViewById(R.id.editTextSearch);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(this, kodaiViewModel);
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
                // a-z, 0-9
//                if ((event.getAction() == KeyEvent.ACTION_UP) && ((keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) ||
//                                (keyCode >= KeyEvent.KEYCODE_BUTTON_A && keyCode <= KeyEvent.KEYCODE_BUTTON_Z) ||keyCode == KeyEvent.KEYCODE_DEL ))

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&  (keyCode == KeyEvent.KEYCODE_ENTER))

                {
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
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                 intent.putExtra("action", Util.ACTION_NEW);
                 startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Bundle bundle = data.getExtras();
                    if (bundle != null){
                        switch(bundle.getInt("action")) {
                            case Util.ACTION_NEW:
                                kodaiViewModel.add(bundle.getString("address"),bundle.getString("code"));
                                updateAdapter();
                                break;
                            case Util.ACTION_EDIT:
                                Toast.makeText(getApplicationContext(), "edited", Toast.LENGTH_SHORT).show();
//                                updateAdapter();
                                break;
                            default:
                        }
                    }
                break;

                case RESULT_CANCELED:
                    break;

                default:
            }
        }


    }

    @Override
    public void onBackPressed() {
        if (exitNow) {
            super.onBackPressed();
            return;
        }
        exitNow = true;
        Toast.makeText(getApplicationContext(), R.string.press_back_once_more_msg, Toast.LENGTH_SHORT).show();


    }

    protected void updateAdapter(){
        // atnaujina irasu saraso vaizda
        adapter = new MyAdapter(this, kodaiViewModel);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    protected void search(){
        kodaiViewModel.setFilter(searchEditText.getText().toString());
        updateAdapter();
        KeyboardTools.hide(this);

    }
}
