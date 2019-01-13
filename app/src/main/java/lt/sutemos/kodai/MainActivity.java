package lt.sutemos.kodai;

/**
 * Main Activity
 * Created by Vilius Bilinkevicius on 2019.01
 */

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import java.util.List;

import lt.sutemos.kodai.adapters.MyAdapter;
import lt.sutemos.kodai.database.AppDatabase;
import lt.sutemos.kodai.database.Code;
import lt.sutemos.kodai.models.KodaiViewModel;
import lt.sutemos.kodai.preferences.MainPreferences;
import lt.sutemos.kodai.utils.Permissions;
import lt.sutemos.kodai.utils.Util;

import static lt.sutemos.kodai.utils.Util.*;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity.class";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private EditText searchEditText;
    private ImageButton clearTextButton;
    private KodaiViewModel kodaiViewModel;
    private boolean exitNow = false;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase appDatabase;
        kodaiViewModel = ViewModelProviders.of(this).get(KodaiViewModel.class);
        // open database once, if it's not opened yet.
        if (kodaiViewModel.getAppDatabase() == null) {
            appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
            kodaiViewModel.setAppDatabase(appDatabase);
        }

        clearTextButton = findViewById(R.id.imageButtonClearText);
        searchEditText = findViewById(R.id.editTextSearch);

        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(this, kodaiViewModel);
        recyclerView.setAdapter(adapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        clearTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
                search();
            }
        });


//        Permissions.checkEXTReadPermission(this);
//        checkEXTWritePermission();

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
                // TODO: permission request dialog appears after file selection
                Permissions.checkEXTReadPermission(this);
//                import file dialog
                Intent intent = new Intent()
                        .setType("text/*")
                        .setAction(Intent.ACTION_GET_CONTENT)
                        .addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_IMPORT_CSV_FILE);
            }
                break;
            case R.id.menu_export: {
                // TODO: permission request dialog appears after file selection
                Permissions.checkEXTWritePermission(this);
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
                startActivity(new Intent(this, MainPreferences.class));
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
        Code code = null;

        Log.d("result","requestCode=" + requestCode + ", resultCode="+ resultCode);

        if (data != null){
            bundle = data.getExtras();
            if (bundle!= null && Code.class.isInstance(bundle.getSerializable("Code"))){
                code= (Code) bundle.getSerializable("Code");
                Log.d(TAG, "onActivityResult: bundle != null && code is Code");
            }
        }

        if ((requestCode == REQUEST_CREATE_ENTRY || requestCode == REQUEST_EDIT_ENTRY) && bundle == null){
            return;
        }

        switch (requestCode){
            case REQUEST_CREATE_ENTRY:{
                switch (resultCode){
//                  add entry
                    case RESULT_OK:
                        if (Code.class.isInstance(code)){
                            kodaiViewModel.add(code);
                            updateAdapter();
                        }
                        break;
                        default:
                }
            }
            break;

            case REQUEST_EDIT_ENTRY:{
                switch (resultCode){
//                  update entry
                    case RESULT_OK:
                        kodaiViewModel.update(code);
                        updateAdapter();
                        break;
//                  delete entry
                    case RESULT_FIRST_USER:
                        kodaiViewModel.delete(code);
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
            case REQUEST_WRITE_PERMISSIONS:
                break;

            default:
        }
    }

    @Override
    public void onBackPressed() {
//        TODO: reset exitNow somewhere to false
        boolean closeOnBack = preferences.getBoolean("close_on_back",false);
        if (!closeOnBack) {
            super.onBackPressed();
            return;
        }

        if (exitNow) {
//            super.onBackPressed();
//            return;
            closeApp();
            return;
        }
        exitNow = true;
        Toast.makeText(getApplicationContext(), R.string.press_back_once_more_msg, Toast.LENGTH_SHORT).show();
    }

    private void closeApp(){
        finish();

    }

    /**
     * updates ListView
     */
    private void updateAdapter(){
        exitNow = false;        // reset back button
        adapter = new MyAdapter(this, kodaiViewModel);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void search(){
        // Using full text search, so adding % before and after search keyword if it's not empty
        StringBuilder keyword = new StringBuilder();
        String searchString = String.valueOf(searchEditText.getText().toString());
        if (!searchString.isEmpty()) {
            keyword.append("%").append(searchEditText.getText().toString()).append("%");
        }
        kodaiViewModel.setFilter(keyword.toString());
        updateAdapter();
    }

    private void createNewEntry(){
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            intent.putExtra("action", Util.ACTION_NEW);
            startActivityForResult(intent, Util.REQUEST_CREATE_ENTRY);
    }

    private void importCsvFile(Uri uri){
        Log.d("Import/URI", uri.getPath());

        List<Code> irasai = Util.loadEntriesFromURI(getApplicationContext(), uri);
            if (irasai!= null) {
                Log.v("importCsvFile()", "got " + irasai.size() + " entries");
                kodaiViewModel.getAppDatabase().codeDao().insertList(irasai);

                updateAdapter();
            } else{
                Log.d("Import/result", "result is null");
            }
    }
    private void exportCsvFile(Uri uri){
        Log.d("Export/URI", uri.getPath());
        List<Code> irasai = kodaiViewModel.getAppDatabase().codeDao().getAll();
        saveEntriesToURI(getApplicationContext(), irasai, uri);

    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}
