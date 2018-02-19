package info.androidhive.navigationdrawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.navigationdrawer.R;

public class TripDetailActivity extends AppCompatActivity {

    TextView tv_plaza, tv_startbooth, tv_endbooth, tv_starttime, tv_endtime, tv_fare, tv_tripid, tv_distance;
    Intent intent;
    String ServerURL = "http://tollapi.000webhostapp.com//tripDetails.php";
    ProgressBar progressBar;
    String description, location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        progressBar = new ProgressBar(this);
        tv_plaza = (TextView) findViewById(R.id.tv_plaza);
        tv_fare = (TextView) findViewById(R.id.tv_fare);
        tv_startbooth = (TextView) findViewById(R.id.tv_startbooth);
        tv_endbooth = (TextView) findViewById(R.id.tv_endbooth);
        tv_starttime = (TextView) findViewById(R.id.tv_starttime);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        tv_tripid = (TextView) findViewById(R.id.tv_tripid);
        tv_distance = (TextView) findViewById(R.id.tv_distance);

        new GetHttpResponse(TripDetailActivity.this).execute();

    }

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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpServicesClass httpServiceObject = new HttpServicesClass(ServerURL);
            try
            {
                httpServiceObject.AddParam("plazaId",intent.getStringExtra("plaza_id"));
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

                            description = jsonObject.getString("description");
                            location = jsonObject.getString("location");
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
            progressBar.setVisibility(View.GONE);

            if(!description.equals(null) || !location.equals(null))
            {
                tv_fare.setText(intent.getStringExtra("total_fare"));
                tv_plaza.setText(description+", "+location);
                tv_tripid.setText(intent.getStringExtra("trip_id"));
                tv_startbooth.setText(intent.getStringExtra("start_booth"));
                tv_endbooth.setText(intent.getStringExtra("end_booth"));
                tv_starttime.setText(intent.getStringExtra("start_time"));
                tv_endtime.setText(intent.getStringExtra("end_time"));
                tv_distance.setText(intent.getStringExtra("distance")+" KM");
            }

        }
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
