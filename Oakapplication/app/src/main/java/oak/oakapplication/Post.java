package oak.oakapplication;

import android.nfc.Tag;

import java.util.List;

/**
 * Created by viktor on 31.7.2017.
 */

public class Post {

    public Post() { }

    public Post (String postText, String title ,String ownerOfPost, String ImgURL, String ImgURL2, String Tags, long Category, double Latitude, double Longitude) {
        this.mText = postText;
        this.mTitle = title;
        this.mOwner = ownerOfPost;
        this.mImgUrl1 = ImgURL;
        this.mImgUrl2 = ImgURL2;
        this.mTags = Tags;
        this.mCategory = Category;
        this.mLatitude = Latitude;
        this.mLongitude = Longitude;

        this.mReputation = defaultRep;
        this.mTimestamp = System.currentTimeMillis();
        this.mLastActivity = System.currentTimeMillis();
        this.mLinkToComments = " ";
        this.mActive = true;
    }


    //private List<Comment> mComments; in a future
    //user set
    public String mText;
    public String mTitle;
    public String mOwner;
    public String mImgUrl1;
    public String mImgUrl2;
    public String mTags;
    public long mCategory;
    public double mLatitude;
    public double mLongitude;
    //generated
    public int mReputation;
    public long mTimestamp;
    public long mLastActivity;
    public boolean mActive;

    //may change in future
    public String mLinkToComments;

    //static
    private static final int defaultRep = 0;
}
