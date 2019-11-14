package market.goldandgo.videonew1.Object;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Locale;

import market.goldandgo.videonew1.Utils.Encode_Decoder;

/**
 * Created by Go Goal on 6/29/2017.
 */

public class Constant {

    public static final String INFO1 = "ဤ app ၏ သေဘာတရားမွာ FB မွာတင္ထားတဲ့ ဇာတ္ကားမ်ားကို ျပန္ျပတာျဖစ္တဲ့ အတြက္ Operator ေတြက FB Package ထဲက ျဖတ္သြားတာပါ ။\n" +
            "\n" +
            "ဒါေၾကာင့္ App အတြက္ အသံုးျပဳရတဲ့ Data ဟာ Mobile Data နဲ႔ျဖစ္ျပီး ဇာတ္ကားၾကည့္တာ ေဒါင္းတာဘဲ FB plan နဲ႔ပါ ။";

    public static final String INFO2 = "ဇာတ္ကားေတြဟာ FB ေပၚတင္ထားတာျဖစ္လို႔ တခ်ိဳ႕ဇာတ္ကားေတြဟာ ပ်က္က်ႏိုင္ပါတယ္ ။\n" +
            "\n" +
            "ပ်က္က်ေနတဲ့ ဇာတ္ကားမ်ားကို Report ျပဳလုပ္ျခင္းျဖင့္ Admin မ်ားကို အသိေပးႏိုင္ပါသည္ ။";
    public static final String INFO3 = "တစ္ခ်ိဳ႔ ဇာတ္ကားေတြဟာ Gold ျဖင့္ေရာင္းပါသည္ ။ \n" +
            "\n" +
            "Gold ကို Free Gold ဆိုေသာ Game ေဆာ့ျခင္းျဖင့္ အခမဲ့ရယူႏိုင္ပါသည္ ။";
    public static final String INFO4 = "သင့္တြင္ဖုန္းေဘလ္ (သို႔) အင္တာနက္ Plan မရွိပါက ဤ app အားဖြင့္၍ ရမည္မဟုတ္ ။\n" +
            "\n" +
            "FB plan သည္ ဇာတ္ကား ၾကည့္ရာ ေဒါင္းရာတြင္ဘဲ အၾကံဳးဝင္မည္ျဖစ္သည္။\n";
    public static final String INFO5 = "Rate Us ခလုပ္ကို ႏိုပ္၍ PlayStore ေပၚတြင္ ၾကယ္ ၅ လံုး ေပးျခင္း၊ လိုအပ္ခ်က္မ်ားကို ေျပာဆိုျခင္းမ်ားျဖင့္ Fmovie ကို ေကာင္းသထက္ေကာင္းလာေအာင္ အၾကံဥာဏ္မ်ား ေပးႏိုင္ပါသည္ ။";
    public static final String INFO6 = "Like F Movie Facebook Page အားႏိုပ္ပါက FMOVIE Facebook Page သို႔ေရာက္ရွိသြားမယ္ျဖစ္သည္ ။\n" +
            "\n" +
            "Page အား Like လုပ္ျပီး See First လုပ္ထားျခင္းအားျဖင့္ FMOVIE ၏ ေနာက္ဆံုးရ သတင္းမ်ားကို သိရွိလိုက္ပါ ။";


    public static final String FIREBASE_APP = "https://fmovie-14887.firebaseio.com";


    public static String host1, hostfile, /*host,*/
            Banner, Mrectcon, Inter, Bannercon, Intercon, Mrect, startappads,dmopen;
    public static String username, fbid;

    public static String  host="http://192.168.8.114/fmovie/v8serverCost/";

    public static String moviephoto;
    public static String saveimage_setting = "v7";
    public static String seriesrcoverphoto;
    public static String seriesrphoto = hostfile + "api/series/";
    public static String adsurl = hostfile + "api/ads/";

    public static String apikey = "p";

    public static String appplaystrore = ""; //rate me
    public static String facebookpage = "";
    public static String facebookgroup = "";

    public static String usermovie = "base64fmgogoal";

    public static String datalocation_movie = Environment.getExternalStorageDirectory() + "/fmovie/movie/";
    public static String datalocation_scover = Environment.getExternalStorageDirectory() + "/fmovie/scover/";
    public static String datalocation_ads = Environment.getExternalStorageDirectory() + "/fmovie/ads/";
    public static String datalocation_series = Environment.getExternalStorageDirectory() + "/fmovie/series/";
    public static String Offialfolder = Environment.getExternalStorageDirectory() + "/fmovie/";
    public static String downloadfolder = Environment.getExternalStorageDirectory() + "/fmovie/downloadManager/";
    public static String DM_downloadfolder = "/fmovie/downloadManager/";
    public static String versionanme = "8.3.0";
    public static String testdev ="123";//"85451CDC21D2DDCB4A5595B8B1CB9695";
    public static String unistallapp="kgg.syriam.fmovie82";

    public static String mopubBanner="8890fce65de148d9a0fe04d06f28bd3d";//"b195f8dd8ded45fe847ad89ed1d016da";//"8890fce65de148d9a0fe04d06f28bd3d";
    public static String mopubInter="51409b44d96c4104ac8fccdf4fb6facc";//"24534e1901884e398f1253216226017e";//"51409b44d96c4104ac8fccdf4fb6facc";
    public static String mopubRect="7ddac138469c4daab0317b9e65c1411e";//"252412d5e9364a05ab77d9396346d73d";//"7ddac138469c4daab0317b9e65c1411e";


    public static String cleanstring(String title, String status) {

        if (status.equals("1")) {

            title = Encode_Decoder.decoding_string(title);
        }


        return title;
    }

    public static void generateapi(AppCompatActivity ac) {


        try {

            PackageInfo info = ac.getPackageManager().getPackageInfo(ac.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                apikey = sign;

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }

    public static void seturl(String hostv) {
        host = hostv;
       // host="http://192.168.8.114/fmovie/v8serverCost/";


    }





    public static boolean bannershow() {

        if (Bannercon.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean intershow() {

        if (Intercon.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Mrecshow() {

        if (Mrectcon.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Isdmopen() {

        if (dmopen.equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    public static String md5(String s) {


        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }


    private static String getBytesToMBString(long bytes) {
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }


    public static String getmb(long downloadedLength) {
        return getBytesToMBString(downloadedLength);
    }

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public static String getDownloadPerSize(long finished, long total) {
        return DF.format((float) finished / (1024 * 1024)) + "M/" + DF.format((float) total / (1024 * 1024)) + "M";
    }

    public static void temp_fun() {

        hostfile = "https://raw.githubusercontent.com/goldyonwar/api/master/";
        moviephoto = hostfile + "api/Movie/";
        seriesrcoverphoto = hostfile + "api/scover/";
        adsurl = hostfile + "api/ads/";

    }

    public static void shutdownads() {
        if (GoldCon()) {
            Bannercon = "0";
            Intercon = "0";
            startappads = "0";
            Mrectcon="0";
            dmopen="0";
        }
    }

    public static void serverdataparse(String s) {

        Banner = Jsonparser.getonestring(s, "banner");
        Inter = Jsonparser.getonestring(s, "inter");
        Bannercon = Jsonparser.getonestring(s, "bannercon");
        Intercon = Jsonparser.getonestring(s, "intercon");


        String code = Jsonparser.getonestring(s, "code");
        String codetype = Jsonparser.getonestring(code, "code");
        username = Jsonparser.getonestring(code, "username");
        fbid = Jsonparser.getonestring(code, "fbid");

        facebookgroup = Jsonparser.getonestring(s, "group");
        facebookpage = Jsonparser.getonestring(s, "page");

        dmopen= Jsonparser.getonestring(s, "dmopen");

        shutdownads();

    }


    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(AppCompatActivity ac, Bitmap smallBitmap, int radius) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(ac);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    public static String fbimg_url(String fbid) {

        String fburl = "https://graph.facebook.com/" + fbid + "/picture?type=small";
        return fburl;
    }

    public static void sharetofacebook(AppCompatActivity ac, Bitmap image) {

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();


        ShareDialog shareDialog = new ShareDialog(ac);
        //  printHashKey();
      /*  ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                .build();*/
        shareDialog.show(ac, content);
    }

    public static Bitmap Sceenshotview(View temrss) {
        View v = temrss;
        v.setDrawingCacheEnabled(true);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache(true);
        Bitmap shareimage = Bitmap.createBitmap(v.getDrawingCache());
        return shareimage;
    }

    public static void Savetosdcard(final Activity ac, String imgurl, final String mid, String type) {

        String storage = "";
        if (type.equals("M")) {
            storage = Constant.datalocation_movie;
        } else {
            storage = Constant.datalocation_scover;
        }

        final String finalStorage = storage;
        Picasso.with(ac).load(imgurl).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                File file = new File(finalStorage + mid + ".fmovie");

                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                    byte[] byteArray = outstream.toByteArray();

                    stream.write(byteArray);
                    stream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });


    }

    public static void setfbuser(String fbname, String fbid1) {
        username = fbname;
        fbid = fbid1;

    }

    public static void imgeng(String scheck) {
        saveimage_setting = scheck;
    }

    public static void Createfolder() {
        File f = new File(Constant.Offialfolder);
        if (!f.exists()) {
            f.mkdir();
        }

        File datalocation_movie = new File(Constant.datalocation_movie);
        if (!datalocation_movie.exists()) {
            datalocation_movie.mkdir();
        }

        File datalocation_scover = new File(Constant.datalocation_scover);
        if (!datalocation_scover.exists()) {
            datalocation_scover.mkdir();
        }

        File datalocation_ads = new File(Constant.datalocation_ads);
        if (!datalocation_ads.exists()) {
            datalocation_ads.mkdir();
        }

        File downloadfolder = new File(Constant.downloadfolder);
        if (!downloadfolder.exists()) {
            downloadfolder.mkdir();
        }

    }

    public static String Golduser;

    public static void setusergold(String golduser) {
        Golduser = golduser;
    }

    public static boolean GoldCon() {
        boolean b = false;
        if (!Golduser.equals("0")) {
            b = true;
        }
        return b;

    }

    public static String mill_to_day() {
        long timeInMilliSeconds = Long.parseLong(Constant.Golduser);
        long seconds = timeInMilliSeconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String time = days + " day : " + hours % 24 + " hr";
        String alert = "( Gold Time : " + time + " )";
        return alert;
    }

    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }

    public static boolean IsShowStartappads() {
        if (startappads.equals("1")){
            return true;
        }

        return false;
    }

    public static void SetFakedata(String s) {
        Banner = Jsonparser.getonestring(s, "banner");
        Inter = Jsonparser.getonestring(s, "inter");
    }
}
