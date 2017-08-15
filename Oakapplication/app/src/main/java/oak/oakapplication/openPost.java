package oak.oakapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class openPost extends AppCompatActivity {

    private Post mPost;
    OakappMain main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_post);

        main = (OakappMain) getApplicationContext();
        Intent intent = getIntent();
        mPost = main.postsToShow.get(intent.getIntExtra("id",0));

    }
}
