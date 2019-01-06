package lt.sutemos.kodai;


import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import lt.sutemos.kodai.Adapters.MyAdapter;
import lt.sutemos.kodai.Models.Irasas;
import lt.sutemos.kodai.Models.KodaiViewModel;
import lt.sutemos.kodai.Utils.Util;

//import static lt.sutemos.kodai.Utils.Util.REQUEST_IMPORT_CSV_FILE;
import static lt.sutemos.kodai.Utils.Util.*;

public class MainActivity extends AppCompatActivity {
//    public final int REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private EditText searchEditText;
    private ImageButton clearTextButton;
    private KodaiViewModel kodaiViewModel;
    private boolean exitNow = false;
    private File csvImportFile;
    private MenuInflater menuInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kodaiViewModel = ViewModelProviders.of(this).get(KodaiViewModel.class);
        csvImportFile = new File(Environment.getExternalStorageDirectory(), getString(R.string.default_filename));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

            List<Irasas> irasai = Util.loadEntriesFromCSV(csvImportFile);
            if (irasai!= null) {
                kodaiViewModel.setKodai(irasai);
            } else {
                kodaiViewModel.setKodai(Util.generateDummyData());
            }

        } else {
            Log.i(getClass().toString(),"Can't read codes from external storage");
        }


        clearTextButton = (ImageButton) findViewById(R.id.imageButtonClearText);
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



        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
        });
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&  (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search();
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                createNewEntry();
                break;
            case R.id.menu_import: {
//                import file dialog
                Intent intent = new Intent()
                        .setType("text/*")
                        .setAction(Intent.ACTION_GET_CONTENT)
                        .addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_IMPORT_CSV_FILE);
            }
                break;
            case R.id.menu_export: {
                Intent intent = new Intent()
                        .setType("text/*")
                        .setAction(Intent.ACTION_CREATE_DOCUMENT)
                        .addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_EXPORT_CSV_FILE);
            }
                break;
            case R.id.menu_clear:
                kodaiViewModel.clear();
                updateAdapter();
                break;
            case R.id.menu_settings:
                break;
            case R.id.menu_about:
                break;
            case R.id.menu_exit:
                closeApp();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = null;

        Log.d("result","requestCode=" + requestCode + ", resultCode="+ resultCode);

        if (data != null){
            bundle = data.getExtras();
        }

        if ((requestCode == REQUEST_CREATE_ENTRY || requestCode == REQUEST_EDIT_ENTRY) && bundle == null){
            return;
        }

        switch (requestCode){
            case REQUEST_CREATE_ENTRY:{
                switch (resultCode){
//                  add entry
                    case RESULT_OK:
                        addNewEntry(bundle);
                        break;
                        default:
                }
            }
            break;

            case REQUEST_EDIT_ENTRY:{
                switch (resultCode){
//                  update entry
                    case RESULT_OK:
                        updateEntry(bundle);
                        break;
//                  delete entry
                    case RESULT_FIRST_USER:
                        kodaiViewModel.delete(bundle.getInt("id"));
                        updateAdapter();
                        break;
                    default:
                }

            }
            break;

            case REQUEST_IMPORT_CSV_FILE:
                if (data != null) {
                    Uri uri = data.getData();
                    importCsvFile(uri);
                } else{
                    Log.d("data"," data == null");
                }

                break;
            case REQUEST_EXPORT_CSV_FILE:
                if (data != null) {
                    Uri uri = data.getData();
                    exportCsvFile(uri);
                }
                break;

            default:
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

    private void closeApp(){
        finish();

    }
    private void updateAdapter(){
        // atnaujina irasu saraso vaizda
        adapter = new MyAdapter(this, kodaiViewModel);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void search(){
        kodaiViewModel.setFilter(searchEditText.getText().toString());
        updateAdapter();
//        KeyboardTools.hide(this);
    }

    private void createNewEntry(){
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            intent.putExtra("action", Util.ACTION_NEW);
            startActivityForResult(intent, Util.REQUEST_CREATE_ENTRY);
    }

    private void addNewEntry(Bundle bundle){
        kodaiViewModel.add(
                bundle.getString("address"),
                bundle.getString("code"),
                bundle.getString("info")
        );
        updateAdapter();
    }

    private void updateEntry(Bundle bundle){
        Irasas irasas = new Irasas(
                bundle.getInt("id"),
                bundle.getString("address"),
                bundle.getString("code"),
                bundle.getString("info")
        );
        kodaiViewModel.update(irasas);
        updateAdapter();
    }

    private void importCsvFile(Uri uri){
        Log.d("Import/URI", uri.getPath());

        List<Irasas> irasai = Util.loadEntriesFromURI(getApplicationContext(), uri);
            if (irasai!= null) {
                Log.v("importCsvFile()", "got " + irasai.size() + " entries");
//                kodaiViewModel.addKodai(irasai);
                kodaiViewModel.getKodai().merge(irasai);
                updateAdapter();
            } else{
                Log.d("Import/result", "result is null");
            }
    }
    private void exportCsvFile(Uri uri){
        Log.d("Export/URI", uri.getPath());

    }
}
