package oak.oakapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Login extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mPostDatabaseRefernce;

    private Button mSendData;
    private EditText mEmail;
    private EditText mPass;
    private Firebase mRootRef;
    private Button mPassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRootRef = new Firebase("https://oakomunita-a34ef.firebaseio.com/Users");
        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mPostDatabaseRefernce = mFirebaseDataBase.getReference().child("posts");

        mSendData = (Button) findViewById(R.id.b_addUser);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPass = (EditText) findViewById(R.id.et_password);
        mPassButton = (Button) findViewById(R.id.b_pass);


        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final Firebase mRefChildListen = mRootRef.child("User");
        mRefChildListen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map <String, String> userInfo = dataSnapshot.getValue(Map.class);
                Log.v("E_VALUE", "User :" + userInfo);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase mRefChild = mRefChildListen;
                Firebase fEmail = mRefChild.child("Email");
                Firebase fPass = mRefChild.child("Password");

                String Pass = mPass.getText().toString();
                String Email = mEmail.getText().toString();

                fEmail.setValue(Email);
                fPass.setValue(Pass);
            }
        });
    }

    public void forceToStart (View view) {
        Intent startApp = new Intent(this, PostsActivity.class);
        startActivity(startApp);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
