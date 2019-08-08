package market.goldandgo.videonew1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import market.goldandgo.videonew1.Fragment.Fragment_menu;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;

import java.util.Arrays;

public class FacebookLogin extends AppCompatActivity {

    static CallbackManager callbackManager;
    static AppCompatActivity ac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        ac=this;
        setContentView(R.layout.facebook_login_layout);

        callbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().logInWithReadPermissions(ac, Arrays.asList( "email"));
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>()
                    {
                        private ProfileTracker mProfileTracker;
                        @Override
                        public void onSuccess(LoginResult loginResult)
                        {
                            if(Profile.getCurrentProfile() == null) {
                                mProfileTracker = new ProfileTracker() {
                                    @Override
                                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                        // profile2 is the new profile
                                        displayMessage(profile2);
                                        //       Log.v("facebook - profile", profile2.getFirstName());
                                        mProfileTracker.stopTracking();
                                    }
                                };
                                // no need to call startTracking() on mProfileTracker
                                // because it is called by its constructor, internally.
                            }
                            else {
                                Profile profile = Profile.getCurrentProfile();
                                displayMessage(profile);
                                //     Log.v("facebook - profile", profile.getFirstName());
                            }
                        }

                        @Override
                        public void onCancel()
                        {
                            ac.finish();
                            Toast.makeText(ac,"Cancel Login",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FacebookException exception)
                        {
                            if (exception instanceof FacebookAuthorizationException) {
                                if (AccessToken.getCurrentAccessToken() != null) {
                                    LoginManager.getInstance().logOut();
                                }
                            }

                            //Log.e("facebooklogin error",exception.toString());
                            ac.finish();
                            Toast.makeText(ac,"Pls Login again,You logged in as different Facebook user",Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    static String fbname, fbid;

    private static void displayMessage(Profile profile) {
        if (profile != null) {
            fbname = profile.getName();
            fbid = profile.getCurrentProfile().getId();

            Constant.setfbuser(fbname,fbid);

            try{
                Detail_recycler.refresh();
            }catch (Exception e){

            }

            try{
                Series_Detail_recycler.refresh();
            }catch (Exception e){

            }

            MyRequest.updateuserfb(fbname,fbid);

        } else {
            ac.finish();
            Toast.makeText(ac,"Facebook denied to give profile",Toast.LENGTH_SHORT).show();
        }
    }


    public static void Feedback(String s) {
        ac.finish();
        Toast.makeText(ac,"Login Successful",Toast.LENGTH_SHORT).show();
        String Gold= Jsonparser.getonestring(s,"gold");
        Constant.setusergold(Gold);
        Constant.shutdownads();
        Fragment_menu.setfbimage();
    }

    public static void Feedback_Error() {
        ac.finish();
        Toast.makeText(ac,"Network Fail",Toast.LENGTH_SHORT).show();
    }
}
