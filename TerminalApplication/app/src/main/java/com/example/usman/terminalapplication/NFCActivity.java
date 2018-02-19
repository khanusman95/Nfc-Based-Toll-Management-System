package com.example.usman.terminalapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.text.format.Time;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class NFCActivity extends AppCompatActivity implements CreateNdefMessageCallback, LocationListener {
    NfcAdapter mNfcAdapter;
    TextView textView, info;
    PreferenceHelper preferenceHelper;
    PendingIntent mPendingIntent;
    EncryptDecrypt enigma;
    String encText;

    private LocationManager locationManager;
    double Latitude;
    double Longitude;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,   // 3 sec
                10, this);

        info = (TextView) findViewById(R.id.info);
        textView = (TextView) findViewById(R.id.nfcText);
        preferenceHelper = new PreferenceHelper(this);

        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Register callback
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

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

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {


        Time time = new Time();
        time.setToNow();
        String text = ("Details:\n\n" +
                "Start Time: " + time.format("%H:%M:%S")+"\n"
                + "UID:"+preferenceHelper.getCnic() + "\n"
                + "Name:"+preferenceHelper.getName()
        );
        NdefMessage msg = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            msg = new NdefMessage(
                    new NdefRecord[] { //NdefRecord.createMime(
                            //"info.androidhive.navigationdrawer.NFCActivity", text.getBytes()),
                            NdefRecord.createMime("info.androidhive.navigationdrawer.NFCActivity",text.getBytes())


                            /**
                             * The Android Application Record (AAR) is commented out. When a device
                             * receives a push with an AAR in it, the application specified in the AAR
                             * is guaranteed to run. The AAR overrides the tag dispatch system.
                             * You can add it back in to guarantee that this
                             * activity starts when receiving a beamed message. For now, this code
                             * uses the tag dispatch system.
                            */
                            //,NdefRecord.createApplicationRecord("com.example.android.beam")
                    });

        }
        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
            try {
                processIntent(getIntent());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    public void onPause(){
        super.onPause();

        if(NfcAdapter.getDefaultAdapter(this) != null){
            NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    void processIntent(Intent intent) {
        textView = (TextView) findViewById(R.id.nfcText);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        textView.setText(new String(msg.getRecords()[0].getPayload()));
        String str = new String(msg.getRecords()[0].getPayload());
        final String[] lst = str.split(",");
        Toast.makeText(NFCActivity.this, lst[0]+lst[1], Toast.LENGTH_SHORT).show();
        if(lst[3].equals("0") ) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NFCActivity.this, lst[3]+ "", Toast.LENGTH_SHORT).show();
                    InsertData(lst[1],lst[2],preferenceHelper.getPlaza(),preferenceHelper.getBooth(),Double.toString(Latitude),Double.toString(Longitude));
                }
            });
        }
        else if(lst[3].equals("1")){
            //update query
            Toast.makeText(NFCActivity.this, lst[3]+ "", Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UpdateData(lst[1],lst[2],preferenceHelper.getBooth(),Double.toString(Latitude),Double.toString(Longitude));
                }
            });
            Toast.makeText(NFCActivity.this, "else", Toast.LENGTH_SHORT).show();
        }
    }

    public void InsertData(final String cnic, final String fairid, final String plazaid, final String boothid, final String startLat, final String startLon){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String cnicHolder = cnic;
                String fairHolder = fairid;
                String plazaHolder = plazaid;
                String boothHolder = boothid;
                String latHolder = startLat;
                String lonHolder = startLon;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("cnic", cnicHolder));
                nameValuePairs.add(new BasicNameValuePair("fair_id", fairHolder));
                nameValuePairs.add(new BasicNameValuePair("plaza_ID", plazaHolder));
                nameValuePairs.add(new BasicNameValuePair("start_booth_ID", boothHolder));
                nameValuePairs.add(new BasicNameValuePair("startLat", latHolder));
                nameValuePairs.add(new BasicNameValuePair("startLon", lonHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("http://tollapi.000webhostapp.com/test.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                new SweetAlertDialog(NFCActivity.this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Congratulations!")
                        .setContentText("Your Trip Has Started")
                        .show();

                Toast.makeText(NFCActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(cnic, fairid, plazaid, boothid, startLat, startLon);
    }

    // Update Data---------------------------------------------------------------------------------------------------
    public void UpdateData(final String cnic, final String fairid, final String boothid, final String startLat, final String startLon){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String cnicHolder = cnic;
                String fairHolder = fairid;
                String boothHolder = boothid;
                String latHolder = startLat;
                String lonHolder = startLon;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("cnic", cnicHolder));
                nameValuePairs.add(new BasicNameValuePair("fair_id", fairHolder));
                nameValuePairs.add(new BasicNameValuePair("end_booth_ID", boothHolder));
                nameValuePairs.add(new BasicNameValuePair("endLat", latHolder));
                nameValuePairs.add(new BasicNameValuePair("endLon", lonHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost("https://tollapi.000webhostapp.com/test-update.php");

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                new SweetAlertDialog(NFCActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Congratulations!")
                        .setContentText("Your Trip Has Completed")
                        .show();

                Toast.makeText(NFCActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(cnic, fairid, boothid, startLat, startLon);
    }

    @Override
    public void onLocationChanged(Location location) {
        String str = "Latitude: "+location.getLatitude()+"Longitude: "+location.getLongitude();
        Latitude = location.getLatitude();
        Longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    /*void processIntent(Intent intent) throws Exception {
        textView = (TextView) findViewById(R.id.nfcText);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        textView.setText(msg.getRecords()[0].getPayload().toString());

        //GPS------------------------------------------------------------------
        gps = new GPSTracker(NFCActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            PreferenceManager.getDefaultSharedPreferences(NFCActivity.this).edit().putFloat("startLatitude", (float)latitude).commit();
            PreferenceManager.getDefaultSharedPreferences(NFCActivity.this).edit().putFloat("startLongitude", (float)longitude).commit();

//            String completeAddress = getCompleteAddressString(latitude,longitude);

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        //Dialog---------------------------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("UID:"+preferenceHelper.getCnic()+"\n"+"Time:"+ System.currentTimeMillis()+"\n"+"Start Location:"+"\n"+"Lat:"+ PreferenceManager.getDefaultSharedPreferences(NFCActivity.this).getFloat("startLatitude",0)+"Long:"+ PreferenceManager.getDefaultSharedPreferences(NFCActivity.this).getFloat("startLongitude",0)+"\n"+"Car Registration:"+msg.getRecords()[0].getPayload())
                .setTitle("Tap Update");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }*/
}