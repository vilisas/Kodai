package lt.sutemos.kodai;

/**
 * Info Activity
 * Created by Vilius Bilinkevicius on 2019.01
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import lt.sutemos.kodai.database.Code;
import lt.sutemos.kodai.utils.Util;

public class InfoActivity extends AppCompatActivity {
    private final String TAG=AppCompatActivity.class.toString();

    private ImageButton deleteImageButton;
    private Button cancelButton;
    private Button saveButton;
    private EditText addressEditText;
    private EditText codeEditText;
    private EditText extraInfoEditText;
    private Code code;
    private int currentAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Bundle extras = getIntent().getExtras();

        deleteImageButton = findViewById(R.id.aiDeleteButtonID);
        cancelButton = findViewById(R.id.aiCancelButtonID);
        saveButton = findViewById(R.id.aiSaveButtonID);
        addressEditText = findViewById(R.id.aiAddressID);
        codeEditText = findViewById(R.id.aiCodeID);
        extraInfoEditText = findViewById(R.id.aiExtraInfoID);
        Log.d(TAG, "onCreate: infoActivity");

        if (extras != null) {
            currentAction =extras.getInt("action");

            switch (currentAction){
                case Util.ACTION_NEW:
                    deleteImageButton.setEnabled(false);
                    deleteImageButton.setVisibility(View.GONE);
                    code = new Code();
                    break;

                case Util.ACTION_EDIT:
                    code = (Code) extras.getSerializable("Code");
                    Log.d(TAG, "onCreate: id="+code.getId());
                    Log.d(TAG, "onCreate: address="+code.getAddress());
                    Log.d(TAG, "onCreate: code="+code.getCode());
                    Log.d(TAG, "onCreate: info="+code.getInfo());
                    addressEditText.setText(code.getAddress());
                    codeEditText.setText(code.getCode());
                    extraInfoEditText.setText(code.getInfo());
                    break;

                    default:
            }
        }
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.delete_hint, Toast.LENGTH_LONG).show();
            }
        });

        deleteImageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.msg_entry_deleted, Toast.LENGTH_LONG).show();
                setResult(RESULT_FIRST_USER, getIntent());
                finish();
                return false;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = getIntent();
                Log.d(TAG, "onClick: saveButton");
                // show toast with error message and return if empty
                if (String.valueOf(addressEditText.getText()).isEmpty()){
                    addressEditText.requestFocus();
                    addressEditText.setBackgroundColor(getResources().getColor(R.color.colorEmptyField));
                    Toast.makeText(getApplicationContext(), R.string.err_empty, Toast.LENGTH_LONG).show();
                    return;
                }
                code.setAddress(addressEditText.getText().toString());
                code.setCode(codeEditText.getText().toString());
                code.setInfo(extraInfoEditText.getText().toString());
                returnIntent.putExtra("Code", code);
                returnIntent.putExtra("action", currentAction);

                setResult(RESULT_OK,returnIntent);
                Log.d(TAG, "onClick: before finish()");
                finish();
            }
        });

    }
}
