package oak.oakapplication;

/**
 * Created by matus on 3.8.2017.
 */

public class Comment {

    public Comment() { }

    public Comment (String comText, String comOwner, String motherPost, boolean direct_msg){
        mComText = comText;
        mComOwner = comOwner;
        mMotherPost = motherPost;
        mDirectmsg = direct_msg;

        mVisiblity = true;
        mComTimestamp = System.currentTimeMillis();
        mPlus = 0;
        mMinus = 0;
    }

    //user set
    public String mComText;
    public String mComOwner;
    public String mMotherPost;
    public boolean mDirectmsg;

    //generated
    public long mComTimestamp;
    public int mPlus;
    public int mMinus;
    public boolean mVisiblity;

}
