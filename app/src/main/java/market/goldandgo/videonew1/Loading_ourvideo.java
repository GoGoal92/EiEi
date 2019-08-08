package market.goldandgo.videonew1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import market.goldandgo.videonew1.Fragment.Fragment_menu;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.fancydialoglib.Dialog_controller;

public class Loading_ourvideo extends AppCompatActivity{

    static AppCompatActivity ac;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_login_layout);
        ac=this;
        String type=getIntent().getExtras().getString("a");
        MyRequest.gettuto_video(type);



    }

    public static void feedbackerror() {
        Toast.makeText(ac,"Network Fail",Toast.LENGTH_SHORT).show();
        ac.finish();
    }

    public static void feedback(String s, String type) {
        String url= Jsonparser.getonestring(s,"url");
        String size= Jsonparser.getonestring(s,"size");
        Dialog_controller.setshow(ac,url,size,type);
    }
}
