package info.androidhive.navigationdrawer.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.androidhive.navigationdrawer.R;


public class NFCActivity extends AppCompatActivity implements CreateNdefMessageCallback {
    NfcAdapter mNfcAdapter;
    ImageView tapinstruction;
    TextView textView, info, textView4;
    PreferenceHelper preferenceHelper;
    PendingIntent mPendingIntent;
    EncryptDecrypt enigma;
    String encText, text, vehicleType;
    Time time;
    String ServerURL = "http://tollapi.000webhostapp.com//vehicleType.php";
    //GPS
    GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        vehicleType = "1";
        info = (TextView) findViewById(R.id.info);
        textView = (TextView) findViewById(R.id.nfcText);
        textView4 = (TextView) findViewById(R.id.textView4);
        tapinstruction = (ImageView) findViewById(R.id.tapinstruction);
        preferenceHelper = new PreferenceHelper(this);
        time = new Time();
        time.setToNow();

        //---vt test---
        new GetHttpResponse(NFCActivity.this).execute();

        preferenceHelper.putIsTouched("0");
        /*if(preferenceHelper.getIsTouched()){
        textView4.setText(Boolean.toString(preferenceHelper.getIsTouched()));}else{
            Toast.makeText(NFCActivity.this, "nul", Toast.LENGTH_SHORT).show();
        }*/

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

     /*   runOnUiThread(new Runnable(){

            @Override
            public void run(){
                if (preferenceHelper.getIsTouched()){

                    text = ("Details:\n\n" +
                               "Start Time: " + time.format("%H:%M:%S")+"\n"
                                 + "UID:"+preferenceHelper.getCnic() + "\n"
                                   + "Name:"+preferenceHelper.getName()+"\n"
                            +"Status:"+Boolean.toString(preferenceHelper.getIsTouched())
                            );
                    preferenceHelper.putIsTouched(false);
                }
                else {
                    text = ("Details:\n\n" +
                            "Start Time: " + time.format("%H:%M:%S")+"\n"
                            + "UID:"+preferenceHelper.getCnic() + "\n"
                            + "Name:"+preferenceHelper.getName()+"\n"
                            +"Status:"+Boolean.toString(preferenceHelper.getIsTouched())
                    );
                    preferenceHelper.putIsTouched(true);
                }
            }
        });*/

        runOnUiThread(new Runnable(){
            @Override
            public void run(){

                tapinstruction.setVisibility(View.GONE);

                if(preferenceHelper.getIsTouched().equals("1")){
                    new SweetAlertDialog(NFCActivity.this, SweetAlertDialog.SUCCESS_TYPE )
                            .setTitleText("Congratulations!")
                            .setContentText("Your Trip Has Completed")
                            .show();
                    preferenceHelper.putIsTouched("0");
                }
                else if(preferenceHelper.getIsTouched().equals("0")){
                    new SweetAlertDialog(NFCActivity.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Congratulations!")
                            .setContentText("Your Trip Has Started")
                            .show();
                    preferenceHelper.putIsTouched("1");
                }
            }
        });

        text = "0"+","+preferenceHelper.getCnic()+","+vehicleType+","+preferenceHelper.getIsTouched() ;
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
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())||NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
            try {
                //processIntent(getIntent());
                Parcelable[] pArray = getIntent().getExtras().getParcelableArray("android.nfc.extra.NDEF_MESSAGES");
                Parcelable p = pArray[0];
                NdefMessage msg = (NdefMessage) p;
                Toast.makeText(NFCActivity.this, ""+new String(msg.getRecords()[0].getPayload()), Toast.LENGTH_SHORT).show();
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

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        textView = (TextView) findViewById(R.id.textView);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        textView.setText(new String(msg.getRecords()[0].getPayload()));
    }

    //------------------------------Vehivle Type Test----------------------------------------------
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String ResultHolder;


        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpServicesClass httpServiceObject = new HttpServicesClass(ServerURL);
            try
            {
                httpServiceObject.AddParam("cnic",preferenceHelper.getCnic());
                httpServiceObject.ExecutePostRequest();

                if(httpServiceObject.getResponseCode() == 200)
                {
                    ResultHolder = httpServiceObject.getResponse();

                    if(ResultHolder != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(ResultHolder);

                            JSONObject jsonObject;
                            jsonObject = jsonArray.getJSONObject(0);

                            vehicleType = jsonObject.getString("type");
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServiceObject.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            if (vehicleType !=null &&  !vehicleType.isEmpty()){
                Toast.makeText(getApplicationContext(), "Vehicle Type: "+vehicleType, Toast.LENGTH_SHORT).show();
            }
        }
    }

}