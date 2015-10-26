package com.example.android.swipe.vbs.Parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 10/5/2015.
 */
public class jsonParser {

    public static String jsonParserToString(String field, String jsonString){
        String defString = "";
        try {
            JSONArray user = new JSONArray(jsonString);
            JSONObject jsonObject = user.getJSONObject(0);
            defString = jsonObject.getString(field);
            Log.d("JSON data: ", defString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return defString;
    }


    public static String [] jsonParserToArray(String field, String jsonString){
        String [] fieldList = null;
        try {
            JSONArray user = new JSONArray(jsonString);
            fieldList= new String[user.length()];
            for (int i=0; i<fieldList.length; i++) {
                fieldList[i] = user.getJSONObject(i).getString(field);
                Log.d("Json Menu ", i + " " + fieldList[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fieldList;
    }

}
