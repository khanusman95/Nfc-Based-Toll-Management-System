package info.androidhive.navigationdrawer.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.androidhive.navigationdrawer.R;

/**
 * Created by Usman on 10/30/2017.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<history> historyList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView start_time;
        public TextView end_time;
        public TextView trip_date;
        public TextView start_booth_id;
        public TextView end_booth_id;
        public TextView plaza_id;
        public TextView total_fare;

        public MyViewHolder(View view) {
            super(view);
            start_time = (TextView) view.findViewById(R.id.start_time);
            end_time = (TextView) view.findViewById(R.id.end_time);
            trip_date = (TextView) view.findViewById(R.id.trip_date);

            total_fare = (TextView) view.findViewById(R.id.total_fare);
        }
    }
    public HistoryAdapter(List<history> historyList, Context context) {

        this.historyList = historyList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final history c = historyList.get(position);
        holder.start_time.setText(c.startTime);
        holder.end_time.setText(c.endTime);
        holder.trip_date.setText(c.tripDate);

        holder.total_fare.setText(c.totalFare);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TripDetailActivity.class);
                intent.putExtra("trip_id",c.trip_id);
                intent.putExtra("distance", c.distance);
                intent.putExtra("trip_date",c.tripDate);
                intent.putExtra("total_fare",c.totalFare);
                intent.putExtra("start_time",c.startTime);
                intent.putExtra("end_time",c.endTime);
                intent.putExtra("start_booth",c.startBoothId);
                intent.putExtra("end_booth",c.endBoothId);
                intent.putExtra("plaza_id",c.plazaId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row,parent, false);
        return new MyViewHolder(v);
    }

}
