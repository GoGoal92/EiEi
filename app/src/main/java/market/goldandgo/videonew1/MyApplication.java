package market.goldandgo.videonew1;

/**
 * Created by Go Goal on 12/16/2016.
 */

import android.app.Application;
import android.content.Context;

import multithreaddownload.DownloadConfiguration;
import multithreaddownload.DownloadManager;

import com.facebook.ads.AudienceNetworkAds;
import com.firebase.client.Firebase;

import market.goldandgo.videonew1.Utils.CrashHandler;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static Context sContext;

    public MyApplication() {
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing firebase

        Firebase.setAndroidContext(getApplicationContext());
        AudienceNetworkAds.initialize(this);

        mInstance = this;

        sContext = getApplicationContext();


        CrashHandler.getInstance(sContext);

    }



    public static Context getContext() {
        return sContext;
    }

}