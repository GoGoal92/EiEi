package market.goldandgo.videonew1.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.RoundImage;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Go Goal on 2/28/2018.
 */

public class Comment_adapter extends BaseAdapter {

    FragmentActivity ac;
    ArrayList<get> list;
    LayoutInflater in;
    Animation expandIn;

    public Comment_adapter(FragmentActivity ac1, ArrayList<get> gets) {
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

    static class ViewHolder {
        Zawgyitextview username,ctext;
        TextView ctime,cedit;
        ImageView usericon;

    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (v == null) {
            v=in.inflate(R.layout.comment_row,null);
            viewHolder = new ViewHolder();
            viewHolder.username= (Zawgyitextview) v.findViewById(R.id.cusername);
            viewHolder.ctext= (Zawgyitextview) v.findViewById(R.id.ctext);
            viewHolder.ctime= (TextView) v.findViewById(R.id.ctime);
            viewHolder.cedit= (TextView) v.findViewById(R.id.cedit);
            viewHolder.usericon= (ImageView) v.findViewById(R.id.usericon);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }


        viewHolder.username.setText(list.get(position).getCusername());
        viewHolder.ctext.setText(list.get(position).getCtext());
        viewHolder.ctime.setText(list.get(position).getCtime());

        if (list.get(position).getCmyment().equals("true")){
            viewHolder. cedit.setVisibility(View.VISIBLE);
        }else {
            viewHolder.cedit.setVisibility(View.GONE);
        }

     /*   if (list.get(position).getCuserfbid().equals("0")){
            viewHolder.usericon.setImageResource(R.drawable.appicon);
        }else{
            Picasso.with(ac)
                    .load(Constant.fbimg_url(list.get(position).getCuserfbid()))
                    .into(viewHolder.usericon);
        }*/


        return v;
    }

    public void refresh(ArrayList<get> clist) {
        list=clist;
        notifyDataSetChanged();
    }
}
