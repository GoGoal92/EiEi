package market.goldandgo.videonew1.Object;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by Go Goal on 12/7/2016.
 */

public class phoneid {

    static String id,dmodel;
    public phoneid(Context ac) {
       String did = Settings.Secure.getString(ac.getContentResolver(), Settings.Secure.ANDROID_ID);
        dmodel = Build.MODEL;
        id=did+dmodel;

    }

    public static String model() {
        return dmodel;
    }

    public static String getid() {
        return id;
    }

  }
