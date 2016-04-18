package com.example.rsg.virtualremote;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

public class EditRemoteActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_remote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
        //File dir = getFilesDir();
        File[] subFiles = dir.listFiles();
        //TextView tmp = (TextView) findViewById(R.id.textView);

        LinearLayout buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);
        //Button buttons[] = new Button[subFiles.length];

        if(subFiles!= null){
            Log.d("msg", "didnt work");
            for(File file : subFiles){
                Log.d("msg", "iteration");
                Button button = new Button(this);
                button.setText(file.getName());
                buttonContainer.addView(button);
                button.setOnClickListener(this);
                //tmp.append(file.getName());
                //tmp.append("\n");
            }
        }
    }

    public void onClick(View v) {
        Button b = (Button)v;
        //int id = v.getId();
        String name = b.getText().toString();

        Intent changeRemoteIntent = new Intent(this, ChangeRemoteActivity.class);
        Bundle tmp = new Bundle();
        tmp.putString("remoteName", name); //Your id
        changeRemoteIntent.putExtras(tmp);
        startActivity(changeRemoteIntent);

    }
}
