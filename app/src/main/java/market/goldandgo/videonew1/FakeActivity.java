package market.goldandgo.videonew1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import market.goldandgo.videonew1.Object.Constant;

public class FakeActivity extends AppCompatActivity {


    ProgressDialog pg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fakexml);
        Setbanner_Ads();
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg.show();
                Loadinter();
            }
        });

        pg=new ProgressDialog(this);
        pg.setTitle("Loading");
        pg.setMessage("Contact To Admin Team");
    }

    InterstitialAd interstitialAd;

    private void Loadinter() {

        interstitialAd = new InterstitialAd(this, Constant.Inter);
        interstitialAd.loadAd();
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                pg.dismiss();
                Log.e(":::",adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                pg.dismiss();
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

    }

    private void Setbanner_Ads() {
        AdView adView = new AdView(this, Constant.Banner, AdSize.BANNER_HEIGHT_50);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ads);
        layout.addView(adView);
        adView.loadAd();

    }

}
