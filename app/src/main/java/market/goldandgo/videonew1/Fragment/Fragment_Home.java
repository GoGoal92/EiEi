package market.goldandgo.videonew1.Fragment;

/**
 * Created by Go Goal on 11/25/2016.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import market.goldandgo.videonew1.Detail_recycler;
import market.goldandgo.videonew1.Series_Detail_recycler;
import market.goldandgo.videonew1.Utils.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Adsadapter;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by Go Goal on 11/20/2016.
 */

public class Fragment_Home extends Fragment implements View.OnClickListener {


    public static Fragment_Home newInstance() {

        Bundle args = new Bundle();

        Fragment_Home fragment = new Fragment_Home();
        fragment.setArguments(args);
        return fragment;
    }

    static FragmentActivity ac;


    @Override
    public void onResume() {
        super.onResume();

        //  MainActivity.Pagerenable(false);
    }

    static NestedScrollView sv;
    static AVLoadingIndicatorView pg;
    static RelativeLayout network;
    static ArrayList<get> topratedlist, topserislist, recmovlist, recserieslist;
    static ArrayList<get> adlist = new ArrayList<>();

    static ImageView iv_recommdmovie[] = new ImageView[5];
    static ImageView fak_iv_recommdmovie[] = new ImageView[5];

    static Zawgyitextview tv_recommdmovie[] = new Zawgyitextview[5];


    static ImageView iv_topmovie[] = new ImageView[5];
    static Zawgyitextview tv_topmovie[] = new Zawgyitextview[5];
    static ImageView fak_iv_topmovie[] = new ImageView[5];

    static ImageView iv_topseries[] = new ImageView[5];
    static ImageView fak_iv_topseries[] = new ImageView[5];
    static Zawgyitextview tv_topseries[] = new Zawgyitextview[5];

    static ImageView iv_recseries[] = new ImageView[5];
    static Zawgyitextview tv_recseries[] = new Zawgyitextview[5];
    static ImageView fak_iv_recseries[] = new ImageView[5];


    static LinearLayout ly_recommdmovie[] = new LinearLayout[5];
    static LinearLayout ly_topmovie[] = new LinearLayout[5];

    static LinearLayout ly_topseries[] = new LinearLayout[5];
    static LinearLayout ly_recseries[] = new LinearLayout[5];


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = getActivity();
        MyRequest.Mainpage();
        adapter = new Adsadapter(ac, new ArrayList<get>());

        File moviepath = new File(Constant.datalocation_movie);
        if (!moviepath.exists()) {
            moviepath.mkdir();
        }

        File scoverpath = new File(Constant.datalocation_scover);
        if (!scoverpath.exists()) {
            scoverpath.mkdir();
        }

        File adspath = new File(Constant.datalocation_ads);
        if (!adspath.exists()) {
            adspath.mkdir();
        }


    }

    public AutoScrollViewPager vpager;
    public CircleIndicator indicator;
    static Adsadapter adapter;


    static RelativeLayout buylaout;
    static TextView priceinfo;
    static Button buy, cancel, okay;
    static SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshlayoutmain);
        swipeLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyRequest.Mainpage();
            }
        });

        ly_recommdmovie[0] = (LinearLayout) v.findViewById(R.id.ly_recmovie1);
        ly_recommdmovie[1] = (LinearLayout) v.findViewById(R.id.ly_recmovie2);
        ly_recommdmovie[2] = (LinearLayout) v.findViewById(R.id.ly_recmovie3);
        ly_recommdmovie[3] = (LinearLayout) v.findViewById(R.id.ly_recmovie4);
        ly_recommdmovie[4] = (LinearLayout) v.findViewById(R.id.ly_recmovie5);

        ly_topmovie[0] = (LinearLayout) v.findViewById(R.id.ly_topmovie1);
        ly_topmovie[1] = (LinearLayout) v.findViewById(R.id.ly_topmovie2);
        ly_topmovie[2] = (LinearLayout) v.findViewById(R.id.ly_topmovie3);
        ly_topmovie[3] = (LinearLayout) v.findViewById(R.id.ly_topmovie4);
        ly_topmovie[4] = (LinearLayout) v.findViewById(R.id.ly_topmovie5);

        ly_topseries[0] = (LinearLayout) v.findViewById(R.id.ly_topseries1);
        ly_topseries[1] = (LinearLayout) v.findViewById(R.id.ly_topseries2);
        ly_topseries[2] = (LinearLayout) v.findViewById(R.id.ly_topseries3);
        ly_topseries[3] = (LinearLayout) v.findViewById(R.id.ly_topseries4);
        ly_topseries[4] = (LinearLayout) v.findViewById(R.id.ly_topseries5);

        ly_recseries[0] = (LinearLayout) v.findViewById(R.id.ly_recseries1);
        ly_recseries[1] = (LinearLayout) v.findViewById(R.id.ly_recseries2);
        ly_recseries[2] = (LinearLayout) v.findViewById(R.id.ly_recseries3);
        ly_recseries[3] = (LinearLayout) v.findViewById(R.id.ly_recseries4);
        ly_recseries[4] = (LinearLayout) v.findViewById(R.id.ly_recseries5);

        for (int i = 0; i < 5; i++) {
            ly_recommdmovie[i].setOnClickListener(this);
            ly_topmovie[i].setOnClickListener(this);
            ly_topseries[i].setOnClickListener(this);
            ly_recseries[i].setOnClickListener(this);

        }


        iv_recommdmovie[0] = (ImageView) v.findViewById(R.id.iv_recmovie1);
        iv_recommdmovie[1] = (ImageView) v.findViewById(R.id.iv_recmovie2);
        iv_recommdmovie[2] = (ImageView) v.findViewById(R.id.iv_recmovie3);
        iv_recommdmovie[3] = (ImageView) v.findViewById(R.id.iv_recmovie4);
        iv_recommdmovie[4] = (ImageView) v.findViewById(R.id.iv_recmovie5);

        fak_iv_recommdmovie[0] = (ImageView) v.findViewById(R.id.fak_iv_recmovie1);
        fak_iv_recommdmovie[1] = (ImageView) v.findViewById(R.id.fak_iv_recmovie2);
        fak_iv_recommdmovie[2] = (ImageView) v.findViewById(R.id.fak_iv_recmovie3);
        fak_iv_recommdmovie[3] = (ImageView) v.findViewById(R.id.fak_iv_recmovie4);
        fak_iv_recommdmovie[4] = (ImageView) v.findViewById(R.id.fak_iv_recmovie5);


        tv_recommdmovie[0] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie1);
        tv_recommdmovie[1] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie2);
        tv_recommdmovie[2] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie3);
        tv_recommdmovie[3] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie4);
        tv_recommdmovie[4] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie5);

        iv_topmovie[0] = (ImageView) v.findViewById(R.id.iv_topmovie1);
        iv_topmovie[1] = (ImageView) v.findViewById(R.id.iv_topmovie2);
        iv_topmovie[2] = (ImageView) v.findViewById(R.id.iv_topmovie3);
        iv_topmovie[3] = (ImageView) v.findViewById(R.id.iv_topmovie4);
        iv_topmovie[4] = (ImageView) v.findViewById(R.id.iv_topmovie5);

        fak_iv_topmovie[0] = (ImageView) v.findViewById(R.id.fak_iv_topmovie1);
        fak_iv_topmovie[1] = (ImageView) v.findViewById(R.id.fak_iv_topmovie2);
        fak_iv_topmovie[2] = (ImageView) v.findViewById(R.id.fak_iv_topmovie3);
        fak_iv_topmovie[3] = (ImageView) v.findViewById(R.id.fak_iv_topmovie4);
        fak_iv_topmovie[4] = (ImageView) v.findViewById(R.id.fak_iv_topmovie5);


        tv_topmovie[0] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie1);
        tv_topmovie[1] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie2);
        tv_topmovie[2] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie3);
        tv_topmovie[3] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie4);
        tv_topmovie[4] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie5);

        iv_topseries[0] = (ImageView) v.findViewById(R.id.iv_topseries1);
        iv_topseries[1] = (ImageView) v.findViewById(R.id.iv_topseries2);
        iv_topseries[2] = (ImageView) v.findViewById(R.id.iv_topseries3);
        iv_topseries[3] = (ImageView) v.findViewById(R.id.iv_topseries4);
        iv_topseries[4] = (ImageView) v.findViewById(R.id.iv_topseries5);


        fak_iv_topseries[0] = (ImageView) v.findViewById(R.id.fak_iv_topseries1);
        fak_iv_topseries[1] = (ImageView) v.findViewById(R.id.fak_iv_topseries2);
        fak_iv_topseries[2] = (ImageView) v.findViewById(R.id.fak_iv_topseries3);
        fak_iv_topseries[3] = (ImageView) v.findViewById(R.id.fak_iv_topseries4);
        fak_iv_topseries[4] = (ImageView) v.findViewById(R.id.fak_iv_topseries5);


        tv_topseries[0] = (Zawgyitextview) v.findViewById(R.id.tv_topseries1);
        tv_topseries[1] = (Zawgyitextview) v.findViewById(R.id.tv_topseries2);
        tv_topseries[2] = (Zawgyitextview) v.findViewById(R.id.tv_topseries3);
        tv_topseries[3] = (Zawgyitextview) v.findViewById(R.id.tv_topseries4);
        tv_topseries[4] = (Zawgyitextview) v.findViewById(R.id.tv_topseries5);


        iv_recseries[0] = (ImageView) v.findViewById(R.id.iv_recseries1);
        iv_recseries[1] = (ImageView) v.findViewById(R.id.iv_recseries2);
        iv_recseries[2] = (ImageView) v.findViewById(R.id.iv_recseries3);
        iv_recseries[3] = (ImageView) v.findViewById(R.id.iv_recseries4);
        iv_recseries[4] = (ImageView) v.findViewById(R.id.iv_recseries5);

        fak_iv_recseries[0] = (ImageView) v.findViewById(R.id.fak_iv_recseries1);
        fak_iv_recseries[1] = (ImageView) v.findViewById(R.id.fak_iv_recseries2);
        fak_iv_recseries[2] = (ImageView) v.findViewById(R.id.fak_iv_recseries3);
        fak_iv_recseries[3] = (ImageView) v.findViewById(R.id.fak_iv_recseries4);
        fak_iv_recseries[4] = (ImageView) v.findViewById(R.id.fak_iv_recseries5);


        tv_recseries[0] = (Zawgyitextview) v.findViewById(R.id.tv_recseries1);
        tv_recseries[1] = (Zawgyitextview) v.findViewById(R.id.tv_recseries2);
        tv_recseries[2] = (Zawgyitextview) v.findViewById(R.id.tv_recseries3);
        tv_recseries[3] = (Zawgyitextview) v.findViewById(R.id.tv_recseries4);
        tv_recseries[4] = (Zawgyitextview) v.findViewById(R.id.tv_recseries5);


        vpager = (AutoScrollViewPager) v.findViewById(R.id.viewpagermain);
        indicator = (CircleIndicator) v.findViewById(R.id.indicatormain);
        adapter = new Adsadapter(ac, new ArrayList<get>());
        vpager.setAdapter(adapter);
        vpager.setCurrentItem(180);
        indicator.setViewPager(vpager);
        vpager.startAutoScroll(4000);


        sv = (NestedScrollView) v.findViewById(R.id.sv);
        pg = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        network = (RelativeLayout) v.findViewById(R.id.networkerro);
        pg.show();

        Button reload = (Button) v.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequest.Mainpage();
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
            }
        });


        return v;
    }


    public static void Feedback(String s) {

        ClearIMage();

        swipeLayout.setRefreshing(false);
        sv.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.GONE);
        pg.hide();

        adlist = new ArrayList<>();
        adlist = Jsonparser.getadlist(s);
        adapter.refresh(adlist);

        topserislist = new ArrayList<>();
        topserislist = Jsonparser.getseriesrated(s, "topseries");

        recserieslist = new ArrayList<>();
        recserieslist = Jsonparser.getseriesrated(s, "recseries");

        recmovlist = new ArrayList<>();
        recmovlist = Jsonparser.getMovierated(s, "recmovie");

        topratedlist = new ArrayList<>();
        topratedlist = Jsonparser.getMovierated(s, "top");

        for (int i = 0; i < 5; i++) {
            tv_recommdmovie[i].setText(recmovlist.get(i).getTitle());
            tv_topmovie[i].setText(topratedlist.get(i).getTitle());
            tv_topseries[i].setText(topserislist.get(i).getTitle());
            tv_recseries[i].setText(recserieslist.get(i).getTitle());

            Imagefill1(i);
            Imagefill2( i);
            Imagefill3( i);
            Imagefill4(i);

        }


    }



    private static void Imagefill1( int i) {
        String loca =Constant.datalocation_movie;

        final int k = i;


        final String fpath = loca + recmovlist.get(k).getMid() + ".fmovie";
        final File ff = new File(fpath);
        if (ff.exists() && Constant.saveimage_setting.equals("v7")) {

            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                    Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                    iv_recommdmovie[k].setBackground(myDrawable);
                }
            });

        } else {

            Picasso.with(ac).load(recmovlist.get(k).getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(fak_iv_recommdmovie[k], new Callback() {
                @Override
                public void onSuccess() {

                    iv_recommdmovie[k].setBackground(fak_iv_recommdmovie[k].getDrawable());

                }

                @Override
                public void onError() {
                    // Try again online if cache failed
                    Picasso.with(ac)
                            .load(recmovlist.get(k).getImage())
                            .into(fak_iv_recommdmovie[k], new Callback() {
                                @Override
                                public void onSuccess() {

                                    iv_recommdmovie[k].setBackground(fak_iv_recommdmovie[k].getDrawable());
                                    if (!ff.exists()) {
                                        Constant.Savetosdcard(ac, recmovlist.get(k).getImage(), recmovlist.get(k).getMid(), "M");
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
            });

        }


    }

    private static void Imagefill2( int i) {



        String loca =  Constant.datalocation_movie;


        final int k = i;
        final String fpath = loca + topratedlist.get(k).getMid() + ".fmovie";
        final File ff = new File(fpath);
        if (ff.exists() && Constant.saveimage_setting.equals("v7")) {

            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                    Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                    iv_topmovie[k].setBackground(myDrawable);
                }
            });

        } else {

            Picasso.with(ac).load(topratedlist.get(k).getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(fak_iv_topmovie[k], new Callback() {
                @Override
                public void onSuccess() {

                    iv_topmovie[k].setBackground(fak_iv_topmovie[k].getDrawable());

                }

                @Override
                public void onError() {
                    // Try again online if cache failed
                    Picasso.with(ac)
                            .load(topratedlist.get(k).getImage())
                            .into(fak_iv_topmovie[k], new Callback() {
                                @Override
                                public void onSuccess() {

                                    iv_topmovie[k].setBackground(fak_iv_topmovie[k].getDrawable());
                                    if (!ff.exists()) {
                                        Constant.Savetosdcard(ac, topratedlist.get(k).getImage(), topratedlist.get(k).getMid(), "M");
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
            });

        }


    }

    private static void Imagefill3(int i) {



        String loca =  Constant.datalocation_scover;


        final int k = i;
        final String fpath = loca + recserieslist.get(k).getMid() + ".fmovie";
        final File ff = new File(fpath);
        if (ff.exists() && Constant.saveimage_setting.equals("v7")) {

            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                    Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                    iv_recseries[k].setBackground(myDrawable);
                }
            });

        } else {

            Picasso.with(ac).load(recserieslist.get(k).getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(fak_iv_recseries[k], new Callback() {
                @Override
                public void onSuccess() {

                    iv_recseries[k].setBackground(fak_iv_recseries[k].getDrawable());

                }

                @Override
                public void onError() {
                    // Try again online if cache failed
                    Picasso.with(ac)
                            .load(recserieslist.get(k).getImage())
                            .into(fak_iv_recseries[k], new Callback() {
                                @Override
                                public void onSuccess() {

                                    iv_recseries[k].setBackground(fak_iv_recseries[k].getDrawable());
                                    if (!ff.exists()) {
                                        Constant.Savetosdcard(ac, recserieslist.get(k).getImage(), recserieslist.get(k).getMid(), "s");
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
            });

        }


    }

    private static void Imagefill4(int i) {



        String loca =  Constant.datalocation_scover;


        final int k = i;
        final String fpath = loca + topserislist.get(k).getMid() + ".fmovie";
        final File ff = new File(fpath);
        if (ff.exists() && Constant.saveimage_setting.equals("v7")) {

            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                    Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                    iv_topseries[k].setBackground(myDrawable);
                }
            });

        } else {

            Picasso.with(ac).load(topserislist.get(k).getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(fak_iv_topseries[k], new Callback() {
                @Override
                public void onSuccess() {

                    iv_topseries[k].setBackground(fak_iv_topseries[k].getDrawable());

                }

                @Override
                public void onError() {
                    // Try again online if cache failed
                    Picasso.with(ac)
                            .load(topserislist.get(k).getImage())
                            .into(fak_iv_topseries[k], new Callback() {
                                @Override
                                public void onSuccess() {

                                    iv_topseries[k].setBackground(fak_iv_topseries[k].getDrawable());
                                    if (!ff.exists()) {
                                        Constant.Savetosdcard(ac, topserislist.get(k).getImage(), topserislist.get(k).getMid(), "s");
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
            });

        }


    }


    @SuppressLint("ResourceAsColor")
    private static void ClearIMage() {
        for (int i = 0; i < 5; i++) {
            iv_recseries[i].setBackgroundColor(R.color.tethintcolor);
            iv_topmovie[i].setBackgroundColor(R.color.tethintcolor);
            iv_topseries[i].setBackgroundColor(R.color.tethintcolor);
            iv_recommdmovie[i].setBackgroundColor(R.color.tethintcolor);

        }

    }


    @Override
    public void onClick(View v) {

        if (v == ly_recommdmovie[0]) {
            Moiveclick(0, "rec");
        }
        if (v == ly_recommdmovie[1]) {
            Moiveclick(1, "rec");
        }
        if (v == ly_recommdmovie[2]) {
            Moiveclick(2, "rec");
        }
        if (v == ly_recommdmovie[3]) {
            Moiveclick(3, "rec");
        }
        if (v == ly_recommdmovie[4]) {
            Moiveclick(4, "rec");
        }

        if (v == ly_topmovie[0]) {
            Moiveclick(0, "top");
        }
        if (v == ly_topmovie[1]) {
            Moiveclick(1, "top");
        }
        if (v == ly_topmovie[2]) {
            Moiveclick(2, "top");
        }
        if (v == ly_topmovie[3]) {
            Moiveclick(3, "top");
        }
        if (v == ly_topmovie[4]) {
            Moiveclick(4, "top");
        }

        if (v == ly_topseries[0]) {
            Seriesclick(0, "top");
        }
        if (v == ly_topseries[1]) {
            Seriesclick(1, "top");
        }
        if (v == ly_topseries[2]) {
            Seriesclick(2, "top");
        }
        if (v == ly_topseries[3]) {
            Seriesclick(3, "top");
        }
        if (v == ly_topseries[4]) {
            Seriesclick(4, "top");
        }

        if (v == ly_recseries[0]) {
            Seriesclick(0, "rec");
        }
        if (v == ly_recseries[1]) {
            Seriesclick(1, "rec");
        }
        if (v == ly_recseries[2]) {
            Seriesclick(2, "rec");
        }
        if (v == ly_recseries[3]) {
            Seriesclick(3, "rec");
        }
        if (v == ly_recseries[4]) {
            Seriesclick(4, "rec");
        }

    }

    private void Seriesclick(int pos, String rec) {
        ArrayList<get> clicklist = new ArrayList<>();
        ActivityOptionsCompat options = null;

        if (rec.equals("rec")) {
            clicklist = recserieslist;
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(ac, iv_recseries[pos], "moviecover");
        }
        if (rec.equals("top")) {
            clicklist = topserislist;
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(ac, iv_topseries[pos], "moviecover");
        }

        String removie_s= clicklist.get(pos).getMid().replace("s","");

        Intent intent = new Intent(ac, Series_Detail_recycler.class);
        intent.putExtra("name", clicklist.get(pos).getTitle());
        intent.putExtra(EXTRA_ANIMAL_ITEM, clicklist.get(pos).getImage());
        intent.putExtra("mid",removie_s);
        intent.putExtra("cate", clicklist.get(pos).getCategory());
        intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, "moviecover");
        startActivity(intent, options.toBundle());
    }

    public static final String EXTRA_ANIMAL_ITEM = "animal_image_url";
    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "moviecover";

    private void Moiveclick(int i, String rec) {
        ArrayList<get> clicklist = new ArrayList<>();
        ActivityOptionsCompat options = null;
        if (rec.equals("rec")) {
            clicklist = recmovlist;
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(ac, iv_recommdmovie[i], "moviecover");
        }
        if (rec.equals("top")) {
            clicklist = topratedlist;
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(ac, iv_topmovie[i], "moviecover");
        }
        Intent intent = new Intent(ac, Detail_recycler.class);
        intent.putExtra("name", clicklist.get(i).getTitle());
        intent.putExtra(EXTRA_ANIMAL_ITEM, clicklist.get(i).getImage());
        intent.putExtra("mid", clicklist.get(i).getMid());
        intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, "moviecover");


        startActivity(intent, options.toBundle());

    /*    Intent it = new Intent(ac, Detail.class);
        it.putExtra("mid", clicklist.get(i).getMid());
        it.putExtra("type", "movie");
        it.putExtra("mname", clicklist.get(i).getTitle());
        it.putExtra("image", clicklist.get(i).getImage());
        ac.startActivity(it);*/

    }


    private static void Imagecondition_RM() {
        for (int i = 0; i < recmovlist.size(); i++) {
            Picasso.with(ac).load(Constant.host + "v8/picsmall.php?size=m&filename=" + recmovlist.get(i).getImage()).into(iv_recommdmovie[i]);
            Picasso.with(ac).load(Constant.host + "v8/picsmall.php?size=m&filename=" + recmovlist.get(i).getImage()).into(iv_recommdmovie[i]);
            Picasso.with(ac).load(Constant.host + "v8/picsmall.php?size=m&filename=" + recmovlist.get(i).getImage()).into(iv_recommdmovie[i]);
            Picasso.with(ac).load(Constant.host + "v8/picsmall.php?size=m&filename=" + recmovlist.get(i).getImage()).into(iv_recommdmovie[i]);
        }
    }


    public static void Feedback_Error() {
        swipeLayout.setRefreshing(false);
        sv.setVisibility(View.GONE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.VISIBLE);
        pg.hide();

    }
}
