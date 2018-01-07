package com.sheygam.java_17_07_01_18;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView myList;
    FrameLayout progressFrame;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = findViewById(R.id.my_list);
        progressFrame = findViewById(R.id.progress_frame);
        progressFrame.setOnClickListener(null);
        handler = new Handler();
        myList.setAdapter(new MyAdapter(new ArrayList<User>()));
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("USER",(User)parent.getAdapter().getItem(position));
                intent.putExtra("POS",position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        progressFrame.setVisibility(View.VISIBLE);
        new LoadThread(this, new LoadThread.LoadThreadCallback() {
            ArrayList<User> arr;
            @Override
            public void onLoad(ArrayList<User> users) {
                arr = users;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myList.setAdapter(new MyAdapter(arr));
                        progressFrame.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
        super.onStart();
    }

    class MyAdapter extends BaseAdapter{

        ArrayList<User> users;

        public MyAdapter(ArrayList<User> users) {
            this.users = users;
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null)
                convertView =  getLayoutInflater().inflate(R.layout.my_row,parent,false);
            TextView nameTxt = convertView.findViewById(R.id.name_txt);
            TextView emailTxt = convertView.findViewById(R.id.email_txt);
            nameTxt.setText(users.get(position).getName());
            emailTxt.setText(users.get(position).getEmail());
            return convertView;
        }
    }

}
