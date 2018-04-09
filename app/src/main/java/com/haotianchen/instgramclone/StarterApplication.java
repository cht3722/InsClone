package com.haotianchen.instgramclone;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by haotianchen on 2018/4/6.
 */

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("92fc10ffd7eddca2adb53898d9b66332c24f0279")
                .clientKey("7f164b8a84f2869827461ffd9d5cb1ee4b9196ae")
                .server("http://54.186.45.154:80/parse/")
                .build()
        );






        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }



}
