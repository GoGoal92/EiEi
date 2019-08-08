package market.goldandgo.videonew1.fancydialoglib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import market.goldandgo.videonew1.*;
import market.goldandgo.videonew1.Object.Constant;

public class Dialog_controller {

    static String[] dialogcolor = new String[]{"#d22e2d","#303f9e","#368c3b","#f47c00"};


    public static void showdialog(AppCompatActivity ac, String status, String msg, String url) {


        if (status.equals("1")) {

            SeverMatainance(ac, msg);

        }

        if (status.equals("2")) {

            Updateavaliable(ac, msg,url);

        }


        if (status.equals("3")) {

            Forceupdate(ac, msg,url);

        }

        if (status.equals("4")) {

            MsgFromServer(ac, msg);

        }

    }

    private static void MsgFromServer(final AppCompatActivity ac, String msg) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Message From Admin")
                .setBackgroundColor(Color.parseColor(dialogcolor[3]))
                .setMessage(msg)
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[3]))
                .setPositiveBtnText("Continue")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_msg, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent it = new Intent(ac, MainActivity.class);
                        ac.startActivity(it);
                        ac.finish();
                    }
                }).build();
    }

    private static void Forceupdate(final AppCompatActivity ac, String msg, final String url) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("FORCE UPDATE")
                .setBackgroundColor(Color.parseColor(dialogcolor[2]))
                .setMessage(msg)
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[2]))
                .setPositiveBtnText("UPDATE")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_update, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                .parse(url));
                        ac.startActivity(intent);
                        ac.finish();
                    }
                }).build();
    }

    private static void Updateavaliable(final AppCompatActivity ac, String msg, final String url) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Update Available")
                .setBackgroundColor(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setMessage(msg)
                .setNegativeBtnText("Continue")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Update")
                .setNegativeBtnBackground(Color.parseColor(dialogcolor[2]))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(false,true)
                .setIcon(R.drawable.ic_update, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                .parse(url));
                        ac.startActivity(intent);
                        ac.finish();
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent it = new Intent(ac, MainActivity.class);
                        ac.startActivity(it);
                        ac.finish();
                    }
                })
                .build();
    }

    public static void Exitconfirm(final Activity ac) {

        new FancyAlertDialog.Builder(ac)
                .setTitle("Exit Confirmation!")
                .setBackgroundColor(Color.parseColor(dialogcolor[0])) //Don't pass R.color.colorvalue
                .setMessage("Are you sure want to exit?")
                .setNegativeBtnText("No")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[0]))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Exit")
                .setNegativeBtnBackground(R.color.darker_gray)  //Don't pass R.color.colorvalue @android:color/darker_gray
                .setAnimation(Animation.POP)
                .isCancellable(false,true)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                       ac.finish();
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();


    }



    private static void SeverMatainance(final AppCompatActivity ac, String msg) {

        new FancyAlertDialog.Builder(ac)
                .setTitle("Server Matainance Break")
                .setBackgroundColor(Color.parseColor(dialogcolor[0]))
                .setMessage(msg)
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[0]))
                .setPositiveBtnText("ComeBack Later")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        ac.finish();
                    }
                }).build();

    }

    public static void showbandialog(final AppCompatActivity ac) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("you have been blocked")
                .setBackgroundColor(Color.parseColor(dialogcolor[0]))
                .setMessage("Sorry you have been blocked from server.If you think this is an error, contact us")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[0]))
                .setPositiveBtnText("Contact Us")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_block, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        ac.finish();
                        try{
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("fb://page/" + Constant.facebookpage));
                            ac.startActivity(intent);
                        }catch (Exception e){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("https://web.facebook.com/fmoviesgogoal/"));
                            ac.startActivity(intent);
                        }

                    }
                }).build();
    }

    public static void reload(AppCompatActivity ac) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Connection Problem")
                .setBackgroundColor(Color.parseColor(dialogcolor[0]))
                .setMessage("Please Check Your Network And Reload Again")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[0]))
                .setPositiveBtnText("Reload")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_reload, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Splash.reload();
                    }
                }).build();
    }

    public static void cannotunlove(AppCompatActivity ac) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Sorry !")
                .setBackgroundColor(Color.parseColor(dialogcolor[0]))
                .setMessage("You cannot abandoned your love :V")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[0]))
                .setPositiveBtnText("Okay")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                }).build();

    }

    public  static void Mustlogin(final AppCompatActivity ac) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Login Need")
                .setBackgroundColor(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setMessage("This feature need to connect with facebook \n comment ေပးႏိုင္ရန္အတြက္ facebook ႏွင့့္ ခ်ိတ္ဆက္ေပးရန္ လိုအပ္ပါသည္")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Login")
                .setNegativeBtnBackground(R.color.darker_gray)  //Don't pass R.color.colorvalue @android:color/darker_gray
                .setAnimation(Animation.POP)
                .isCancellable(false,true)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent it = new Intent(ac, FacebookLogin.class);
                        ac.startActivity(it);
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }

    public static void Showdialogto_v8(final FragmentActivity ac) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Change v7 to v8")
                .setBackgroundColor(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setMessage("we recommend to dont use this if you can see movies image\n ပံုေတြ ျမင္ရပါက မသံုးရန္ အၾကံျပဳပါသည္။ သံုးပါ က data ပိုကုန္ပါမည္")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Change")
                .setNegativeBtnBackground(R.color.darker_gray)  //Don't pass R.color.colorvalue @android:color/darker_gray
                .setAnimation(Animation.POP)
                .isCancellable(false,true)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        SharedPreferences prefs = ac.getSharedPreferences("imgeng",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("eng", "v8");
                        editor.commit();
                        Intent it=new Intent(ac,Splash.class);
                        ac.startActivity(it);
                        ac.finish();
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }

    public static void Showdialogto_v7(final FragmentActivity ac) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Change v8 to v7")
                .setBackgroundColor(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setMessage("Are you sure to change v7 image engine?\n" +
                        "v7 image engine ကို ခ်ိန္မည္မွာ ေသခ်ာပါသလား?")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Change")
                .setNegativeBtnBackground(R.color.darker_gray)  //Don't pass R.color.colorvalue @android:color/darker_gray
                .setAnimation(Animation.POP)
                .isCancellable(false,true)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        SharedPreferences prefs = ac.getSharedPreferences("imgeng",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("eng", "v7");
                        editor.commit();
                        Intent it=new Intent(ac,Splash.class);
                        ac.startActivity(it);
                        ac.finish();
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }

    public static void gotoads(final  String adsurl, final Activity ac) {

        new FancyAlertDialog.Builder(ac)
                .setTitle("Ads Click")
                .setBackgroundColor(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setMessage("Are you sure to view this Ads?\n" +
                        "ဤ ေၾကာ္ျငာကို ၾကည့္မည္မွာ ေသခ်ာပါသလား?")
                .setNegativeBtnText("No")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Yes")
                .setNegativeBtnBackground(R.color.darker_gray)  //Don't pass R.color.colorvalue @android:color/darker_gray
                .setAnimation(Animation.POP)
                .isCancellable(false,true)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                .parse(adsurl));
                        ac.startActivity(intent);
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();


    }

    public static void setshow(final FragmentActivity ac, final String url, String size, String type) {

        String msg="";
        if (!size.equals("0")){

            if (type.equals("use")){
                msg="Fmovie Application အသံုးျပဳပံုကို "+size+" အရြယ္အစားရွိ Video ကို Fb plan ျဖင့္ၾကည့္၍ ေလ့လာလိုက္ပါ";
            }else{
                msg="Fmovie မွ ဇာတ္ကားမ်ား Download ျပဳလုပ္ပံုကို "+size+" အရြယ္အစားရွိ Video ကို Fb plan ျဖင့္ၾကည့္၍ ေလ့လာလိုက္ပါ";
            }

            Log.e("vidurl",url);

            new FancyAlertDialog.Builder(ac)
                    .setTitle("Help")
                    .setBackgroundColor(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                    .setMessage(msg)
                    .setNegativeBtnText("No")
                    .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))  //Don't pass R.color.colorvalue
                    .setPositiveBtnText("Watch")
                    .setNegativeBtnBackground(R.color.darker_gray)  //Don't pass R.color.colorvalue @android:color/darker_gray
                    .setAnimation(Animation.POP)
                    .isCancellable(false,true)
                    .setIcon(R.drawable.ic_information, Icon.Visible)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            Intent it = new Intent(ac, Myexoplayer.class);
                            it.putExtra("url", url);
                            ac.startActivity(it);
                            ac.finish();
                        }
                    })
                    .OnNegativeClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            ac.finish();
                        }
                    })
                    .build();



        }else{
            msg="ဝမ္းနည္းပါသည္ tuto video မ်ားပ်က္ေနပါသည္";
            new FancyAlertDialog.Builder(ac)
                    .setTitle("Sorry !")
                    .setBackgroundColor(Color.parseColor(dialogcolor[0]))
                    .setMessage(msg)
                    .setPositiveBtnBackground(Color.parseColor(dialogcolor[0]))
                    .setPositiveBtnText("Okay")

                    .setAnimation(Animation.POP)
                    .isCancellable(false,false)
                    .setIcon(R.drawable.ic_matainance, Icon.Visible)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            ac.finish();
                        }
                    }).build();

        }



    }

    public static void mustunistall(final AppCompatActivity ac) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Action Require !")
                .setBackgroundColor(Color.parseColor(dialogcolor[2]))
                .setMessage("To can use version 8.2 , first need to unistall version 8.1")
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[2]))
                .setPositiveBtnText("unistall v8.1")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                         Splash.Unistall();
                    }
                }).build();
    }

    public static void mustunistall_hackapp(AppCompatActivity ac, String umsg, final String upackage) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Action Require !")
                .setBackgroundColor(Color.parseColor(dialogcolor[1]))
                .setMessage(umsg)
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))
                .setPositiveBtnText("unistall")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        Splash.Unistall_hack(upackage);
                    }
                }).build();

    }


    public static void OnlyforgoldUser(FragmentActivity ac, String umsg) {
        new FancyAlertDialog.Builder(ac)
                .setTitle("Special Feacture !")
                .setBackgroundColor(Color.parseColor(dialogcolor[1]))
                .setMessage(umsg)
                .setPositiveBtnBackground(Color.parseColor(dialogcolor[1]))
                .setPositiveBtnText("OKAY")

                .setAnimation(Animation.POP)
                .isCancellable(false,false)
                .setIcon(R.drawable.ic_matainance, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {


                    }
                }).build();

    }

}
