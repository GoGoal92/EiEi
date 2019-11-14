package market.goldandgo.videonew1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import market.goldandgo.videonew1.Adapter.Fragmentadapter_Main;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Utils.CustomViewPager;
import market.goldandgo.videonew1.fancydialoglib.Dialog_controller;


public class MainActivity extends AppCompatActivity {

    Fragmentadapter_Main adapter;
    ViewPager pager;
    CardView search_click;
    static AppCompatActivity ac;
    static LinearLayout activity_main;
    static LinearLayout searchlayout;
    static TabLayout tabLayout;

    public static Bitmap getback() {
        return allback;
    }

    public static int getbackcolor() {
        return backcolor;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        Setbanner_Ads();


        ac = this;
        search_click = (CardView) findViewById(R.id.search_click);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        searchlayout = (LinearLayout) findViewById(R.id.searchcontainer);

        pager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new Fragmentadapter_Main(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tv.setTextColor(Color.WHITE);
            tabLayout.getTabAt(i).setCustomView(tv);

        }


        search_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Search.class);
                startActivity(it);
            }
        });

        Bitmap b = BitmapFactory.decodeResource(ac.getResources(), R.drawable.background);
        int DominantColor = Constant.getDominantColor(b);
        back(b, DominantColor);


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




    static Bitmap allback;
    static int backcolor;

    public static void back(Bitmap bitmap, int color) {
        allback = bitmap;
        backcolor = color;
        activity_main.setBackground(new BitmapDrawable(ac.getResources(), bitmap));
        searchlayout.setBackgroundColor(backcolor);
        tabLayout.setBackgroundColor(backcolor);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            Dialog_controller.Exitconfirm(ac);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
