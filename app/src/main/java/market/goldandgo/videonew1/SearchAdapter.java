package market.goldandgo.videonew1;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.HFRecyclerView;
import market.goldandgo.videonew1.Utils.searchItemClickListener;

class SearchAdapter extends HFRecyclerView<get> {

    static ArrayList<get> mylist;
    AppCompatActivity ac;

    searchItemClickListener ItemClickListener;


    public SearchAdapter(AppCompatActivity getactivity, ArrayList<get> asklist1, searchItemClickListener ItemClickListener1) {
        super(asklist1, false, false);
        mylist = asklist1;
        ac = getactivity;
        this.ItemClickListener = ItemClickListener1;

    }


    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.search_row, parent, false));
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).title.setText(Html.fromHtml("<b>" + mylist.get(position).getTitle() + "</b>"));
            ((ItemViewHolder) holder).cate.setText(Html.fromHtml("<b>" + mylist.get(position).getCategory() + "</b>"));
            ((ItemViewHolder) holder).type.setText(Html.fromHtml("<b>" + mylist.get(position).getType() + "</b>"));



            try {
                final String image = mylist.get(position).getImage();
                Picasso.with(ac).load(Constant.host + "v8/picsmall.php?size=s&filename=" + image)
                        .networkPolicy(NetworkPolicy.OFFLINE).into(((ItemViewHolder) holder).movieimage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(ac).load(Constant.host + "v8/picsmall.php?size=s&filename=" + image).networkPolicy(NetworkPolicy.OFFLINE).into(((ItemViewHolder) holder).movieimage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                // Try again online if cache failed
                                Picasso.with(ac)
                                        .load(Constant.host + "v8/picsmall.php?size=s&filename=" + image)
                                        .into(((ItemViewHolder) holder).movieimage, new Callback() {
                                            @Override
                                            public void onSuccess() {


                                            }

                                            @Override
                                            public void onError() {
                                            }
                                        });
                            }
                        });
                    }
                });
            } catch (Exception e) {

            }





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemClickListener.onRVlItemClick(holder.getAdapterPosition(), mylist.get(position).getImage(), ((ItemViewHolder) holder).movieimage);
            }
        });

    }
}

    public void refresh(ArrayList<get> list) {

        mylist = list;
        notifyDataSetChanged();
    }


public class ItemViewHolder extends RecyclerView.ViewHolder {

    public Zawgyitextview title, cate, type;
    public ImageView movieimage;


    public ItemViewHolder(View v) {
        super(v);

        title = (Zawgyitextview) v.findViewById(R.id.title);
        cate = (Zawgyitextview) v.findViewById(R.id.cate);
        type = (Zawgyitextview) v.findViewById(R.id.type);
        movieimage = (ImageView) v.findViewById(R.id.cover);


    }
}
}
