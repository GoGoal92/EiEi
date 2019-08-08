package market.goldandgo.videonew1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.phoneid;
import market.goldandgo.videonew1.fancydialoglib.Dialog_controller;
import multithreaddownload.DownloadConfiguration;
import multithreaddownload.DownloadManager;
import pub.devrel.easypermissions.EasyPermissions;

public class Splash extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    static AppCompatActivity ac;
    static boolean firsttime = false;
    TextView loading;

    @Override
    protected void onResume() {
        super.onResume();
        firsttime = false;
    }


    static String hashKey;

    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(10);
        configuration.setThreadNum(10);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        Firebase.setAndroidContext(getApplicationContext());

        setContentView(R.layout.splash);
        ac = this;
        Constant.temp_fun();
        phoneid p = new phoneid(ac);
        hashKey = printHashKey();
        Constant.generateapi(this);

        if (appInstalledOrNot()) {

            Dialog_controller.mustunistall(ac);
        } else {
            checkStoragePermission();
        }

        initDownloader();
        // MyRequest.checkversion();
        loading = (TextView) findViewById(R.id.loading);
        TextView vname = (TextView) findViewById(R.id.vname);
        vname.setText("V " + Constant.versionanme);

        SharedPreferences prefs = ac.getSharedPreferences("imgeng",
                Context.MODE_PRIVATE);
        String scheck = prefs.getString("eng", null);
        if (scheck == null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("eng", "v7");
            editor.commit();
            scheck = "v7";
        }

        Constant.imgeng(scheck);


    }

    private boolean appInstalledOrNot() {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(Constant.unistallapp, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UNINSTALL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                checkStoragePermission();
            } else {
                Dialog_controller.mustunistall(ac);
            }
        }

        if (requestCode == 2) {

            Feedback(json);

        }

    }

    static int UNINSTALL_REQUEST_CODE = 1;


    public static void Unistall() {
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + Constant.unistallapp));
            intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
            ac.startActivityForResult(intent, UNINSTALL_REQUEST_CODE);
        } catch (Exception e) {
            Log.e("errror", e.toString());
        }

        // ac.finish();
    }

    String TAG = "facebook";

    public String printHashKey() {
        String hashKey = "0";
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
        return hashKey;
    }


    private void checkStoragePermission() {
        String[] per = new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(ac, per)) {
            // Already have permission, do the thing
            Constant.Createfolder();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Setfirebase();


                }
            }, 2000);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "All Permission Must Grant", 200, per);
        }
    }

    private void Setfirebase() {
        Firebase firebase = new Firebase(Constant.FIREBASE_APP);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!firsttime) {
                    HashMap<String, String> valuee = (HashMap) dataSnapshot.getValue();
                    String host = valuee.get("v8_url");

                    Constant.seturl(host);
                    Log.e("myhost", host);

                   MyRequest.checkversion(hashKey);
                    loading.setText("Checking App Version (" + Constant.versionanme + ")");
                    firsttime = true;
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        Constant.Createfolder();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Setfirebase();
            }
        }, 2000);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        Toast.makeText(ac, "Permission Needed", Toast.LENGTH_SHORT).show();
        ac.finish();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {

            Constant.Createfolder();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Setfirebase();


                }
            }, 2000);
        }

    }


    private static boolean appInstalledOrNot_hackapp(String pack) {
        PackageManager pm = ac.getPackageManager();
        try {
            pm.getPackageInfo(pack, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    static String json;

    public static void Feedback(String s) {
        json = s;
        String ustatus = Jsonparser.getonestring(s, "ustatus");
        Log.e("ustatus", ustatus);
        if (ustatus.equals("1")) {
            String upackage = Jsonparser.getonestring(s, "upackage");
            Log.e("upackage", upackage);
            if (appInstalledOrNot_hackapp(upackage)) {
                String umsg = Jsonparser.getonestring(s, "umsg");
                Log.e("umsg", umsg);
                Dialog_controller.mustunistall_hackapp(ac, umsg, upackage);

            } else {
                Status_method(s);
            }


        } else {
            Status_method(s);
        }


    }

    private static void Status_method(String s) {
        Log.e("Myhjson", s);

        String golduser = Jsonparser.getonestring(s, "golduser");
        Constant.setusergold(golduser);

        String status = Jsonparser.getonestring(s, "status");
        if (status.equals("0")) {

            String code = Jsonparser.getonestring(s, "code");
            String codetype = Jsonparser.getonestring(code, "code");
            if (codetype.equals("1")) {

                Constant.serverdataparse(s);

                Intent it = new Intent(ac, MainActivity.class);
                ac.startActivity(it);
                ac.finish();


            } else {
                Dialog_controller.showbandialog(ac);
            }


        } else if (status.equals("1")) {
            String msg = Jsonparser.getonestring(s, "msg");
            Dialog_controller.showdialog(ac, status, msg, "");

        } else if (status.equals("2")) {
            String code = Jsonparser.getonestring(s, "code");
            String codetype = Jsonparser.getonestring(code, "code");
            if (codetype.equals("1")) {
                Constant.serverdataparse(s);
                String msg = Jsonparser.getonestring(s, "msg");
                String url = Jsonparser.getonestring(s, "url");
                Dialog_controller.showdialog(ac, status, msg, url);
            } else {
                Dialog_controller.showbandialog(ac);
            }


        } else if (status.equals("3")) {
            String msg = Jsonparser.getonestring(s, "msg");
            String url = Jsonparser.getonestring(s, "url");
            Dialog_controller.showdialog(ac, status, msg, url);

        } else if (status.equals("4")) {

            String code = Jsonparser.getonestring(s, "code");
            String codetype = Jsonparser.getonestring(code, "code");
            if (codetype.equals("1")) {
                Constant.serverdataparse(s);
                String msg = Jsonparser.getonestring(s, "msg");
                Dialog_controller.showdialog(ac, status, msg, "");
            } else {
                Dialog_controller.showbandialog(ac);
            }


        }

    }

    public static void Feedback_Error() {
        Dialog_controller.reload(ac);
    }

    public static void reload() {
        MyRequest.checkversion(hashKey);
    }

    public static void Unistall_hack(String upackage) {
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + upackage));
            intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
            ac.startActivityForResult(intent, 2);
        } catch (Exception e) {
            Log.e("errror", e.toString());
        }
    }
}
