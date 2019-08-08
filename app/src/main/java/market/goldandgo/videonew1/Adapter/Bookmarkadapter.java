package market.goldandgo.videonew1.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Bookmark;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.HFRecyclerView;
import market.goldandgo.videonew1.Utils.bookmarkItemClickListener;
import market.goldandgo.videonew1.Utils.recycler_viewItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bookmarkadapter extends HFRecyclerView<get> {

    static ArrayList<get> list;
    AppCompatActivity ac;
    bookmarkItemClickListener ItemClickListener;

    public Bookmarkadapter(AppCompatActivity getactivity, ArrayList<get> asklist1, bookmarkItemClickListener ItemClickListener1) {
        super(asklist1, false, false);
        list = asklist1;
        ac = getactivity;
        this.ItemClickListener = ItemClickListener1;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.video_detail_theme, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ItemViewHolder) {

            final ItemViewHolder holder1 = (ItemViewHolder) holder;
            holder1.title.setText(list.get(i).getTitle());
            holder1.cate.setText(list.get(i).getCategory());
            holder1.like.setText(list.get(i).getLike() + " Love");
            holder1.view.setText(list.get(i).getView() + " View");
            holder1.comment.setText(list.get(i).getCommentcount() + " Talking");

            String path="";
            if (list.get(i).getMid().contains("s")){
                path=Constant.datalocation_scover;
            }else{
                path=Constant.datalocation_movie;
            }


            final String fpath =  path+ list.get(i).getMid() + ".fmovie";
            final File ff = new File(fpath);
            if (ff.exists() && Constant.saveimage_setting.equals("v7")) {

                ac.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                        Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                        holder1.iv.setBackground(myDrawable);
                    }
                });

            }else{

                Picasso.with(ac).load(list.get(i).getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder1.fakeiv, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder1.iv.setBackground(holder1.fakeiv.getDrawable());
                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(ac)
                                .load(list.get(i).getImage())
                                .into(holder1.fakeiv, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                        holder1.iv.setBackground(holder1.fakeiv.getDrawable());
                                        if (!ff.exists()){
                                            Constant.Savetosdcard(ac,list.get(i).getImage(),list.get(i).getMid(),"M");
                                        }

                                    }

                                    @Override
                                    public void onError() {
                                    }
                                });
                    }
                });

            }






            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemClickListener.onRVlItemClick(holder1.getAdapterPosition(), list.get(i).getImage(), holder1.iv);
                }
            });

        }
    }

    public void refresh(ArrayList<get> list1) {
        list=list1;
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        Zawgyitextview title, cate;
        ImageView iv, fakeiv;
        TextView like, view, comment;
        CardView click;

        public ItemViewHolder(View v) {
            super(v);
            click = (CardView) v.findViewById(R.id.rv_click);
            title = (Zawgyitextview) v.findViewById(R.id.title);
            cate = (Zawgyitextview) v.findViewById(R.id.cate);
            like = (TextView) v.findViewById(R.id.like);
            view = (TextView) v.findViewById(R.id.view);
            comment = (TextView) v.findViewById(R.id.comment);
            iv = (ImageView) v.findViewById(R.id.iv);
            fakeiv = (ImageView) v.findViewById(R.id.fake_iv);

        }
    }
}
