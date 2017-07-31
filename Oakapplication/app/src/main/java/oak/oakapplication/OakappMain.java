package oak.oakapplication;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by viktor on 17.7.2017.
 */

public class OakappMain extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
