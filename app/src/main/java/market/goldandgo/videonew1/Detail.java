package market.goldandgo.videonew1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import at.blogc.android.views.ExpandableTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import market.goldandgo.videonew1.API.NestedListView;
import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Comment_adapter;
import market.goldandgo.videonew1.Fragment.Fragment_movie;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.Encode_Decoder;
import market.goldandgo.videonew1.Utils.RoundImage;
import market.goldandgo.videonew1.fancydialoglib.Dialog_controller;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {


    ImageView imageView, fakeiv, image_arrow;
    static TextView show_tv, info1, nocomment;
    static AppCompatActivity ac;
    static String imgurl, hdsize, sdsize, HDlink, SDlink, userlike, like, view, userid,moviename;
    static ImageView detailback, loveiv;
    Zawgyitextview title, cate;
    static LinearLayout show_v, hidelayout;
    static String mid, cate_str;
    static ExpandableTextView detail;
    static AVLoadingIndicatorView pg, compg;
    static Button watch_btn, download_btn, copy_btn, loadcomment;
    static LinearLayout commentlayout;
    static NestedListView nlv;
    static Comment_adapter cadapter;
    static int com_count = 10;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        com_count = 10;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        ac = this;
        cadapter = new Comment_adapter(ac, new ArrayList<get>());
        ImageView back = (ImageView) findViewById(R.id.gg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        imgurl = extras.getString(Fragment_movie.EXTRA_ANIMAL_ITEM);
        mid = extras.getString("mid");
        cate_str = extras.getString("cate");


        imageView = (ImageView) findViewById(R.id.iv);
        image_arrow = (ImageView) findViewById(R.id.image_arrow);
        fakeiv = (ImageView) findViewById(R.id.iv_fake);
        show_tv = (TextView) findViewById(R.id.show_tv);
        info1 = (TextView) findViewById(R.id.info1);
        nocomment = (TextView) findViewById(R.id.nocomment);
        title = (Zawgyitextview) findViewById(R.id.title);
        cate = (Zawgyitextview) findViewById(R.id.cate);
        detail = (ExpandableTextView) findViewById(R.id.detail);
        detailback = (ImageView) findViewById(R.id.detailback);
        show_v = (LinearLayout) findViewById(R.id.show_v);
        hidelayout = (LinearLayout) findViewById(R.id.hidelayout);
        commentlayout = (LinearLayout) findViewById(R.id.commentlayout);
        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        loveiv = (ImageView) findViewById(R.id.loveiv);
        watch_btn = (Button) findViewById(R.id.watch);
        download_btn = (Button) findViewById(R.id.download);
        copy_btn = (Button) findViewById(R.id.copylink);
        loadcomment = (Button) findViewById(R.id.loadmorecomment);
        compg = (AVLoadingIndicatorView) findViewById(R.id.avicom);
        nlv = (NestedListView) findViewById(R.id.nlv);
        nlv.setDividerHeight(0);
        nlv.setAdapter(cadapter);

        pg.show();

        detail.setTypeface(Typeface.createFromAsset(getAssets(), "zawgyi.ttf"));

        title.setText(Html.fromHtml("<b>" + extras.getString("name") + "</b>"));

        show_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (detail.isExpanded()) {
                    detail.collapse();
                    image_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    show_tv.setText("SHOW REVIEW");
                } else {
                    detail.expand();
                    image_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
                    show_tv.setText("HIDE REVIEW");
                }


            }
        });


        Picasso.with(this).load(imgurl).networkPolicy(NetworkPolicy.OFFLINE).into(fakeiv, new Callback() {
            @Override
            public void onSuccess() {

                imageView.setBackground(fakeiv.getDrawable());
                Bitmap ff = ((BitmapDrawable) fakeiv.getDrawable()).getBitmap();
                Bitmap bmp = Constant.blurRenderScript(ac, ff, 25);
                int DominantColor=Constant.getDominantColor(bmp);
                MainActivity.back(bmp,DominantColor);

                detailback.setBackground(new BitmapDrawable(ac.getResources(), bmp));
            //    MainActivity.back(((BitmapDrawable) fakeiv.getDrawable()).getBitmap());
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
                                int DominantColor=Constant.getDominantColor(bmp);
                                MainActivity.back(bmp,DominantColor);
                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
        });

        cate.setText("CATEGORY : " + cate_str);

        MyRequest.getMoviedetail(mid);

        watch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Showwatchdialog();

            }
        });

        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Showdownloaddialog();

            }
        });

        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Showcopydialog();

            }
        });

        commentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Biged.class);
                it.putExtra("text", "0");
                startActivity(it);
            }
        });

        loveiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userlike.equals("true")) {
                    Dialog_controller.cannotunlove(ac);

                } else {
                    userlike = "true";
                    loveiv.setBackgroundResource(R.drawable.ic_love);
                    MyRequest.userlike(mid);
                    int pluslike = Integer.parseInt(like) + 1;

                    info1.setText(pluslike + " Love | " + view + " View | " + view + " Comment");
                }
            }
        });

        loadcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com_count = com_count + 10;
                compg.setVisibility(View.VISIBLE);
                loadcomment.setVisibility(View.INVISIBLE);
                MyRequest.loadcommmnet(mid, userid, com_count + "");
            }
        });

    }


    Dialog Showwatchdialog, Showdownloaddialog, Showcopydialog;

    private void Showcopydialog() {
        Showcopydialog = new Dialog(ac, R.style.PopTheme);
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

        hdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(ac, HDlink);
                Toast.makeText(ac, "Copied Link", Toast.LENGTH_SHORT).show();
                Showcopydialog.dismiss();
            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(ac, SDlink);
                Toast.makeText(ac, "Copied Link", Toast.LENGTH_SHORT).show();
                Showcopydialog.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showcopydialog.dismiss();

            }
        });
    }

    private void Showdownloaddialog() {
        Showdownloaddialog = new Dialog(ac, R.style.PopTheme);
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

        hdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdownloaddialog.dismiss();
            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdownloaddialog.dismiss();


            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdownloaddialog.dismiss();
            }
        });
    }

    private void Showwatchdialog() {
        Showwatchdialog = new Dialog(ac, R.style.PopTheme);
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

        hdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showwatchdialog.dismiss();
                Intent it = new Intent(ac, Myexoplayer.class);
                it.putExtra("url", HDlink);
                ac.startActivity(it);
            }
        });
        sdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showwatchdialog.dismiss();
                Intent it = new Intent(ac, Myexoplayer.class);
                it.putExtra("url", SDlink);
                ac.startActivity(it);
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showwatchdialog.dismiss();
            }
        });

    }


    static ArrayList<get> com_list;

    public static void Feedback(final String s) {

        pg.setVisibility(View.GONE);
        hidelayout.setVisibility(View.VISIBLE);
        like = Jsonparser.getonestring(s, "like");
        view = Jsonparser.getonestring(s, "view");
        userlike = Jsonparser.getonestring(s, "userlike");
        String detailjson = Jsonparser.getonestring(s, "detail");
        String vstatus = Jsonparser.getonestring(s, "vstatus");
        HDlink = Jsonparser.getonestring(s, "url");
        SDlink = Jsonparser.getonestring(s, "sdurl");
        hdsize = Jsonparser.getonestring(s, "hdsize");
        sdsize = Jsonparser.getonestring(s, "sdsize");
        userid = Jsonparser.getonestring(s, "userid");

        if (vstatus.equals("1")) {
            detailjson = Constant.cleanstring(detailjson, "1");
        }

        info1.setText(like + " Love | " + view + " View | " + view + " Comment");
        detail.setText(detailjson);

        if (userlike.equals("true")) {
            loveiv.setBackgroundResource(R.drawable.ic_love);
        }

        ac.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                int total_com_count = Integer.parseInt(Jsonparser.getonestring(s, "c_count"));
                if (total_com_count > 1) {
                    nlv.setVisibility(View.VISIBLE);
                    nocomment.setVisibility(View.GONE);
                    com_list = Jsonparser.getcommentlist(Jsonparser.getonestring(s, "comment"));
                    cadapter.refresh(com_list);
                    if (total_com_count > 10) {
                        loadcomment.setVisibility(View.VISIBLE);
                        int remain = total_com_count - com_count;
                        loadcomment.setText("Load More Comments ( " + remain + " )");
                    }


                } else {
                    nlv.setVisibility(View.GONE);
                    nocomment.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public static void Feedback_Error() {
        ac.finish();
        Toast.makeText(ac, "Network fail", Toast.LENGTH_SHORT).show();

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


    static  String commm;
    public static void sendcomment(final String commm1) {
        commm=commm1;
        ac.runOnUiThread(new Runnable(){
            @Override
            public void run(){
                nlv.setVisibility(View.VISIBLE);
                nocomment.setVisibility(View.GONE);

                get eg = new get();
                eg.setCtext(commm);
                eg.setCtime("Posting");
                eg.setCusername(Constant.username);
                eg.setCuserfbid(Constant.fbid);
                eg.setCmyment("false");
                com_list.add(0, eg);
                cadapter.refresh(com_list);

                commm = Encode_Decoder.encoding_string(commm);
                MyRequest.postcomment(userid, mid, commm, "0",com_count+"");
            }
        });

    }


    public static void postcomment_feedbackerror() {

        com_list.remove(0);
        cadapter.refresh(com_list);
        Toast.makeText(ac, "Network Fail", Toast.LENGTH_SHORT).show();

        if (com_list.size()<1){
            nlv.setVisibility(View.GONE);
            nocomment.setVisibility(View.VISIBLE);
        }

    }

    public static void loadcomment_feedback(String s) {
        compg.setVisibility(View.GONE);
        int total_com_count = Integer.parseInt(Jsonparser.getonestring(s, "c_count"));
        com_list = Jsonparser.getcommentlist(Jsonparser.getonestring(s, "comment"));
        cadapter.refresh(com_list);

        if (total_com_count > com_count) {
            loadcomment.setVisibility(View.VISIBLE);
            int remain = total_com_count - com_count;
            loadcomment.setText("Load More Comments ( " + remain + " )");
        } else {
            compg.setVisibility(View.GONE);
            loadcomment.setVisibility(View.INVISIBLE);
        }
    }

    public static void loadcomment_feedbackerror() {
        compg.setVisibility(View.GONE);
        loadcomment.setVisibility(View.INVISIBLE);
        Toast.makeText(ac, "Network Fail", Toast.LENGTH_SHORT).show();
    }
}
