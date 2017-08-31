package oak.oakapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class openPost extends AppCompatActivity {

    private Post mPost;
    private User mOwner;

    private OakappMain main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_post);

        main = (OakappMain) getApplicationContext();
        Intent intent = getIntent();
        mPost = OakappMain.postsToShow.get(intent.getIntExtra("id",0));
        OakappMain.getUserByUid(mPost.mOwner, new UserInterface() {
            @Override
            public void UserListener(User u) {
                mOwner = u;
                //also draw him
            }
        });




    }
}
