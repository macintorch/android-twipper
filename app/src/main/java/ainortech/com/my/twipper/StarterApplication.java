package ainortech.com.my.twipper;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by ainorsyahrizal on 07/12/2017.
 */

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.APPLICATION_ID))
                .clientKey(getString(R.string.CLIENT_KEY))
                .server(getString(R.string.SERVER_URL))
                .build()
        );

        // ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
