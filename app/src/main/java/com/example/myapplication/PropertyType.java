package com.example.myapplication;

import static android.os.Build.ID;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PropertyType extends AppCompatActivity {

    TextView DemoData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_type);
        DemoData=findViewById(R.id.dataDemo);
        JasonTask jasonTask=new JasonTask();
        jasonTask.execute();
    }
    @SuppressLint("StaticFieldLeak")
    public class JasonTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;//initialize as null
            BufferedReader bufferedReader = null; //intialize as null

            int ID;
            String Name;
            try {
                URL url=new URL("https://e2msandbox.datalibrary.io/api/propertytypes"); //get URL from the server
                httpURLConnection= (HttpURLConnection) url.openConnection(); //http connection open
                InputStream inputStream=httpURLConnection.getInputStream(); //for read byte code data from the url
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream)); //convert byte code into character cde
                StringBuffer stringBuffer=new StringBuffer(); //put string character
                String line=""; //for read line by line
                StringBuffer lastBuffer=new StringBuffer();
                while((line=bufferedReader.readLine())!=null){ //check the url if not null
                    stringBuffer.append(line); //check every line of the url
                }
                String file=stringBuffer.toString();
                JSONObject fileObject=new JSONObject(file);
                JSONArray jsonArray=fileObject.getJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject arrayObject=jsonArray.getJSONObject(i);
                    ID=arrayObject.getInt("id");
                    Name=arrayObject.getString("name");
                    lastBuffer.append(ID+"\n"+Name+"\n");
                }
                return  lastBuffer.toString(); //get all the request file

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }finally {
                assert httpURLConnection != null;
                httpURLConnection.disconnect(); //url data connection off
                try {
                    bufferedReader.close(); //stops the buffer
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DemoData.setText(s);
        }
    }
}