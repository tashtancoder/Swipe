package com.example.android.swipe.vbs.HttpRequestResponse;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.swipe.vbs.Parsers.jsonParser;
import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by android on 10/3/2015.
 */
public class HttpRequestTask extends AsyncTask <String, String, String> {
    public AsyncResponse delegate = null;
    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        Log.d("Response: ", responseString);
        /*String name = jsonParser.jsonParserToString("ADI", responseString);
        String surname = jsonParser.jsonParserToString("ADI", responseString);

        Log.d("ADI ", jsonParser.jsonParserToString("ADI", responseString) );
        Log.d("SOYADI ", jsonParser.jsonParserToString("SOYADI", responseString));
        */
        return responseString;
        //return name + "-" + surname;
    }

    @Override

    /*protected void onPostExecute(String result){
        super.onPostExecute(result);
    }*/
    protected void onPostExecute(String result){
        //delegate.processFinish(result);
        super.onPostExecute(result);
    }
}
