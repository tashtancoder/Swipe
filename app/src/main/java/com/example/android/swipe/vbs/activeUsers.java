package com.example.android.swipe.vbs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.*;
import android.view.View.OnClickListener;

import java.util.*;

import com.example.android.swipe.R;
import com.example.android.swipe.vbs.DBclasses.localUsersDB;
import com.example.android.swipe.vbs.Gestures.OnSwipeTouchListener;

import java.util.ArrayList;

public class activeUsers extends AppCompatActivity {

    GridView gridView;
    ArrayAdapter <String> adapter;
    String [] nameSurnameArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_users);
        Intent intent = getIntent();
        gridView = (GridView) findViewById(R.id.gridView);

        setToList(gridView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_active_users, menu);
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

    public void setToList(View v){
        //List list = readUserFromDB();
        gridView = (GridView)v;
        invalidation();
        /*String selectAllQuery = "SELECT * FROM " + localUsersDB.TABLE_NAME;
        nameSurnameArray = readUserFromDB(selectAllQuery, this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, nameSurnameArray);
        gridView.setAdapter(adapter);
        gridView.setChoiceMode(gridView.CHOICE_MODE_SINGLE);
        String selectDefaultUser = "SELECT * FROM " + localUsersDB.TABLE_NAME + " WHERE " + localUsersDB.isActive + "=1";
        String [] defUser = readUserFromDB(selectDefaultUser, this);
        if (defUser.length == 1){
            gridView.setItemChecked(findUserInArray(nameSurnameArray, defUser[0]), true);
        }*/
        //gridView.setItemChecked(0, true);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameSurname = (String) ((TextView) view).getText();
                Log.d("Item selected ", nameSurname);
                String name = nameSurname.substring(0, nameSurname.indexOf('_'));
                String surname = nameSurname.substring(nameSurname.indexOf('_') + 1);
                changeUser(name, surname);
                Intent intent = new Intent(activeUsers.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        gridView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Click", "Long Click");


                return true;
            }
        });
        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Touch", "Long Touch");
                String nameSurname = (String) ((TextView) view).getText();
                Log.d("Item selected ", nameSurname);
                String name = nameSurname.substring(0, nameSurname.indexOf('_'));
                String surname = nameSurname.substring(nameSurname.indexOf('_') + 1);
                deleteUser(name, surname, activeUsers.this);
                //setToList(view);
                gridView.invalidateViews();
                /*String selectAllQuery = "SELECT * FROM " + localUsersDB.TABLE_NAME;
                nameSurnameArray = readUserFromDB(selectAllQuery, activeUsers.this);
                adapter = new ArrayAdapter<String>(activeUsers.this, android.R.layout.simple_list_item_single_choice, nameSurnameArray);
                gridView.setAdapter(adapter);
                gridView.setChoiceMode(gridView.CHOICE_MODE_SINGLE);
                String selectDefaultUser = "SELECT * FROM " + localUsersDB.TABLE_NAME + " WHERE " + localUsersDB.isActive + "=1";
                String[] defUser = readUserFromDB(selectDefaultUser, activeUsers.this);
                if (defUser.length == 1) {
                    gridView.setItemChecked(findUserInArray(nameSurnameArray, defUser[0]), true);
                }

                parent.invalidate();
                adapter.notifyDataSetChanged();
                view.invalidate();
                gridView.invalidate();
                gridView.postInvalidate();
                gridView.invalidateViews();*/
                return true;
            }
        });

        gridView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Toast.makeText(activeUsers.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(activeUsers.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(activeUsers.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(activeUsers.this, "bottom", Toast.LENGTH_SHORT).show();
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        /*gridView.setOnClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_LONG).show();
            }
        });*/

    }



    public static String [] readUserFromDB(String sqlQuery, Context context){
        List <String> list = new ArrayList<String>();
        localUsersDB localDB = new localUsersDB(context);
        SQLiteDatabase dbObject = localDB.getReadableDatabase();
        //String selectAllQuery = "SELECT * FROM " + localUsersDB.TABLE_NAME;
        String selectDefaultQuery = "SELECT * FROM " + localUsersDB.TABLE_NAME + " WHERE " + localUsersDB.isActive + "=1";
        Cursor cursor = dbObject.rawQuery(sqlQuery, null);
        Cursor defCursor = dbObject.rawQuery(selectDefaultQuery, null);
        if (defCursor.moveToFirst()){
            Log.d("NAME ", defCursor.getString(defCursor.getColumnIndex(localUsersDB.name)));
        }
        Log.d("DefaultUser ", "" + defCursor.getCount());
        //String [] nameSurnameArray = new String [cursor.getCount()];
        //if (cursor.moveToFirst())
        while (cursor.moveToNext()) {
            String nameSurname = cursor.getString(cursor.getColumnIndex(localUsersDB.name)) + "_" + cursor.getString(cursor.getColumnIndex(localUsersDB.surname));
            Log.d("DB DATA: ", nameSurname);
            list.add(nameSurname);
           //list.add(cursor.getString(cursor.getColumnIndex(localUsersDB.name)) + " " + cursor.getString(cursor.getColumnIndex(localUsersDB.surname)));
        } ;
        //Cursor dbCursor = dbObject.query(localUsersDB.TABLE_NAME, new String[]{localUsersDB.userNo},
         //       localUsersDB.userNo + " =" + "\"" + userNo + "\"", null, null, null, null);
        //int cursorCount = dbCursor.getCount();
        cursor.close();
        localDB.close();
        String [] nameSurnameArray = new String[list.size()];
        list.toArray(nameSurnameArray);
        Log.d("DB Data: ", list.toString());
        //return new String []{"Student1", "Student2", "Student3", "Student4"};
        return nameSurnameArray;
    }

    public void deleteUser(final String name, final String surname, final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmTitleDelete);
        builder.setMessage(R.string.confirmDeleteMessage);
        builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                localUsersDB localDB = new localUsersDB(context);
                SQLiteDatabase dbObject = localDB.getWritableDatabase();
                dbObject.execSQL("delete from " + localUsersDB.TABLE_NAME + " where " + localUsersDB.name + "='" + name + "' and " + localUsersDB.surname + "='" + surname + "'");
                dbObject.close();
                localDB.close();
                invalidation();
                //gridView.invalidateViews();


            }
        });

        builder.setNegativeButton(R.string.button_cancel, null);
        builder.show();

    }

    public void invalidation(){
        String selectAllQuery = "SELECT * FROM " + localUsersDB.TABLE_NAME;
        nameSurnameArray = readUserFromDB(selectAllQuery, activeUsers.this);
        adapter = new ArrayAdapter<String>(activeUsers.this, android.R.layout.simple_list_item_single_choice, nameSurnameArray);
        gridView.setAdapter(adapter);
        gridView.setChoiceMode(gridView.CHOICE_MODE_SINGLE);
        String selectDefaultUser = "SELECT * FROM " + localUsersDB.TABLE_NAME + " WHERE " + localUsersDB.isActive + "=1";
        String[] defUser = readUserFromDB(selectDefaultUser, activeUsers.this);
        if (defUser.length == 1) {
            gridView.setItemChecked(findUserInArray(nameSurnameArray, defUser[0]), true);
        }

        //parent.invalidate();
        adapter.notifyDataSetChanged();
        //view.invalidate();
        gridView.invalidate();
        gridView.postInvalidate();
        gridView.invalidateViews();
    }

    public void changeUser(String name, String surname){
        Log.d("Name ", name);
        Log.d("Surname ", surname);
        localUsersDB localDB = new localUsersDB(getApplicationContext());
        SQLiteDatabase dbObject = localDB.getWritableDatabase();
        dbObject.execSQL("UPDATE " + localUsersDB.TABLE_NAME + " SET " + localUsersDB.isActive
                + "='0' WHERE " + localUsersDB.isActive + "='1'");
        dbObject.execSQL("UPDATE " + localUsersDB.TABLE_NAME + " SET " + localUsersDB.isActive
                + "='1' WHERE " + localUsersDB.name + "='" + name + "' AND " + localUsersDB.surname + "='" + surname + "'");
        dbObject.close();
        localDB.close();

    }

    public int findUserInArray(String [] array, String searchedString){
        for (int i = 0; i<array.length; i++)
            if (array[i].equalsIgnoreCase(searchedString))
                return i;
        return -1;
    }
}
