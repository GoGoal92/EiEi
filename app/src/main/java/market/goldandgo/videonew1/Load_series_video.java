package market.goldandgo.videonew1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Jsonparser;

public class Load_series_video extends AppCompatActivity {

    static AppCompatActivity ac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = this;
        setContentView(R.layout.facebook_login_layout);
        RelativeLayout fakerl = (RelativeLayout) findViewById(R.id.fakerl);
        fakerl.setClickable(true);


        String mid = getIntent().getExtras().getString("mid");

        MyRequest.getseries_video(mid);

    }

    public static void feedback(String s) {
        String hdurl = Jsonparser.getonestring(s, "url");
        String sdurl = Jsonparser.getonestring(s, "sdurl");
        String hdsize = Jsonparser.getonestring(s, "hdsize");
        String sdsize = Jsonparser.getonestring(s, "sdsize");
        Series_Detail_recycler.setopenurl(hdurl, hdsize, sdurl, sdsize);
        ac.finish();
    }

    public static void feedbackerror() {
        ac.finish();
        Toast.makeText(ac, "Network Fail", Toast.LENGTH_SHORT).show();
    }
}
