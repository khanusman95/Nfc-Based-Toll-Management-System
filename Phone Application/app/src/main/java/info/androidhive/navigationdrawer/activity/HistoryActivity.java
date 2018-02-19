package info.androidhive.navigationdrawer.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.navigationdrawer.R;

/**
 * Created by Usman on 10/5/2017.
 */
public class HistoryActivity extends AppCompatActivity {


    RecyclerView rv;
    HistoryAdapter adapter;
    //ListView historyListView;
    ProgressBar progressBarSubject;
    String ServerURL = "http://tollapi.000webhostapp.com//userHistory.php";
    PreferenceHelper preferenceHelper;
    List<history> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferenceHelper = new PreferenceHelper(this);

        rv = (RecyclerView) findViewById(R.id.recycler_view);
        //historyListView = (ListView)findViewById(R.id.listview1);

        progressBarSubject = (ProgressBar)findViewById(R.id.progressBar);

        new GetHttpResponse(HistoryActivity.this).execute();
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

                            history history;

                            historyList = new ArrayList<history>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                history = new history();

                                jsonObject = jsonArray.getJSONObject(i);

                                history.distance = jsonObject.getString("Distance");
                                history.trip_id = jsonObject.getString("Trip_ID");
                                history.tripDate = jsonObject.getString("trip_date");
                                history.startTime = jsonObject.getString("start_time");
                                history.endTime = jsonObject.getString("end_time");
                                history.plazaId = jsonObject.getString("plaza_ID");
                                history.startBoothId = jsonObject.getString("start_booth_ID");
                                history.endBoothId = jsonObject.getString("end_booth_ID");
                                history.totalFare = jsonObject.getString("Total_Fare");


                               historyList.add(history);
                            }
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
            progressBarSubject.setVisibility(View.GONE);

            //historyListView.setVisibility(View.VISIBLE);


            if(historyList != null)
            {
                adapter = new HistoryAdapter(historyList,HistoryActivity.this);
                rv.setAdapter(adapter);
                LinearLayoutManager llm = new LinearLayoutManager(getApplication());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(llm);
                //historyListView.setAdapter(adapter);
                /*rv.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), rv,
                        new RecyclerItemListener.RecyclerTouchListener() {
                            public void onClickItem(View v, int position) {
                                System.out.println("single click");
                            }

                            public void onLongClickItem(View v, int position) {
                                System.out.println("On Long Click Item interface");
                            }
                        }));*/
                rv.addItemDecoration(new VerticalSpacingDecoration(64));
                rv.addItemDecoration(
                        new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.item_decorator)));
            }

        }
    }
}

