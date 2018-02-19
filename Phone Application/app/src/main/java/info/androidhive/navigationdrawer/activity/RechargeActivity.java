package info.androidhive.navigationdrawer.activity;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import info.androidhive.navigationdrawer.R;

public class RechargeActivity extends AppCompatActivity {

    EditText recharge_amount;
    Button btn_recharge;
    String amount;

    String URL_TO_PHP = "http://tollapi.000webhostapp.com/recharge.php";
    String TAG_SUCCESS = "status";
    String TAG_STUFF = "credit_amount";
    PreferenceHelper preferenceHelper;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recharge_amount = (EditText) findViewById(R.id.recharge_amount);
        recharge_amount.setHintTextColor(Color.parseColor("#333333"));
        btn_recharge = (Button) findViewById(R.id.btn_recharge);
        preferenceHelper = new PreferenceHelper(this);

        amount = recharge_amount.getText().toString();

        btn_recharge.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                if(recharge_amount.getText().equals("")){
                    Toast.makeText(RechargeActivity.this, "Recharge Amount Field Empty!", Toast.LENGTH_SHORT).show();
                }else{
                    amount = recharge_amount.getText().toString();
                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(RechargeActivity.this)
                                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                    .setContentTitle("Recharge Succeeded")
                                    .setContentText(amount+" PKR, Were Successfully added to your account");
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotificationManager.notify(001, mBuilder.build());

                    new testing().execute();
                    recharge_amount.setText("");
                }
            }
        });
    }

    class testing extends AsyncTask<Void,Void,Void> {

        @Override
        public Void doInBackground(Void... args){
            HashMap<String , String> postDataParams = new HashMap<String, String>();
            postDataParams.put("cnic", preferenceHelper.getCnic());
            postDataParams.put("amount", amount);
            performPostCall(URL_TO_PHP, postDataParams);

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            AlertDialog.Builder builder = new AlertDialog.Builder(RechargeActivity.this);
            builder.setMessage("Recharge Succeeded")
                    .setTitle("Credits Recharge");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
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
                    Log.e("Res:", response);
                }

            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
