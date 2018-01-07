package com.sheygam.java_17_07_01_18;

import android.content.Context;

import java.util.ArrayList;


/**
 * Created by gregorysheygam on 07/01/2018.
 */

public class LoadThread extends Thread {
    private Context context;
    private LoadThreadCallback callback;

    public LoadThread(Context context, LoadThreadCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            sleep(3000);
            String contacts = context.getSharedPreferences("CONTACTS",Context.MODE_PRIVATE)
                    .getString("email.com",null);
            if(contacts!=null){
                String[] cnts = contacts.split(";");
                ArrayList<User> users = new ArrayList<>();
                for (String str:cnts) {
                    users.add(User.newInstance(str));
                }
                callback.onLoad(users);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    interface LoadThreadCallback{
        void onLoad(ArrayList<User> user);
    }
}
