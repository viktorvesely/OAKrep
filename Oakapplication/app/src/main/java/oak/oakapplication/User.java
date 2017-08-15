package oak.oakapplication;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

/**
 * Created by viktor on 8.8.2017.
 */

public class User {

    User () {
        mUsername = "";
        mReputation = 0;
        mLevel = 0;
        mRank = OakappMain.getRankFromLevel(mLevel);
    }

    public String mUsername;
    public int mReputation;
    public boolean mActive;
    public boolean mAdmin;
    public ArrayList<Post>mOwnPosts;
    public ArrayList<Post>mFavoritePosts;

    //generated;
    private String mRank;
    private int mLevel;

    public void SetLevel(int level) {mLevel = level;}
    public int Level() {return mLevel;}
    public void SetRank (String rank) {mRank = rank;}
    public String Rank() {return mRank;}

    public Location GetUserCurrentLocation() {
        Location current = null;
        if (HasInternetAcces() == false) {
            return current;
        }


        return current;
    }

    public boolean HasInternetAcces () {
        boolean yes;
        ConnectivityManager connectivityManager = (ConnectivityManager)OakappMain.selfPointer.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            yes = true;
        }
        else {yes = false;}
        return yes;
    }
}
