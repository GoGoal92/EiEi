package market.goldandgo.videonew1.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import at.blogc.android.views.ExpandableTextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Biged;
import market.goldandgo.videonew1.Detail_recycler;
import market.goldandgo.videonew1.Fragment.Fragment_movie;
import market.goldandgo.videonew1.MainActivity;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Object.header_object;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.HFRecyclerView;
import market.goldandgo.videonew1.Utils.Mydatabase;
import market.goldandgo.videonew1.Utils.recycler_viewItemClickListener;
import market.goldandgo.videonew1.fancydialoglib.Dialog_controller;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Go Goal on 6/29/2017.
 */

public class Detail_recycler_adapter extends HFRecyclerView<get> {


    ArrayList<get> list;
    AppCompatActivity ac;
    ArrayList<header_object> headerlist;
    Boolean nocomment_con, loadmore = false, showpg = false;
    String morebtn_text = "";
    Mydatabase mdb;

    public Detail_recycler_adapter(AppCompatActivity getactivity, ArrayList<get> asklist1, ArrayList<header_object> header_objects) {
        super(asklist1, true, true);
        list = asklist1;
        ac = getactivity;
        headerlist = header_objects;
        nocomment_con = true;
        mdb = new Mydatabase(ac);
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.comment_row, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.detail_recycler_header, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(inflater.inflate(R.layout.detail_recyler_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof HeaderViewHolder) {


            final HeaderViewHolder holder1 = (HeaderViewHolder) holder;

            if (Constant.Mrecshow()){

                AdView adView = new AdView(ac);
                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                adView.setAdUnitId(Constant.Mrect);
                holder1.admrect.addView(adView);
                AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.testdev)
                        .build();
                adView.loadAd(adRequest);
                adView.setAdListener(new AdListener() {

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        holder1.admrect.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        holder1.admrect.setVisibility(View.GONE);
                    }
                });

            }


            if (Constant.IsShowStartappads()) {
                SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(Constant.mopubRect);
                MoPub.initializeSdk(ac, configBuilder.build(), new SdkInitializationListener() {
                    @Override
                    public void onInitializationFinished() {
                        Log.e("onBannerLoaded", "onBannerLoaded");

                        holder1.moPubView.setAdUnitId(Constant.mopubRect); // Enter your Ad Unit ID from www.mopub.com
                        holder1.moPubView.loadAd();
                        holder1.moPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
                            @Override
                            public void onBannerLoaded(MoPubView banner) {
                                holder1.moPubView.setVisibility(View.VISIBLE);
                                Log.e("onBannerLoaded2", "onBannerLoaded2");
                            }

                            @Override
                            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                                holder1.moPubView.setVisibility(View.GONE);
                                Log.e("onBannerFailed", errorCode + "");
                            }

                            @Override
                            public void onBannerClicked(MoPubView banner) {
                                Log.e("onBannerClicked", "onBannerClicked");
                            }

                            @Override
                            public void onBannerExpanded(MoPubView banner) {
                                Log.e("onBannerExpanded", "onBannerExpanded");
                            }

                            @Override
                            public void onBannerCollapsed(MoPubView banner) {
                                Log.e("onBannerCollapsed", "onBannerCollapsed");
                            }
                        });
                    }
                });

            }


            final String fpath = Constant.datalocation_movie + Detail_recycler.getmid() + ".fmovie";
            final File myfile = new File(fpath);

            if (myfile.exists() && Constant.saveimage_setting.equals("v7")) {

                ac.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                        Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                        holder1.imageView.setBackground(myDrawable);
                    }
                });

            } else {

                Picasso.with(ac).load(Detail_recycler.getimageurl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder1.fakeiv, new Callback() {
                    @Override
                    public void onSuccess() {

                        holder1.imageView.setBackground(holder1.fakeiv.getDrawable());

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(ac)
                                .load(Detail_recycler.getimageurl())
                                .into(holder1.fakeiv, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                        holder1.imageView.setBackground(holder1.fakeiv.getDrawable());
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                });


            }


            holder1.show_v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder1.detail.isExpanded()) {
                        holder1.detail.collapse();
                        holder1.image_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        holder1.show_tv.setText("SHOW REVIEW");
                    } else {
                        holder1.detail.expand();
                        holder1.image_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
                        holder1.show_tv.setText("HIDE REVIEW");
                    }


                }
            });

            holder1.title.setText(Html.fromHtml("<b>" + Detail_recycler.getmname() + "</b>"));
            holder1.cate.setText(headerlist.get(0).getCate());
            holder1.detail.setText(headerlist.get(0).getDetailjson());

            holder1.commentlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constant.fbid.equals("0")) {
                        Detail_recycler.mustlogin();
                    } else {
                        Intent it = new Intent(ac, Biged.class);
                        it.putExtra("text", "0");
                        ac.startActivity(it);
                    }


                }
            });

            holder1.info1.setText(headerlist.get(0).getLike() + " Love | " + headerlist.get(0).getView() + " View | " + headerlist.get(0).getComment() + " Comment");

            if (headerlist.get(0).getUserlike().equals("true")) {
                holder1.loveiv.setBackgroundResource(R.drawable.ic_love);
            }

            holder1.loveiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (headerlist.get(0).getUserlike().equals("true")) {
                        Dialog_controller.cannotunlove(ac);

                    } else {
                        headerlist.get(0).setUserlike("true");
                        holder1.loveiv.setBackgroundResource(R.drawable.ic_love);
                        Detail_recycler.userlike();

                        int pluslike = Integer.parseInt(headerlist.get(0).getLike()) + 1;

                        holder1.info1.setText(pluslike + " Love | " + headerlist.get(0).getView() + " View | " + headerlist.get(0).getComment() + " Comment");
                    }
                }
            });

            if (mdb.rowexit(Detail_recycler.getmid())) {
                holder1.bookmarkiv.setBackgroundResource(R.drawable.ic_bookmark);
            } else {
                holder1.bookmarkiv.setBackgroundResource(R.drawable.ic_bookmark_un);
            }

            holder1.bookmarkiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mdb.rowexit(Detail_recycler.getmid())) {
                        mdb.deletebookmark(Detail_recycler.getmid());
                        holder1.bookmarkiv.setBackgroundResource(R.drawable.ic_bookmark_un);
                    } else {
                        mdb.insertbookmark(Detail_recycler.getmid(), Detail_recycler.getmname(), headerlist.get(0).getCate(), headerlist.get(0).getLike(), headerlist.get(0).getView(), headerlist.get(0).getComment(), Detail_recycler.getimageurl());
                        holder1.bookmarkiv.setBackgroundResource(R.drawable.ic_bookmark);
                    }


                }
            });

            holder1.watch_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Detail_recycler.Interreload("watch", headerlist.get(0).getHdsize(), headerlist.get(0).getSdsize(), headerlist.get(0).getHDlink(), headerlist.get(0).getSDlink());

                }
            });

            holder1.download_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constant.Isdmopen()) {


                        Detail_recycler.Interreload("download", headerlist.get(0).getHdsize(), headerlist.get(0).getSdsize(), headerlist.get(0).getHDlink(), headerlist.get(0).getSDlink());
                    }else{
                        Dialog_controller.OnlyforgoldUser(ac,"Only Gold User Can Use Download Manager");
                    }
                }
            });

            holder1.copy_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Detail_recycler.Interreload("copy", headerlist.get(0).getHdsize(), headerlist.get(0).getSdsize(), headerlist.get(0).getHDlink(), headerlist.get(0).getSDlink());

                }
            });

            holder1.shareiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Detail_recycler.screeshotImage();

                }
            });

            if (Constant.fbid.equals("1")) {
                holder1.usericon.setImageResource(R.drawable.appicon);

            } else if (!Constant.fbid.equals("0")) {
                Picasso.with(ac)
                        .load(Constant.fbimg_url(Constant.fbid))
                        .into(holder1.usericon);
            }

            if (Constant.GoldCon()) {
                holder1.border.setBackgroundResource(R.color.yello);
                holder1.gold.setVisibility(View.VISIBLE);
            }


        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            holder1.username.setText(list.get(i - 1).getCusername());
            holder1.ctext.setText(list.get(i - 1).getCtext());
            holder1.ctime.setText(list.get(i - 1).getCtime());

            if (list.get(i - 1).getCmyment().equals("true")) {
                holder1.cedit.setVisibility(View.VISIBLE);
            } else {
                holder1.cedit.setVisibility(View.GONE);
            }

            if (list.get(i - 1).getCuserfbid().equals("1")) {
                holder1.usericon.setImageResource(R.drawable.appicon);
                holder1.blueicon.setVisibility(View.VISIBLE);
            } else {
                Picasso.with(ac)
                        .load(Constant.fbimg_url(list.get(i - 1).getCuserfbid()))
                        .into(holder1.usericon);
                holder1.blueicon.setVisibility(View.GONE);
            }

            if (list.get(i - 1).getGold()) {
                holder1.border.setBackgroundResource(R.color.yello);
                holder1.gold.setVisibility(View.VISIBLE);
            } else {
                holder1.gold.setVisibility(View.GONE);
                holder1.border.setBackgroundResource(R.color.colorPrimary);
            }

            holder1.cedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(ac, Biged.class);
                    it.putExtra("text", list.get(i - 1).getCtext());
                    int pos = i - 1;
                    it.putExtra("position", pos + "");
                    ac.startActivity(it);
                }
            });


        } else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder holder1 = (FooterViewHolder) holder;
            if (nocomment_con) {
                holder1.nocomment.setVisibility(View.VISIBLE);
            } else {
                holder1.nocomment.setVisibility(View.GONE);
            }
            if (loadmore) {
                holder1.loadcomment.setVisibility(View.VISIBLE);
            } else {
                holder1.loadcomment.setVisibility(View.GONE);
            }

            if (showpg) {
                holder1.compg.setVisibility(View.VISIBLE);
            } else {
                holder1.compg.setVisibility(View.GONE);
            }
            ((FooterViewHolder) holder).loadcomment.setText(morebtn_text);

            holder1.loadcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder1.compg.setVisibility(View.VISIBLE);
                    holder1.loadcomment.setVisibility(View.INVISIBLE);
                    Detail_recycler.morecomment();

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    public void refresh(ArrayList<get> com_list, boolean b, String morebtn_text1) {
        nocomment_con = false;
        showpg = false;
        list = com_list;
        loadmore = b;
        morebtn_text = morebtn_text1;
        notifyDataSetChanged();
    }

    public void show_nocomment(boolean b) {
        nocomment_con = b;
        notifyDataSetChanged();
    }

    public void refreshheader(ArrayList<header_object> headerlist1) {
        headerlist = headerlist1;
        notifyDataSetChanged();
    }

    public void showpg(boolean b, ArrayList<get> com_list1, boolean x) {
        showpg = b;
        list = com_list1;
        loadmore = x;
        notifyDataSetChanged();
    }

    public void justrefresh() {
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public Zawgyitextview username, ctext;
        public TextView ctime, cedit;
        public ImageView usericon, blueicon;
        public RelativeLayout border;
        public ImageView gold;


        public ItemViewHolder(View v) {
            super(v);
            border = (RelativeLayout) v.findViewById(R.id.border);
            gold = (ImageView) v.findViewById(R.id.golduser);
            username = (Zawgyitextview) v.findViewById(R.id.cusername);
            ctext = (Zawgyitextview) v.findViewById(R.id.ctext);
            ctime = (TextView) v.findViewById(R.id.ctime);
            cedit = (TextView) v.findViewById(R.id.cedit);
            usericon = (ImageView) v.findViewById(R.id.usericon);
            blueicon = (ImageView) v.findViewById(R.id.blueicon);

        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public TextView nocomment;
        public Button loadcomment;
        public AVLoadingIndicatorView compg;

        public FooterViewHolder(View v) {
            super(v);
            nocomment = (TextView) v.findViewById(R.id.nocomment);
            loadcomment = (Button) v.findViewById(R.id.loadmorecomment);
            compg = (AVLoadingIndicatorView) v.findViewById(R.id.avicom);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageView, fakeiv, image_arrow, usericon;
        public ImageView loveiv, shareiv, bookmarkiv;
        public ExpandableTextView detail;
        public TextView show_tv, info1;
        public Zawgyitextview title, cate;
        public Button watch_btn, download_btn, copy_btn;
        public LinearLayout commentlayout, show_v;

        public RelativeLayout border;
        public ImageView gold;

        public RelativeLayout admrect;
        public MoPubView moPubView;

        public HeaderViewHolder(View v) {
            super(v);

            border = (RelativeLayout) v.findViewById(R.id.border);
            gold = (ImageView) v.findViewById(R.id.golduser);

            bookmarkiv = (ImageView) v.findViewById(R.id.bookmarkiv);
            shareiv = (ImageView) v.findViewById(R.id.shareiv);
            imageView = (ImageView) v.findViewById(R.id.iv);
            fakeiv = (ImageView) v.findViewById(R.id.iv_fake);
            image_arrow = (ImageView) v.findViewById(R.id.image_arrow);
            usericon = (ImageView) v.findViewById(R.id.usericon);
            loveiv = (ImageView) v.findViewById(R.id.loveiv);
            detail = (ExpandableTextView) v.findViewById(R.id.detail);
            Typeface font = Typeface.createFromAsset(ac.getAssets(), "zawgyi.ttf");
            detail.setTypeface(font);
            show_tv = (TextView) v.findViewById(R.id.show_tv);
            info1 = (TextView) v.findViewById(R.id.info1);
            cate = (Zawgyitextview) v.findViewById(R.id.cate);
            title = (Zawgyitextview) v.findViewById(R.id.title);
            watch_btn = (Button) v.findViewById(R.id.watch);
            download_btn = (Button) v.findViewById(R.id.download);
            copy_btn = (Button) v.findViewById(R.id.copylink);
            commentlayout = (LinearLayout) v.findViewById(R.id.commentlayout);
            show_v = (LinearLayout) v.findViewById(R.id.show_v);
            admrect = (RelativeLayout) v.findViewById(R.id.admrect);
            moPubView = (MoPubView) v.findViewById(R.id.adviewrect);
        }
    }

}
