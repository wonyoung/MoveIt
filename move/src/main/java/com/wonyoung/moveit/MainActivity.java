package com.wonyoung.moveit;

import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String DEFAULT_SOURCE_FOLDER = "/sdcard/DCIM/Camera";
    private static final String DEFAULT_TARGET_FOLDER = "/sdcard/DCIM/Camera/newFolder";
    private static final String DEFAULT_REG_EX = "\\d{13,}.jpg";
    private EditText sourceEdit;
    private EditText targetEdit;
    private EditText regexEdit;

    private FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileManager = new FileManager(this);

        setContentView(R.layout.activity_main);
        setup();
    }

    private void setup() {
        sourceEdit = (EditText) findViewById(R.id.source_editText);
        targetEdit = (EditText) findViewById(R.id.target_editText);
        regexEdit = (EditText) findViewById(R.id.regex_editText);

        sourceEdit.setText(DEFAULT_SOURCE_FOLDER);
        targetEdit.setText(DEFAULT_TARGET_FOLDER);
        regexEdit.setText(DEFAULT_REG_EX);

        Button moveButton = (Button) findViewById(R.id.move_button);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.d("moveit", "selected");
            move();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void move() {
        final String sourceFolder = sourceEdit.getText().toString();
        final String targetFolder = targetEdit.getText().toString();
        final String regEx = regexEdit.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                fileManager.move(sourceFolder, targetFolder, regEx);
            }
        }).start();

        Toast.makeText(this, String.format("Move (%s) to (%s) by (%s)", sourceFolder, targetFolder, regEx),Toast.LENGTH_SHORT).show();
    }

}
