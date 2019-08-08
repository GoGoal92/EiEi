package market.goldandgo.videonew1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;

import com.google.android.exoplayer2.C;
import com.google.android.gms.ads.*;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Series_Detail_recycler_adapter;
import market.goldandgo.videonew1.Fragment.Fragment_movie;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Object.header_object;
import market.goldandgo.videonew1.Utils.Encode_Decoder;
import market.goldandgo.videonew1.Utils.Mydatabase_down;
import market.goldandgo.videonew1.fancydialoglib.Dialog_controller;

import java.io.File;
import java.util.ArrayList;

import static market.goldandgo.videonew1.Detail.moviename;

public class Series_Detail_recycler extends AppCompatActivity {

    static RecyclerView rv;
    LinearLayoutManager llm;
    static LinearLayout fakelayout;
    static AppCompatActivity ac;
    static Series_Detail_recycler_adapter adapter;
    static ImageView detailback;
    static AVLoadingIndicatorView pg;
    static String imgurl, mid;
    ImageView imageView, fakeiv;
    Zawgyitextview title;
    static RelativeLayout ss, temrss;

    static ImageView tempss;

    public static String getimageurl() {
        return imgurl;
    }

    public static String getmid() {
        return mid;
    }

    static SharedPreferences prefs;

    private void Setbanner_Ads() {
        if (Constant.bannershow()) {
            AdView adView = new AdView(this);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(Constant.Banner);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.ad_view);
            layout.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.testdev)
                    .build();


            final FrameLayout fm = (FrameLayout) findViewById(R.id.ad_vi1ew);


            try {
                adView.loadAd(adRequest);
                adView.setAdListener(new AdListener() {

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        fm.setVisibility(View.VISIBLE);
                    }
                });
            } catch (Exception e) {

            }
        }

    }

    static InterstitialAd mInterstitialAd;

    private static void Interreload() {
        if (Constant.intershow()) {
            final ProgressDialog pd = new ProgressDialog(ac);
            pd.setMessage("Loading for sponsor Ads , PLease Wait ....");
            pd.show();

            mInterstitialAd = new InterstitialAd(ac);
            mInterstitialAd.setAdUnitId(Constant.Inter);
            AdRequest adRequestinter = new AdRequest.Builder().addTestDevice("6CC2048E31D4506B7BFEFEFD6A4BC0F6").build();
            mInterstitialAd.loadAd(adRequestinter);
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mInterstitialAd.show();
                    pd.dismiss();
                    Showdialog();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    pd.dismiss();
                    Showdialog();
                }
            });


        } else {

            if (Constant.IsShowStartappads()) {

                final ProgressDialog pd = new ProgressDialog(ac);
                pd.setMessage("Loading for sponsor Ads , PLease Wait ....");
                pd.show();
                final MoPubInterstitial mInterstitial = new MoPubInterstitial(ac, Constant.mopubInter);
                mInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
                    @Override
                    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                        mInterstitial.show();
                        pd.dismiss();
                        Showdialog();
                    }

                    @Override
                    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                        pd.dismiss();
                        Showdialog();
                    }

                    @Override
                    public void onInterstitialShown(MoPubInterstitial interstitial) {

                    }

                    @Override
                    public void onInterstitialClicked(MoPubInterstitial interstitial) {

                    }

                    @Override
                    public void onInterstitialDismissed(MoPubInterstitial interstitial) {

                    }
                });
                mInterstitial.load();
            }else{
                Showdialog();
            }


        }
    }

    private void SetMopubBanner() {
        if (Constant.IsShowStartappads()){
            SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(Constant.mopubBanner);
            MoPub.initializeSdk(this, configBuilder.build(), new SdkInitializationListener() {
                @Override
                public void onInitializationFinished() {
                    Log.e("onBannerLoaded","onBannerLoaded");
                    MoPubView moPubView = (MoPubView) findViewById(R.id.adview);
                    moPubView.setAdUnitId(Constant.mopubBanner); // Enter your Ad Unit ID from www.mopub.com
                    moPubView.loadAd();
                    final FrameLayout fm = (FrameLayout) findViewById(R.id.ad_vi1ew);
                    moPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
                        @Override
                        public void onBannerLoaded(MoPubView banner) {
                            fm.setVisibility(View.VISIBLE);
                            Log.e("onBannerLoaded2","onBannerLoaded2");
                        }

                        @Override
                        public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                            Log.e("onBannerFailed",errorCode+"");
                        }

                        @Override
                        public void onBannerClicked(MoPubView banner) {
                            Log.e("onBannerClicked","onBannerClicked");
                        }

                        @Override
                        public void onBannerExpanded(MoPubView banner) {
                            Log.e("onBannerExpanded","onBannerExpanded");
                        }

                        @Override
                        public void onBannerCollapsed(MoPubView banner) {
                            Log.e("onBannerCollapsed","onBannerCollapsed");
                        }
                    });
                }
            });


        }

    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recycler);
        ac = this;
        Setbanner_Ads();
        SetMopubBanner();
      //  ShowStartappbanner();

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(ac);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        Bundle extras = getIntent().getExtras();
        imgurl = extras.getString(Fragment_movie.EXTRA_ANIMAL_ITEM);
        mid = extras.getString("mid");
        //  cate_str = extras.getString("cate");


        adapter = new Series_Detail_recycler_adapter(ac, new ArrayList<get>(), new ArrayList<header_object>());
        rv.setAdapter(adapter);

        ImageView back = (ImageView) findViewById(R.id.gg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });

        tempss = (ImageView) findViewById(R.id.temss);
        detailback = (ImageView) findViewById(R.id.detailback);
        title = (Zawgyitextview) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.iv);
        fakeiv = (ImageView) findViewById(R.id.iv_fake);
        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        fakelayout = (LinearLayout) findViewById(R.id.fakelayout);
        ss = (RelativeLayout) findViewById(R.id.ss);
        temrss = (RelativeLayout) findViewById(R.id.temrss);

        moviename = extras.getString("name");
        title.setText(Html.fromHtml("<b>" + extras.getString("name") + "</b>"));

        final String fpath = Constant.datalocation_scover + "s" + mid + ".fmovie";
        final File myfile = new File(fpath);

        if (myfile.exists() && Constant.saveimage_setting.equals("v7")) {

            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                    Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                    imageView.setBackground(myDrawable);
                    Bitmap bmp = Constant.blurRenderScript(ac, myBitmap, 25);
                    detailback.setBackground(new BitmapDrawable(ac.getResources(), bmp));
                    int DominantColor = Constant.getDominantColor(bmp);
                    MainActivity.back(bmp, DominantColor);
                }
            });

        } else {

            Picasso.with(this).load(imgurl).networkPolicy(NetworkPolicy.OFFLINE).into(fakeiv, new Callback() {
                @Override
                public void onSuccess() {

                    imageView.setBackground(fakeiv.getDrawable());
                    Bitmap ff = ((BitmapDrawable) fakeiv.getDrawable()).getBitmap();
                    Bitmap bmp = Constant.blurRenderScript(ac, ff, 25);
                    detailback.setBackground(new BitmapDrawable(ac.getResources(), bmp));
                    int DominantColor = Constant.getDominantColor(bmp);
                    MainActivity.back(bmp, DominantColor);
                }

                @Override
                public void onError() {
                    // Try again online if cache failed
                    Picasso.with(ac)
                            .load(imgurl)
                            .into(fakeiv, new Callback() {
                                @Override
                                public void onSuccess() {

                                    imageView.setBackground(fakeiv.getDrawable());
                                    Bitmap ff = ((BitmapDrawable) fakeiv.getDrawable()).getBitmap();
                                    Bitmap bmp = Constant.blurRenderScript(ac, ff, 25);
                                    detailback.setBackground(new BitmapDrawable(ac.getResources(), bmp));
                                    int DominantColor = Constant.getDominantColor(bmp);
                                    MainActivity.back(bmp, DominantColor);
                                    if (!myfile.exists()) {
                                        Constant.Savetosdcard(ac, imgurl, "s" + mid, "s");
                                    }

                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
            });

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyRequest.getseriesdetail(mid);
            }
        }, 1000);


        prefs = getSharedPreferences("usercurrent", Context.MODE_PRIVATE);
    }

    static ArrayList<header_object> headerlist;
    static ArrayList<get> com_list;
    static int total_com_count;

    boolean first = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (first) {
            first = false;
        } else {
            pg.hide();
        }

    }


    public static void Feedback(final String s) {

        pg.setVisibility(View.GONE);

        fakelayout.setVisibility(View.GONE);

        headerlist = new ArrayList<header_object>();
        header_object ho = new header_object();
        ho.setLike(Jsonparser.getonestring(s, "like"));
        ho.setView(Jsonparser.getonestring(s, "view"));
        ho.setComment(Jsonparser.getonestring(s, "c_count"));
        ho.setUserlike(Jsonparser.getonestring(s, "userlike"));
        ho.setUserid(Jsonparser.getonestring(s, "userid"));
        String detailjson = Jsonparser.getonestring(s, "detail");
        ho.setCate(Jsonparser.getonestring(s, "cate"));
        String scheck = "0";
        if (prefs.contains(mid)) {
            scheck = prefs.getString(mid, null);
        }
        ho.setMylist(Jsonparser.getseries_ep(s, "series_ep", scheck));


        ho.setDetailjson(detailjson);
        headerlist.add(ho);
        Log.e("mylistsize_SD", headerlist.get(0).getMylist().size() + "");

        adapter.refreshheader(headerlist);
        rv.setVisibility(View.VISIBLE);

        total_com_count = Integer.parseInt(Jsonparser.getonestring(s, "c_count"));
        if (total_com_count > 0) {
            com_list = Jsonparser.getcommentlist(Jsonparser.getonestring(s, "comment"));

            if (total_com_count > 10) {

                int remain = total_com_count - com_count;
                String te = "Load More Comments ( " + remain + " )";
                adapter.refresh(com_list, true, te);

            } else {
                String te = "Load More Comments";
                adapter.refresh(com_list, false, te);
            }
        } else {
            adapter.show_nocomment(true);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                get_shareimage();

            }
        }, 1000);

    }

    static Bitmap shareimage;

    public static void Feedback_Error() {
        ac.finish();
        Toast.makeText(ac, "Network fail", Toast.LENGTH_SHORT).show();

    }


    static int com_count = 10;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        com_count = 10;
        adapter = new Series_Detail_recycler_adapter(ac, new ArrayList<get>(), new ArrayList<header_object>());
        rv.setAdapter(adapter);
    }

    public static void morecomment() {
        com_count = com_count + 10;
        MyRequest.loadcommmnet_series("s" + mid, headerlist.get(0).getUserid(), com_count + "");

    }

    public static void loadcomment_feedback(String s) {
        int total_com_count = Integer.parseInt(Jsonparser.getonestring(s, "c_count"));
        com_list = Jsonparser.getcommentlist(Jsonparser.getonestring(s, "comment"));
        if (total_com_count > com_count) {

            int remain = total_com_count - com_count;
            String te = "Load More Comments ( " + remain + " )";
            adapter.refresh(com_list, true, te);


        } else {
            String te = "Load More Comments";
            adapter.refresh(com_list, false, te);
        }
    }

    public static void loadcomment_feedbackerror() {
        adapter.showpg(false, com_list, true);
        Toast.makeText(ac, "Network Fail", Toast.LENGTH_SHORT).show();
    }

    public static String getmname() {
        return moviename;
    }


    public static void sendcomment(String commm1) {


        get eg = new get();
        eg.setCtext(commm1);
        eg.setCtime("Posting");
        eg.setCusername(Constant.username);
        eg.setCuserfbid(Constant.fbid);
        eg.setCmyment("false");
        eg.setGold(Constant.GoldCon());
        try {
            com_list.add(0, eg);
        } catch (Exception e) {
            com_list = new ArrayList<>();
            com_list.add(eg);
        }


        if (total_com_count > com_count) {

            int remain = total_com_count - com_count;
            String tee = "Load More Comments ( " + remain + " )";
            adapter.refresh(com_list, true, tee);


        } else {
            String teb = "Load More Comments";
            adapter.refresh(com_list, false, teb);
        }


        commm1 = Encode_Decoder.encoding_string(commm1);
        MyRequest.postcomment_series(headerlist.get(0).getUserid(), "s" + mid, commm1, "0", com_count + "");

    }

    public static void Editcomment(String commm1, String pos) {

        int pp = Integer.parseInt(pos);
        com_list.get(pp).setCtext(commm1);
        com_list.get(pp).setCtime("Editing");

        if (total_com_count > com_count) {

            int remain = total_com_count - com_count;
            String tee = "Load More Comments ( " + remain + " )";
            adapter.refresh(com_list, true, tee);


        } else {
            String teb = "Load More Comments";
            adapter.refresh(com_list, false, teb);
        }


        commm1 = Encode_Decoder.encoding_string(commm1);
        MyRequest.postcomment_series(headerlist.get(0).getUserid(), "s" + mid, commm1, com_list.get(pp).getCid(), com_count + "");
    }

    public static void postcomment_feedbackerror() {

        com_list.remove(0);
        if (total_com_count > com_count) {

            int remain = total_com_count - com_count;
            String tee = "Load More Comments ( " + remain + " )";
            adapter.refresh(com_list, true, tee);


        } else {
            String teb = "Load More Comments";
            adapter.refresh(com_list, false, teb);
        }

        Toast.makeText(ac, "Network Fail", Toast.LENGTH_SHORT).show();


    }

    public static void userlike() {
        MyRequest.userlike("s" + mid);
    }

    public static void screeshotImage() {

        if (shareimage == null) {
            get_shareimage();
        }
        Constant.sharetofacebook(ac, shareimage);

    }

    public static void get_shareimage() {
        ss.setDrawingCacheEnabled(true);
        shareimage = Bitmap.createBitmap(ss.getDrawingCache());
        tempss.setImageBitmap(shareimage);
        View v = temrss;
        v.setDrawingCacheEnabled(true);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache(true);
        shareimage = Bitmap.createBitmap(v.getDrawingCache());
    }

    public static void mustlogin() {
        Dialog_controller.Mustlogin(ac);
    }

    public static void refresh() {
        adapter.justrefresh();
    }

    public static void showwatch(String mid, String title) {
        seriestitle = title;
        Intent it = new Intent(ac, Load_series_video.class);
        it.putExtra("mid", mid);
        ac.startActivity(it);

    }

    static String hdurl, hdsize, sdurl, sdsize, seriestitle;

    public static void setopenurl(String hdurl1, String hdsize1, String sdurl1, String sdsize1) {

        hdurl = hdurl1;
        hdsize = hdsize1;
        sdurl = sdurl1;
        sdsize = sdsize1;
        Interreload();

    }

    public static void Showdialog() {

        final Dialog Showdownloaddialog = new Dialog(ac, R.style.PopTheme);
        Showdownloaddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Showdownloaddialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Showdownloaddialog.setContentView(R.layout.series_dialog);
        Showdownloaddialog.setCanceledOnTouchOutside(true);
        Showdownloaddialog.show();
        Button copy = (Button) Showdownloaddialog.findViewById(R.id.cancel_btn);
        Button downloadvideo = (Button) Showdownloaddialog.findViewById(R.id.hdbutton);
        Button watch = (Button) Showdownloaddialog.findViewById(R.id.sdbutton);
        TextView serisename = (TextView) Showdownloaddialog.findViewById(R.id.title);
        serisename.setText(moviename + " ( " + seriestitle + " )");
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showwatchdialog();
                Showdownloaddialog.dismiss();
            }
        });

        downloadvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdownloaddialog.dismiss();
                if (Constant.Isdmopen()){
                    Showdownloaddialog();
                }else{
                    Dialog_controller.OnlyforgoldUser(ac,"Only Gold User Can Use Download Manager");
                }

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showcopydialog();
                Showdownloaddialog.dismiss();
            }
        });


    }

    public static void Showdownloaddialog() {
        final Dialog Showdownloaddialog = new Dialog(ac, R.style.PopTheme);
        Showdownloaddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Showdownloaddialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Showdownloaddialog.setContentView(R.layout.download_dialog);
        Showdownloaddialog.setCanceledOnTouchOutside(true);
        Showdownloaddialog.show();
        Button cancel_btn = (Button) Showdownloaddialog.findViewById(R.id.cancel_btn);
        Button hdbutton = (Button) Showdownloaddialog.findViewById(R.id.hdbutton);
        Button sdbButton = (Button) Showdownloaddialog.findViewById(R.id.sdbutton);

        hdbutton.setText("HD Quality : " + hdsize);
        sdbButton.setText("SD Quality : " + sdsize);
        if (hdsize.equals("0")) {
            hdbutton.setText("HD Quality : Unavailable");
        }

        if (sdsize.equals("0")) {
            sdbButton.setText("SD Quality : Unavailable");
        }

        hdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdownloaddialog.dismiss();
                String epname = moviename + " ( " + seriestitle + " )";
                if (!hdsize.equals("0")) {
                    if (epname.contains("'")) {
                        epname = epname.replace("'", "");
                    }


                    String filecover = Constant.datalocation_scover + "s" + mid + ".fmovie";
                    Mydatabase_down mdb = new Mydatabase_down(ac);
                    mdb.insertdata(hdurl + "", epname, "0", filecover, System.currentTimeMillis() + "");
                    Intent it = new Intent(ac, Mydownloadmanager.class);
                    ac.startActivity(it);
                }


            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdownloaddialog.dismiss();
                String epname = moviename + " ( " + seriestitle + " )";
                if (!hdsize.equals("0")) {
                    if (epname.contains("'")) {
                        epname = epname.replace("'", "");
                    }


                    String filecover = Constant.datalocation_scover + "s" + mid + ".fmovie";
                    Mydatabase_down mdb = new Mydatabase_down(ac);
                    mdb.insertdata(sdurl + "", epname, "0", filecover, System.currentTimeMillis() + "");
                    Intent it = new Intent(ac, Mydownloadmanager.class);
                    ac.startActivity(it);
                }


            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdownloaddialog.dismiss();
            }
        });
    }

    public static void Showwatchdialog() {
        final Dialog Showwatchdialog = new Dialog(ac, R.style.PopTheme);
        Showwatchdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Showwatchdialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Showwatchdialog.setContentView(R.layout.video_dialog);
        Showwatchdialog.setCanceledOnTouchOutside(true);
        Showwatchdialog.show();
        Button cancel_btn = (Button) Showwatchdialog.findViewById(R.id.cancel_btn);
        Button hdbutton = (Button) Showwatchdialog.findViewById(R.id.hdbutton);
        Button sdbButton = (Button) Showwatchdialog.findViewById(R.id.sdbutton);


        hdbutton.setText("HD Quality : " + hdsize);
        sdbButton.setText("SD Quality : " + sdsize);

        if (hdsize.equals("0")) {
            hdbutton.setText("HD Quality : Unavailable");
        }

        if (sdsize.equals("0")) {
            sdbButton.setText("SD Quality : Unavailable");
        }

        hdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hdsize.equals("0")) {
                    Intent it = new Intent(ac, Myexoplayer.class);
                    it.putExtra("url", hdurl);
                    ac.startActivity(it);
                }
                Showwatchdialog.dismiss();

            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sdsize.equals("0")) {
                    Intent it = new Intent(ac, Myexoplayer.class);
                    it.putExtra("url", sdurl);
                    ac.startActivity(it);
                }

                Showwatchdialog.dismiss();

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Showwatchdialog.dismiss();
            }
        });

    }

    public static void Showcopydialog() {
        final Dialog Showcopydialog = new Dialog(ac, R.style.PopTheme);
        Showcopydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Showcopydialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Showcopydialog.setContentView(R.layout.copy_dialog);
        Showcopydialog.setCanceledOnTouchOutside(true);
        Showcopydialog.show();
        Button cancel_btn = (Button) Showcopydialog.findViewById(R.id.cancel_btn);
        Button hdbutton = (Button) Showcopydialog.findViewById(R.id.hdbutton);
        Button sdbButton = (Button) Showcopydialog.findViewById(R.id.sdbutton);

        hdbutton.setText("HD Quality : " + hdsize);
        sdbButton.setText("SD Quality : " + sdsize);
        if (hdsize.equals("0")) {
            hdbutton.setText("HD Quality : Unavailable");
        }

        if (sdsize.equals("0")) {
            sdbButton.setText("SD Quality : Unavailable");
        }


        hdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hdsize.equals("0")) {
                    setClipboard(ac, hdurl);
                    Toast.makeText(ac, "Copied Link\n" + sdurl, Toast.LENGTH_SHORT).show();
                }

                Showcopydialog.dismiss();
            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sdsize.equals("0")) {
                    setClipboard(ac, sdurl);
                    Toast.makeText(ac, "Copied Link \n" + sdurl, Toast.LENGTH_SHORT).show();
                }

                Showcopydialog.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Loading_ourvideo.class);
                it.putExtra("a", "help");
                ac.startActivity(it);
                Showcopydialog.dismiss();

            }
        });
    }

    private static void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

}
