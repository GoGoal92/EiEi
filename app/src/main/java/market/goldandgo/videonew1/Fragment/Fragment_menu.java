package market.goldandgo.videonew1.Fragment;

/**
 * Created by Go Goal on 11/25/2016.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import market.goldandgo.videonew1.*;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.fancydialoglib.*;


/**
 * Created by Go Goal on 11/20/2016.
 */

public class Fragment_menu extends Fragment {


    public static Fragment_menu newInstance() {

        Bundle args = new Bundle();

        Fragment_menu fragment = new Fragment_menu();
        fragment.setArguments(args);
        return fragment;
    }


    static FragmentActivity ac;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ac = getActivity();


    }


    LinearLayout login,joingroup,likeus,imeng,bookmark,help_use,help_download,down_mgr;
    static ImageView userprofile, fakbutton;
    static TextView username,tvengine,accstatus;
    static RelativeLayout border;
    static ImageView gold;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mymenu, container, false);

        login = (LinearLayout) v.findViewById(R.id.login);
        down_mgr= (LinearLayout) v.findViewById(R.id.downloadmgr);
        bookmark= (LinearLayout) v.findViewById(R.id.bookmark);
        imeng= (LinearLayout) v.findViewById(R.id.imeng);
        accstatus= (TextView) v.findViewById(R.id.accstaus);




        joingroup = (LinearLayout) v.findViewById(R.id.joingroup);
        likeus = (LinearLayout) v.findViewById(R.id.likepage);
        help_use= (LinearLayout) v.findViewById(R.id.help_use);
        help_download= (LinearLayout) v.findViewById(R.id.helpdownload);

        border= (RelativeLayout) v.findViewById(R.id.border);
        gold= (ImageView) v.findViewById(R.id.golduser);



        username = (TextView) v.findViewById(R.id.username);
        tvengine= (TextView) v.findViewById(R.id.tvengine);
        userprofile = (ImageView) v.findViewById(R.id.userprofile);
        fakbutton = (ImageView) v.findViewById(R.id.fakelogin);

        tvengine.setText("Image Engine ( "+Constant.saveimage_setting+" )");

        username.setText(Constant.username);

         if (!Constant.fbid.equals("0") && !Constant.fbid.equals("1")) {
             fakbutton.setVisibility(View.GONE);
             Picasso.with(ac).load(Constant.fbimg_url(Constant.fbid)).networkPolicy(NetworkPolicy.OFFLINE).into(userprofile, new Callback() {
                 @Override
                 public void onSuccess() {

                 }

                 @Override
                 public void onError() {
                     // Try again online if cache failed
                     Picasso.with(ac)
                             .load(Constant.fbimg_url(Constant.fbid))
                             .into(userprofile, new Callback() {
                                 @Override
                                 public void onSuccess() {



                                 }

                                 @Override
                                 public void onError() {
                                 }
                             });
                 }
             });
             accstatus.setVisibility(View.VISIBLE);

             if (Constant.GoldCon()){
                 border.setBackgroundResource(R.color.yello);
                 gold.setVisibility(View.VISIBLE);
                 accstatus.setText(Constant.mill_to_day());
             }

        }

        if (Constant.fbid.equals("1")) {
            fakbutton.setVisibility(View.GONE);
             userprofile.setImageResource(R.drawable.appicon);
         }


        down_mgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.Isdmopen()){
                    Intent it = new Intent(ac, Mydownloadmanager.class);
                    startActivity(it);
                }else{
                    Dialog_controller.OnlyforgoldUser(ac,"Only Gold User Can Use Download Manager");
                }


            }
        });


        help_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Loading_ourvideo.class);
                it.putExtra("a","use");
                startActivity(it);
            }
        });


        help_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Loading_ourvideo.class);
                it.putExtra("a","help");
                startActivity(it);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.fbid.equals("0")) {
                    Intent it = new Intent(ac, FacebookLogin.class);
                    startActivity(it);
                }
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent it = new Intent(ac, Bookmark.class);
                    startActivity(it);

            }
        });

        imeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.saveimage_setting.equals("v7")){
                    Dialog_controller.Showdialogto_v8(ac);
                }else{
                    Dialog_controller.Showdialogto_v7(ac);
                }

            }
        });


        joingroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://web.facebook.com/groups/"+Constant.facebookgroup));
                startActivity(intent);
           /*Intent it=new Intent(ac,TestAds.class);
           startActivity(it);*/

            }
        });

        likeus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse("fb://page/" + Constant.facebookpage));
                    startActivity(intent);
                }catch (Exception e){

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse("https://web.facebook.com/"+Constant.facebookpage));
                    startActivity(intent);

                }

            }
        });


        return v;
    }


    public static void setfbimage() {

        fakbutton.setVisibility(View.GONE);
        username.setText(Constant.username);
        Picasso.with(ac).load(Constant.fbimg_url(Constant.fbid)).networkPolicy(NetworkPolicy.OFFLINE).into(userprofile, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                // Try again online if cache failed
                Picasso.with(ac)
                        .load(Constant.fbimg_url(Constant.fbid))
                        .into(userprofile, new Callback() {
                            @Override
                            public void onSuccess() {



                            }

                            @Override
                            public void onError() {
                            }
                        });
            }
        });
        accstatus.setVisibility(View.VISIBLE);

        if (Constant.GoldCon()){
            border.setBackgroundResource(R.color.yello);
            gold.setVisibility(View.VISIBLE);
            accstatus.setText(Constant.mill_to_day());
        }

    }


}
