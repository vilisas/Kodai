package lt.sutemos.kodai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import lt.sutemos.kodai.Utils.Util;

import static lt.sutemos.kodai.Utils.Util.RESULT_DELETE;

public class InfoActivity extends AppCompatActivity {

    private ImageButton deleteImageButton;
    private Button cancelButton;
    private Button saveButton;
    private EditText addressEditText;
    private EditText codeEditText;
    private EditText extraInfoEditText;
    private int currentAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Bundle extras = getIntent().getExtras();

        deleteImageButton = (ImageButton) findViewById(R.id.aiDeleteButtonID);
        cancelButton = (Button) findViewById(R.id.aiCancelButtonID);
        saveButton = (Button) findViewById(R.id.aiSaveButtonID);
        addressEditText = (EditText) findViewById(R.id.aiAddressID);
        codeEditText = (EditText) findViewById(R.id.aiCodeID);
        extraInfoEditText = (EditText) findViewById(R.id.aiExtraInfoID);

        if (extras != null) {
            currentAction =extras.getInt("action");
//            Toast.makeText(getApplicationContext(), "Action: " + currentAction, Toast.LENGTH_LONG).show();

            switch (currentAction){
                case Util.ACTION_NEW:
                    deleteImageButton.setEnabled(false);
                    deleteImageButton.setVisibility(View.GONE);
                    break;

                case Util.ACTION_EDIT:
                    addressEditText.setText(extras.getString("address"));
                    codeEditText.setText(extras.getString("code"));
                    extraInfoEditText.setText(extras.getString("info"));
                    break;

                    default:
            }
        }

        deleteImageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setResult(RESULT_DELETE, getIntent());
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
//                Log.d("id", String.valueOf(returnIntent.getExtras().getInt("id")));
//                Toast.makeText(getApplicationContext(), "id:" +returnIntent.getExtras().getInt("id"), Toast.LENGTH_SHORT).show();
                returnIntent.putExtra("address", addressEditText.getText().toString());
                returnIntent.putExtra("code", codeEditText.getText().toString());
                returnIntent.putExtra("info", extraInfoEditText.getText().toString());
                returnIntent.putExtra("action", currentAction);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });

    }
}
