package oak.oakapplication;

import android.app.Application;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.LocationListener;
import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viktor on 17.7.2017.
 */

public class OakappMain extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        firebaseUser = null;
        selfPointer = this;
        postsToShow = new ArrayList<Post>();
        remoteConfig = FirebaseRemoteConfig.getInstance();
        loacationProvider = LocationServices.getFusedLocationProviderClient(this);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location l)
            {
                OakappMain.lastLocation = l;
            }
        };

        mLocationManager = new LocationRequest();

        mLocationManager.setInterval(LOCATION_REFRESH_TIME);
        mLocationManager.setSmallestDisplacement(LOCATION_REFRESH_DISTANCE);


    }

    public static FirebaseRemoteConfig remoteConfig;

    static String getRankFromLevel(int level)  { return selfPointer.getString(R.string.default_rank +  level); }

    static int getRankLevelFromRep(int reputation) {

        for (int i = 0; i < rankBorders.size(); ++i)
        {
            if (reputation < rankBorders.get(i))
                return i;
        }

        return -1;
    }


    public static void getUserByUid(String uid,final UserInterface ui)
    {
        Query filter =  FirebaseDatabase.getInstance().getReference().child("Users").orderByKey().equalTo(uid);


        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                ui.UserListener(u);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void getPostByKey(final String id, final UserInterface ui)
    {
        Query filter = FirebaseDatabase.getInstance().getReference().child("Posts").orderByKey().equalTo(id);

        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post p = dataSnapshot.getValue(Post.class);
                ui.PostListener(p);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
;    }

    public static void getCommentByKey(final String id, final UserInterface ui)
    {
        Query filter = FirebaseDatabase.getInstance().getReference().child("Comments").orderByKey().equalTo(id);

        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Comment c = dataSnapshot.getValue(Comment.class);
                ui.CommentListener(c);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public static boolean HasInternetAcces() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    public static void SaveUserByUid(final User user) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(user.mId).setValue(user);
    }

    public static void SavePostByKey(final Post post) {
        FirebaseDatabase.getInstance().getReference().child("Posts").child(post.mKey).setValue(post);
    }

    public static void SaveCommentByKey(final Comment comment) {
        FirebaseDatabase.getInstance().getReference().child("Comments").child(comment.mKey).setValue(comment);
    }


    public static LocationListener locationListener;
    public static Location lastLocation;
    private static FusedLocationProviderClient loacationProvider;
    public static LocationRequest mLocationManager;
    private static final float LOCATION_REFRESH_DISTANCE = 100;
    private static final long LOCATION_REFRESH_TIME = 1000 * 60 *1;


    public static ArrayList<Post> postsToShow;
    public static User user;
    public static FirebaseUser firebaseUser;

    public static OakappMain selfPointer;
    public static final int MAXRANKLEVEL = 30;

    public static List<Long> rankBorders;
}
