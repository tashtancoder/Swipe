package com.example.android.swipe.vbs;




import android.os.AsyncTask;

//Http related imports
//import com.loopj.android.http.AsyncHttpClient;
import com.example.android.swipe.R;
import com.example.android.swipe.vbs.HttpRequestResponse.AsyncResponse;
import com.example.android.swipe.vbs.HttpRequestResponse.HttpRequestTask;
import com.example.android.swipe.vbs.Parsers.jsonParser;
import com.example.android.swipe.vbs.userClasses.ConstantValues;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.*;
//Java io imports
import java.io.*;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import com.example.android.swipe.vbs.DBclasses.localUsersDB;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.WindowManager;
import java.io.IOException;
import java.sql.SQLDataException;
import java.util.concurrent.ExecutionException;

public class addUser extends AppCompatActivity implements AsyncResponse{
    Button buttonNewUser;
    EditText editTextUsername, editTextServer, editTextPassword;
    private ContentValues conValues;
    String userName, serverName, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        /*String uri = "http://192.168.1.103:84/WebServices/LoginService.aspx?usr=5325412197&ps=ccc";
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.delegate = this;
        httpRequestTask.execute(uri);
        */
        editTextUsername = (EditText)findViewById(R.id.editTextUser);
        editTextServer = (EditText)findViewById(R.id.editTextServer);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        Intent intent = getIntent();
        editTextUsername.setText("");
        editTextPassword.setText("");
        editTextUsername.requestFocus();
    }

    @Override
    public  void onResume(){
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void buttonAddNewUserOnClick(View v){
        buttonNewUser = (Button) v;
        //localUserDB localDB = new localUsersDB(getApplicationContext(), "LocalUserData.db", null, localUsersDB.DATABASE_VERSION);
        Log.d("ButtonNewUserAdd", "WORKED ADD USER BUTTON");
        userName = editTextUsername.getText().toString();
        serverName = editTextServer.getText().toString();
        password = editTextPassword.getText().toString();
        if (userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            MessageBox("Fill all blanks");
        } else {
            /*localUsersDB localDB = new localUsersDB(getApplicationContext());
            SQLiteDatabase dbObject = localDB.getReadableDatabase();

            Cursor dbCursor = dbObject.query(localUsersDB.TABLE_NAME, new String[]{localUsersDB.userNo},
                    localUsersDB.userNo + " =" + "\"" + userName + "\"", null, null, null, null);
            */

            if (isExist(userName)){
            //if (dbCursor.getCount() != 0 ){
                editTextUsername.setText("");
                editTextPassword.setText("");
                MessageBox("User already exists");
                editTextUsername.requestFocus();

            } else {
                //String uri = "http://192.168.1.103:84/WebServices/LoginService.aspx?usr=5325412197&ps=ccc";
                //String uri = "http://192.168.1.109:84/WebServices/LoginService.aspx?usr=" + userName + "&ps=" + password;
                StringBuilder uri = new StringBuilder();
                uri.append(ConstantValues.uriLogin).append("usr=").append(userName).append("&ps=").append(password);
                HttpRequestTask httpRequestTask = new HttpRequestTask();
                String response = "";
                try {
                    response = httpRequestTask.execute(uri.toString()).get();
                    if (response.equalsIgnoreCase("[]")){
                        editTextUsername.setText("");
                        editTextPassword.setText("");
                        editTextUsername.requestFocus();
                        MessageBox("Username or password is incorrect!");
                    }
                    else{
                        String name = jsonParser.jsonParserToString("ADI", response);
                        String surname = jsonParser.jsonParserToString("SOYADI", response);
                        int userNo = Integer.parseInt(jsonParser.jsonParserToString("Kimlik", response));
                        int sezon = Integer.parseInt(jsonParser.jsonParserToString("sezon", response));
                        int sube = Integer.parseInt(jsonParser.jsonParserToString("sube", response));
                        String userTypeLong = jsonParser.jsonParserToString("UserType", response);
                        String userType = String.valueOf(userTypeLong.charAt(0)).toLowerCase();
                        Log.d("Name RES ", name);
                        Log.d("Surname RES ", surname);
                        Log.d("userNo RES ", "" + userNo);
                        Log.d("sezon RES ", "" + sezon);
                        Log.d("sube RES ", "" + sube);
                        Log.d("UserType RES ", userType);
                        conValues = new ContentValues();
                        conValues.put(localUsersDB.username, userName);
                        conValues.put(localUsersDB.password, password);
                        conValues.put(localUsersDB.isActive, "0");
                        conValues.put(localUsersDB.surname, surname );
                        conValues.put(localUsersDB.name, name);
                        conValues.put(localUsersDB.userNo, userNo);
                        conValues.put(localUsersDB.sube, sube);
                        conValues.put(localUsersDB.sezon, sezon);
                        conValues.put(localUsersDB.userType, userType);
                        insertToDB(conValues);
                    /*try {
                        dbObject.insert(localDB.TABLE_NAME, null, conValues);
                        Log.d("DATABASE", "SUCCESSFUL");
                    } catch (SQLiteException e){
                        Log.d("Database", "DB ERROR");
                    }*/

                        //localDB.close();
                        //dbCursor.close();
                        conValues.clear();

                        Intent addUserIntent = new Intent(addUser.this, activeUsers.class);
                        startActivity(addUserIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    MessageBox("Network Connection Problem");

                } catch (ExecutionException e) {
                    e.printStackTrace();
                    MessageBox("Network Connection Problem");

                }
                Log.d("RESULT ", response);



                //Log.d("ADI ", jsonParser.jsonParserToString("ADI", response) );
                //Log.d("SOYADI ", jsonParser.jsonParserToString("SOYADI", response));




            }
            //localDB.close();

        }
    }

    public boolean isExist(String username){
        localUsersDB localDB = new localUsersDB(getApplicationContext());
        SQLiteDatabase dbObject = localDB.getReadableDatabase();

        Cursor dbCursor = dbObject.query(localUsersDB.TABLE_NAME, new String[]{localUsersDB.username},
                localUsersDB.username + " =" + "\"" + username + "\"", null, null, null, null);
        int cursorCount = dbCursor.getCount();
        dbCursor.close();
        localDB.close();
        dbObject.close();
        return cursorCount != 0;
    }

    public void insertToDB(ContentValues conValues){
        localUsersDB localDB = new localUsersDB(getApplicationContext());
        SQLiteDatabase dbObject = localDB.getWritableDatabase();
        try {
            dbObject.insert(localDB.TABLE_NAME, null, conValues);
            Log.d("DATABASE", "SUCCESSFUL");
        } catch (SQLiteException e){
            Log.d("Database", "DB ERROR");
        }


        localDB.close();
        dbObject.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
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

    private void MessageBox(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                addUser.this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Tamam",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
								/*
								 * Whatever you wanna do when Tamam is clicked.
								 */
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void processFinish(String output) {
        Log.d("Process Finish", output);
    }
}
