package market.goldandgo.videonew1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.wang.avi.AVLoadingIndicatorView;
import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.searchItemClickListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements searchItemClickListener {

    ImageView back;
    AppCompatActivity ac;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static SearchAdapter adapter;
    static EditText ed;
    static ArrayList<get> list;
    static Zawgyitextview result;

    static AVLoadingIndicatorView pg;
    static RelativeLayout network,searchrl;
    static String ss="0";

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
        setContentView(R.layout.search_layout);
        ac = this;
        Setbanner_Ads();

        result= (Zawgyitextview) findViewById(R.id.result);
        ed = (EditText) findViewById(R.id.ed);
        back = (ImageView) findViewById(R.id.gg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        searchrl= (RelativeLayout) findViewById(R.id.searchRL);
        searchrl.setBackgroundColor(MainActivity.getbackcolor());

        adapter = new SearchAdapter(ac, new ArrayList<get>(),this);
        rv.setAdapter(adapter);

        ed.requestFocus();
        ed.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(ed, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 1000);


        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int bb) {
                if (ed.getText().length() > 2) {
                    ss="0";
                    MyRequest.getsearchmovie(ed.getText().toString());
                    result.setVisibility(View.GONE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ss="1";
                    InputMethodManager in = (InputMethodManager)ac.getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(ed.getWindowToken(), 0);
                    MyRequest.getsearchmovie(ed.getText().toString());
                    pg.setVisibility(View.VISIBLE);
                    pg.show();
                    rv.setVisibility(View.GONE);
                    network.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });


        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        network = (RelativeLayout) findViewById(R.id.networkerro);

        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequest.getsearchmovie(ed.getText().toString());
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
                pg.show();
            }
        });



    }





    public static void Feedback_Error() {
        pg.setVisibility(View.GONE);
        network.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        pg.hide();
    }

    public static void Feedback(String s) {

        if (ss.equals("0")){
            pg.setVisibility(View.GONE);
            network.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            list = new ArrayList<>();
            list = Jsonparser.getsearchalllist(s);
            if (list.size()>0){
                adapter.refresh(list);
            }else{
                result.setVisibility(View.VISIBLE);
                result.setText("No result found for '"+ed.getText()+"' " );
                rv.setVisibility(View.GONE);
            }


        }else{
            pg.setVisibility(View.GONE);
            network.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            pg.hide();

            list = new ArrayList<>();
            list = Jsonparser.getsearchalllist(s);



            if (list.size()<1){
                result.setVisibility(View.VISIBLE);
                result.setText("No result found for '"+ed.getText()+"' " );
                rv.setVisibility(View.GONE);
            }else{
                adapter.refresh(list);
            }
        }



    }

    public static final String EXTRA_ANIMAL_ITEM = "animal_image_url";
    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "moviecover";


    @Override
    public void onRVlItemClick(int pos, String imgurl, ImageView shareImageView) {
        boolean conti=false;
        try{
            String mid=list.get(pos).getMid();
            conti=true;
        }catch (Exception e){
            conti=false;
        }

        Log.e(conti+"",conti+"");
        if (conti){
            if (list.get(pos).getMid().contains("s")){
                String removie_s= list.get(pos).getMid().replace("s","");

                Intent intent = new Intent(ac, Series_Detail_recycler.class);
                intent.putExtra("name", list.get(pos).getTitle());
                intent.putExtra(EXTRA_ANIMAL_ITEM, imgurl);
                intent.putExtra("mid",removie_s);
                intent.putExtra("cate", list.get(pos).getCategory());
                intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        ac,
                        shareImageView,
                        ViewCompat.getTransitionName(shareImageView));

                startActivity(intent, options.toBundle());
            }else{

                Intent intent = new Intent(ac, Detail_recycler.class);
                intent.putExtra("name", list.get(pos).getTitle());
                intent.putExtra(EXTRA_ANIMAL_ITEM, imgurl);
                intent.putExtra("mid", list.get(pos).getMid());
                intent.putExtra("cate", list.get(pos).getCategory());
                intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        ac,
                        shareImageView,
                        ViewCompat.getTransitionName(shareImageView));

                startActivity(intent, options.toBundle());

            }
        }


    }
}
