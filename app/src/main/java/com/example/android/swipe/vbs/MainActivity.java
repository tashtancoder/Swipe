package com.example.android.swipe.vbs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.android.swipe.ProfileMainActivity;
import com.example.android.swipe.R;
import com.example.android.swipe.vbs.DBclasses.localUsersDB;
import com.example.android.swipe.vbs.userClasses.User;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button buttonAddUser, buttonLoginUser, buttonChangeUser;
    public boolean addUserFlag=false;
    public User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAddUser = (Button) findViewById(R.id.addButton);
        buttonLoginUser = (Button) findViewById(R.id.loginButton);
        buttonChangeUser = (Button) findViewById(R.id.changeButton);

        Intent addUserIntent = getIntent();
        /*String uri = "http://192.168.1.103:84/WebServices/LoginService.aspx?usr=5325412197&ps=ccc";
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute(uri);*/

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("onResume ", "onResume");
        String selectDefaultUser = "SELECT * FROM " + localUsersDB.TABLE_NAME + " WHERE " + localUsersDB.isActive + "=1";
        String [] defUser = activeUsers.readUserFromDB(selectDefaultUser, this);
        Log.d("DEF USER ", defUser.toString());
        if (defUser.length == 1){
            buttonLoginUser.setText(defUser[0]);
            buttonLoginUser.setEnabled(true);
        }
        else buttonLoginUser.setEnabled(false);

    }



    public void buttonLoginUserOnClick(View v){
        buttonLoginUser = (Button) v;
        Log.d("ButtonAdd", "WORKED");

        //String name = nameSurname.substring(0, nameSurname.indexOf('_'));
        //String surname = nameSurname.substring(nameSurname.indexOf('_') + 1);
        readUserFromDB();
        Intent profileIntent = new Intent (MainActivity.this, ProfileMainActivity.class);
        profileIntent.putExtra("userObject", user);
        startActivity(profileIntent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    public void buttonAddUserOnClick(View v){
        //buttonAddUser = (Button) v;
        Log.d("ButtonAdd", "WORKED");
        Intent intent = new Intent (MainActivity.this, addUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void buttonChangeUserOnClick(View v){
        //buttonChangeUser = (Button) v;
        Log.d("ButtonChange", "Change button WORKED");
        Intent intent = new Intent (MainActivity.this, activeUsers.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void readUserFromDB(){
        //List<String> list = new ArrayList<String>();
        localUsersDB localDB = new localUsersDB(getApplicationContext());
        SQLiteDatabase dbObject = localDB.getReadableDatabase();
        //String selectAllQuery = "SELECT * FROM " + localUsersDB.TABLE_NAME;
        String selectDefaultQuery = "SELECT * FROM " + localUsersDB.TABLE_NAME + " WHERE " + localUsersDB.isActive + "=1";
        //Cursor cursor = dbObject.rawQuery(sqlQuery, null);
        Cursor defCursor = dbObject.rawQuery(selectDefaultQuery, null);
        if (defCursor.moveToFirst()){
            String name = defCursor.getString(defCursor.getColumnIndex(localUsersDB.name));
            String surname = defCursor.getString(defCursor.getColumnIndex(localUsersDB.surname));
            String username = defCursor.getString(defCursor.getColumnIndex(localUsersDB.username));
            String password = defCursor.getString(defCursor.getColumnIndex(localUsersDB.password));
            String userType = defCursor.getString(defCursor.getColumnIndex(localUsersDB.userType));
            int userNo = Integer.parseInt(defCursor.getString(defCursor.getColumnIndex(localUsersDB.userNo)));
            int sube = Integer.parseInt(defCursor.getString(defCursor.getColumnIndex(localUsersDB.sube)));
            int sezon = Integer.parseInt(defCursor.getString(defCursor.getColumnIndex(localUsersDB.sezon)));
            Log.d("User ", name + " " + surname + " " + username + " " + password + " " + userType + " " + userNo + " " + sube + " " + sezon);
            //user = new User(username, name, surname, password);
            user = new User(username, name, password, surname, userNo, userType, sube, sezon);
        }
        //Log.d("DefaultUser ", "" + defCursor.getCount());
        //String [] nameSurnameArray = new String [cursor.getCount()];
        //if (cursor.moveToFirst())
        /*while (cursor.moveToNext()) {
            String nameSurname = cursor.getString(cursor.getColumnIndex(localUsersDB.name)) + "_" + cursor.getString(cursor.getColumnIndex(localUsersDB.surname));
            Log.d("DB DATA: ", nameSurname);
            list.add(nameSurname);
            //list.add(cursor.getString(cursor.getColumnIndex(localUsersDB.name)) + " " + cursor.getString(cursor.getColumnIndex(localUsersDB.surname)));
        } */
        //Cursor dbCursor = dbObject.query(localUsersDB.TABLE_NAME, new String[]{localUsersDB.userNo},
        //       localUsersDB.userNo + " =" + "\"" + userNo + "\"", null, null, null, null);
        //int cursorCount = dbCursor.getCount();
        defCursor.close();
        localDB.close();
        //String [] nameSurnameArray = new String[list.size()];
        //list.toArray(nameSurnameArray);
        //Log.d("DB Data: ", list.toString());
        //return new String []{"Student1", "Student2", "Student3", "Student4"};
        //return user;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


