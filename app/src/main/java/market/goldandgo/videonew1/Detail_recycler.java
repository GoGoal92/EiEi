package market.goldandgo.videonew1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Detail_recycler_adapter;
import market.goldandgo.videonew1.Fragment.Fragment_movie;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Object.header_object;
import market.goldandgo.videonew1.Utils.Encode_Decoder;
import market.goldandgo.videonew1.Utils.Mydatabase_down;
import market.goldandgo.videonew1.fancydialoglib.Dialog_controller;


public class Detail_recycler extends AppCompatActivity {

    static RecyclerView rv;
    LinearLayoutManager llm;
    static LinearLayout fakelayout;
    static AppCompatActivity ac;
    static Detail_recycler_adapter adapter;
    static ImageView detailback;
    static AVLoadingIndicatorView pg;
    static String imgurl, mid;
    ImageView imageView, fakeiv;
    Zawgyitextview title;
    static RelativeLayout ss, temrss;

    static ImageView tempss;
    static String moviename;

    public static String getimageurl() {
        return imgurl;
    }

    public static String getmid() {
        return mid;
    }

    private void Setbanner_Ads() {
        if (Constant.bannershow()) {
            AdView adView = new AdView(this, Constant.Banner, AdSize.BANNER_HEIGHT_50);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.ad_view);
            layout.addView(adView);
            final FrameLayout fm = (FrameLayout) findViewById(R.id.ad_vi1ew);
            adView.setAdListener(new AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {

                }

                @Override
                public void onAdLoaded(Ad ad) {

                    fm.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
            adView.loadAd();


        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recycler);
        ac = this;
        Setbanner_Ads();


        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(ac);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        Bundle extras = getIntent().getExtras();
        imgurl = extras.getString(Fragment_movie.EXTRA_ANIMAL_ITEM);
        mid = extras.getString("mid");
        //  cate_str = extras.getString("cate");


        adapter = new Detail_recycler_adapter(ac, new ArrayList<get>(), new ArrayList<header_object>());
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


        final String fpath = Constant.datalocation_movie + mid + ".fmovie";
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
                    int DominantColor = Constant.getDominantColor(bmp);


                    detailback.setBackground(new BitmapDrawable(ac.getResources(), bmp));
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
                                        Constant.Savetosdcard(ac, imgurl, mid, "M");
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
                MyRequest.getMoviedetail(mid);
            }
        }, 1000);


    }

    static InterstitialAd interstitialAd;

    public static void Interreload(final String type, final String hdsize, final String sdsize, final String HDlink, final String SDlink) {


        if (Constant.intershow()) {
            final ProgressDialog pd = new ProgressDialog(ac);
            pd.setMessage("Loading for sponsor Ads , PLease Wait ....");
            pd.show();

            interstitialAd = new InterstitialAd(ac, Constant.Inter);
            interstitialAd.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {


                    pd.dismiss();
                    if (type.equals("watch")) {
                        Showwatchdialog(hdsize, sdsize, HDlink, SDlink);
                    } else if (type.equals("download")) {
                        Showdownloaddialog(hdsize, sdsize, HDlink, SDlink);
                    } else {
                        Showcopydialog(hdsize, sdsize, HDlink, SDlink);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    interstitialAd.show();

                    pd.dismiss();
                    if (type.equals("watch")) {
                        Showwatchdialog(hdsize, sdsize, HDlink, SDlink);
                    } else if (type.equals("download")) {
                        Showdownloaddialog(hdsize, sdsize, HDlink, SDlink);
                    } else {
                        Showcopydialog(hdsize, sdsize, HDlink, SDlink);
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });

            interstitialAd.loadAd();

        } else {


//
            if (type.equals("watch")) {
                Showwatchdialog(hdsize, sdsize, HDlink, SDlink);
            } else if (type.equals("download")) {
                Showdownloaddialog(hdsize, sdsize, HDlink, SDlink);
            } else {
                Showcopydialog(hdsize, sdsize, HDlink, SDlink);
            }

    }


}



  /*  public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }*/

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

    static ArrayList<header_object> headerlist;
    static ArrayList<get> com_list;
    static int total_com_count;

    public static void Feedback(final String s) {

        pg.setVisibility(View.GONE);
        fakelayout.setVisibility(View.GONE);

        headerlist = new ArrayList<header_object>();
        header_object ho = new header_object();
        ho.setLike(Jsonparser.getonestring(s, "like"));
        ho.setView(Jsonparser.getonestring(s, "view"));
        ho.setComment(Jsonparser.getonestring(s, "c_count"));
        ho.setUserlike(Jsonparser.getonestring(s, "userlike"));
        ho.setHDlink(Jsonparser.getonestring(s, "url"));
        ho.setSDlink(Jsonparser.getonestring(s, "sdurl"));
        ho.setHdsize(Jsonparser.getonestring(s, "hdsize"));
        ho.setSdsize(Jsonparser.getonestring(s, "sdsize"));
        ho.setUserid(Jsonparser.getonestring(s, "userid"));
        String vstatus = Jsonparser.getonestring(s, "vstatus");
        String detailjson = Jsonparser.getonestring(s, "detail");
        ho.setCate(Jsonparser.getonestring(s, "cate"));


        if (vstatus.equals("1")) {
            detailjson = Constant.cleanstring(detailjson, "1");
        }

        ho.setDetailjson(detailjson);
        headerlist.add(ho);
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
        adapter = new Detail_recycler_adapter(ac, new ArrayList<get>(), new ArrayList<header_object>());
        rv.setAdapter(adapter);
    }

    public static void morecomment() {
        com_count = com_count + 10;
        MyRequest.loadcommmnet(mid, headerlist.get(0).getUserid(), com_count + "");

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


        String te = "Load More Comments";

        if (total_com_count > com_count) {

            int remain = total_com_count - com_count;
            String tee = "Load More Comments ( " + remain + " )";
            adapter.refresh(com_list, true, tee);


        } else {
            String teb = "Load More Comments";
            adapter.refresh(com_list, false, teb);
        }


        commm1 = Encode_Decoder.encoding_string(commm1);
        MyRequest.postcomment(headerlist.get(0).getUserid(), mid, commm1, "0", com_count + "");

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
        MyRequest.postcomment(headerlist.get(0).getUserid(), mid, commm1, com_list.get(pp).getCid(), com_count + "");
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
        MyRequest.userlike(mid);
    }

    public static void Showdownloaddialog(final String hdsize, final String sdsize, final String HDlink, final String SDlink) {

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
                if (!hdsize.equals("0")) {
                    if (moviename.contains("'")) {
                        moviename = moviename.replace("'", "");
                    }

                    String filecover = Constant.datalocation_movie + mid + ".fmovie";
                    Mydatabase_down mdb = new Mydatabase_down(ac);
                    mdb.insertdata(HDlink + "", moviename, "0", filecover, System.currentTimeMillis() + "");
                    Intent it = new Intent(ac, Mydownloadmanager.class);
                    ac.startActivity(it);
                }


            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Showdownloaddialog.dismiss();
                if (!sdsize.equals("0")) {
                    if (moviename.contains("'")) {
                        moviename = moviename.replace("'", "");
                    }

                    String filecover = Constant.datalocation_movie + mid + ".fmovie";
                    Mydatabase_down mdb = new Mydatabase_down(ac);
                    mdb.insertdata(SDlink + "", moviename, "0", filecover, System.currentTimeMillis() + "");
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

    public static void Showcopydialog(final String hdsize, final String sdsize, final String HDlink, final String SDlink) {
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
                    setClipboard(ac, HDlink);
                    Toast.makeText(ac, "Copied Link\n" + HDlink, Toast.LENGTH_SHORT).show();
                }

                Showcopydialog.dismiss();
            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sdsize.equals("0")) {
                    setClipboard(ac, SDlink);
                    Toast.makeText(ac, "Copied Link\n" + SDlink, Toast.LENGTH_SHORT).show();
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

    public static void Showwatchdialog(final String hdsize, final String sdsize, final String HDlink, final String SDlink) {
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
                    it.putExtra("url", HDlink);
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
                    it.putExtra("url", SDlink);
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
}
