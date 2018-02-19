package info.androidhive.navigationdrawer.activity;

/**
 * Created by Usman on 10/5/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import info.androidhive.navigationdrawer.R;

public class ListAdapterClass extends BaseAdapter {

    Context context;
    List<history> valueList;

    public ListAdapterClass(List<history> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
    }

    @Override
    public int getCount()
    {
        return this.valueList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.valueList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.layout_items, null);

            viewItem.TextViewSubjectName = (TextView)convertView.findViewById(R.id.textView1);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.TextViewSubjectName.setText("Trip Date:" + valueList.get(position).tripDate + "  " + "Fare:" + "Rs" + valueList.get(position).totalFare + "  " +"Time:"+ valueList.get(position).startTime + "  " +"Plaza ID:" +valueList.get(position).plazaId);

        return convertView;
    }
}

class ViewItem
{
    TextView TextViewSubjectName;

}
