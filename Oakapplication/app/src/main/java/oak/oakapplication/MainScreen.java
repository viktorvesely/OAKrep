package oak.oakapplication;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    private DatabaseReference mRootRef;
    private DatabaseReference mPostRef;
    private ChildEventListener mPostListener;
    private ArrayList<Post> mPostsToShow;
    private PostArrayAdapter adapter;
    private OakappMain main;
    private MainScreen selfPointer;
    private ListView mPostsListView;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mFirebaseAuth;
    private static int RC_SIGN_IN = 1;

    private Button mButtonSignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selfPointer = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPost = new Intent(selfPointer, PostsActivity.class);
                startActivity(newPost);
            }
        });

        main = ((OakappMain)getApplicationContext());
        mPostsToShow = new ArrayList<Post>();
        adapter = new PostArrayAdapter(this,mPostsToShow);

        mPostsListView = (ListView) findViewById(R.id.lv_listOfPosts);
        mPostsListView.setAdapter(adapter);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mPostRef = mRootRef.child("Posts");
        mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                onSignedInInit(user.getDisplayName());
                }
                else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

        mButtonSignOut = (Button) findViewById(R.id.b_signOut);
        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(selfPointer);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
        detachDatabaseReadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void onSignedInInit(String userName) {
        main.user.mUsername = userName;
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanUp () {
        main.user.mUsername = "anonymous";
        adapter.clear();
    }

    private void attachDatabaseReadListener() {
        if (mPostListener != null) {
            //is already attached
            return;
        }
        mPostListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                mPostsToShow.add(post);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //on error
            }

        };
        mPostRef.addChildEventListener(mPostListener);
    }

    private void detachDatabaseReadListener() {
        if (mPostListener != null) {
            mPostRef.removeEventListener(mPostListener);
            mPostListener = null;
        }
    }



}
