package info.androidhive.navigationdrawer.activity;

import android.Manifest;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;

public class GPSActivity extends Activity {

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

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

        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(GPSActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    PreferenceManager.getDefaultSharedPreferences(GPSActivity.this).edit().putFloat("startLatitude", (float)latitude).commit();
                    PreferenceManager.getDefaultSharedPreferences(GPSActivity.this).edit().putFloat("startLongitude", (float)longitude).commit();

                    /* To Retrieve shared Preferences Values
                    PreferenceManager.getDefaultSharedPreferences(context).getString("Latitude", "No Latitude Value Stored");
                    PreferenceManager.getDefaultSharedPreferences(context).getString("Longitude", "No Longitude Value Stored");*/

                    /*Find Distance Covered By User

                    Location startPoint=new Location("locationA");
                    startPoint.setLatitude(17.372102);
                    startPoint.setLongitude(78.484196);

                    Location endPoint=new Location("locationA");
                    endPoint.setLatitude(17.375775);
                    endPoint.setLongitude(78.469218);

                    double distance=startPoint.distanceTo(endPoint);*/

                    String completeAddress = getCompleteAddressString(latitude,longitude);

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    Toast.makeText(GPSActivity.this, completeAddress, Toast.LENGTH_SHORT).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strAdd;
    }
}
