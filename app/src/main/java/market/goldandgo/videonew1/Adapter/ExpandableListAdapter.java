package market.goldandgo.videonew1.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Series_Detail_recycler;

public class ExpandableListAdapter extends BaseAdapter {

    private AppCompatActivity ac;
    ArrayList<get> list;


    public ExpandableListAdapter(AppCompatActivity context,ArrayList<get> list1) {
        this.ac = context;
        this.list = list1;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_child, null);
        }

        final RelativeLayout eprelative= (RelativeLayout) convertView.findViewById(R.id.eprelative);
        eprelative.setBackgroundResource(list.get(position).getBaclgrouund());
        TextView episode = (TextView) convertView.findViewById(R.id.episode);
        episode.setText(list.get(position).getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eprelative.setBackgroundResource(R.drawable.rectangle_transparent_selected);


                SharedPreferences prefs = ac.getSharedPreferences("usercurrent",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Series_Detail_recycler.getmid(), list.get(position).getMid());
                editor.commit();

                for (int j = 0; j < list.size(); j++) {
                    if (j == position) {
                        list.get(j).setBaclgrouund(R.drawable.rectangle_transparent_selected);
                    } else {
                        list.get(j).setBaclgrouund(R.drawable.rectangle_transparent);
                    }

                }
                Series_Detail_recycler.showwatch(list.get(position).getMid(),list.get(position).getTitle());

                notifyDataSetChanged();
            }
        });

        return  convertView;
    }
}