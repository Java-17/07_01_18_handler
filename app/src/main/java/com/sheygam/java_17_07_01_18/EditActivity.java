package com.sheygam.java_17_07_01_18;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputName, inputEmail;
    FrameLayout progressFrame;
    Button saveBtn;
    User currentUser;
    int position;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        handler = new Handler();
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("USER");
        position = intent.getIntExtra("POS",-1);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        progressFrame = findViewById(R.id.progress_frame);
        progressFrame.setOnClickListener(null);
        saveBtn = findViewById(R.id.saveBtn);
        inputName.setText(currentUser.getName());
        inputEmail.setText(currentUser.getEmail());
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.saveBtn){
            progressFrame.setVisibility(View.VISIBLE);
            currentUser.setName(inputName.getText().toString());
            currentUser.setEmail(inputEmail.getText().toString());
            new UpdateThread(this,callback,position,currentUser).start();
        }
    }

    UpdateThread.UpdateThreadCallback callback = new UpdateThread.UpdateThreadCallback() {
        @Override
        public void updateDone() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }
    };
}
