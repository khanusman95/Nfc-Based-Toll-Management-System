package com.example.usman.terminalapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class CreditsActivity extends AppCompatActivity {


    String URL_TO_PHP = "http://192.168.0.102:8888/balance.php";
    String TAG_SUCCESS = "status";
    String TAG_STUFF = "credit_amount";

    String n;

    TextView balance;
    Button callcred;
    PreferenceHelper preferenceHelper;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


             n = "n";
        balance = (TextView) findViewById(R.id.balance);
        callcred = (Button) findViewById(R.id.callcred);
        preferenceHelper = new PreferenceHelper(this);

        balance.setText(PreferenceManager.getDefaultSharedPreferences(CreditsActivity.this).getString("Credits", "defaultStringIfNothingFound"));

        callcred.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //Toast.makeText(CreditsActivity.this,"click",Toast.LENGTH_LONG).show();
                new testing().execute();


                //HashMap<String , String> postDataParams = new HashMap<String, String>();

                //postDataParams.put("cnic", preferenceHelper.getCnic());

                //n = performPostCall("http://192.168.0.106:8888/balance.php", postDataParams);


            }
        });

        callcred.performClick();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            CreditsActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static final void recreateActivity(final Activity a){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            a.recreate();
        }else{
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0,0);
            a.startActivity(intent);
            a.overridePendingTransition(0,0);
        }
    }

    class testing extends AsyncTask<Void,Void,String> {
        @Override
        public String doInBackground(Void... args){
            HashMap<String , String> postDataParams = new HashMap<String, String>();
            postDataParams.put("cnic", preferenceHelper.getCnic());
            performPostCall("http://192.168.0.102:8888/balance.php", postDataParams);
            n = performPostCall("http://192.168.0.102:8888/balance.php", postDataParams);

            return n;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            String a = "";
            String b = "";
            JSONObject c = null;
            super.onPostExecute(aVoid);
            //Toast.makeText(CreditsActivity.this,aVoid.toString(),Toast.LENGTH_LONG).show();
            TextView txt = (TextView)((CreditsActivity.this)).findViewById(R.id.balance);
            try {
                jsonObject = new JSONObject(aVoid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                a = jsonObject.getString("data");
                jsonArray = jsonObject.getJSONArray("data");
                 c = jsonArray.getJSONObject(0);
                b = c.getString("credit_amount");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            txt.setText(b+" "+"PKR");
                //Toast.makeText(CreditsActivity.this,b.toString(), Toast.LENGTH_SHORT).show();

            txt.invalidate();
        }

    }

    public String performPostCall(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                    n = response;
                    balance.setText(response);
                    Log.e("Res:", response);
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("Credits", "Wait...").commit();


        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}

