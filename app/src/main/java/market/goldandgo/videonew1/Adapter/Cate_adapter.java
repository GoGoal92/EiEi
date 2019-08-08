package market.goldandgo.videonew1.Adapter;

import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import android.widget.LinearLayout;
import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;

/**
 * Created by Go Goal on 2/28/2018.
 */

public class Cate_adapter extends BaseAdapter {

    FragmentActivity ac;
    ArrayList<get> list;
    LayoutInflater in;
    Animation expandIn;

    public Cate_adapter(FragmentActivity ac1, ArrayList<get> gets) {
        ac=ac1;
        list=gets;
        in= (LayoutInflater) ac.getSystemService(ac.LAYOUT_INFLATER_SERVICE);
        expandIn = AnimationUtils.loadAnimation(ac, R.anim.pop_in);


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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=in.inflate(R.layout.cate_row,null);
        LinearLayout tvani= (LinearLayout) v.findViewById(R.id.tvclick);
        Zawgyitextview tv= (Zawgyitextview) v.findViewById(R.id.tv);
        tv.setText(Html.fromHtml("<b>"+list.get(position).getTitle()+"</b>"));


        return v;
    }

    public void refresh(ArrayList<get> clist) {
        list=clist;
        notifyDataSetChanged();
    }
}
