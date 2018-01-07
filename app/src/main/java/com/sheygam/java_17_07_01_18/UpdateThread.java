package com.sheygam.java_17_07_01_18;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by gregorysheygam on 07/01/2018.
 */

public class UpdateThread extends Thread {
    private Context context;
    private UpdateThreadCallback callback;
    private int position;
    private User user;

    public UpdateThread(Context context, UpdateThreadCallback callback, int position, User user) {
        this.context = context;
        this.callback = callback;
        this.position = position;
        this.user = user;
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
                users.remove(position);
                users.add(position,user);
                String save = "";
                for (int i = 0; i < users.size(); i++) {
                    save +=users.get(i).toString();
                    if(i != users.size()-1){
                        save+=";";
                    }
                }
                context.getSharedPreferences("CONTACTS",Context.MODE_PRIVATE)
                        .edit()
                        .putString("email.com",save)
                        .commit();

                callback.updateDone();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    interface UpdateThreadCallback{
        void updateDone();
    }
}
