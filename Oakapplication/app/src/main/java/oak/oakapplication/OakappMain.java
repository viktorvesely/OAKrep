package oak.oakapplication;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by viktor on 17.7.2017.
 */

public class OakappMain extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        firebaseUser = null;
        selfPointer = this;
        postsToShow = new ArrayList<Post>();
    }

    static String getRankFromLevel(int level)  { return selfPointer.getString(R.string.default_rank +  level); }

    static int getRankLevelFromRep(int reputation) {

        for (int i = 0; i < RANK_BORDERS.length; ++i)
        {
            if (reputation < RANK_BORDERS[i])
                return i;
        }

        return -1;
    }

    public ArrayList<Post> postsToShow;
    public User user;
    public FirebaseUser firebaseUser;

    public static OakappMain selfPointer;
    public static final int MAXRANKLEVEL = 30;

    private static int[] RANK_BORDERS = {100,200,300,400,500,600};
}
