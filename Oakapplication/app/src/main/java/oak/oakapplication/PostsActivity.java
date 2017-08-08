package oak.oakapplication;

import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.toIntExact;

public class PostsActivity extends AppCompatActivity {

    private DatabaseReference mRootRef;
    private DatabaseReference postRef;
    private Geocoder geocoder;

    private Button mPostButton;
    private Button mAddImage1;
    private Button mAddImage2;
    private EditText mPostText;
    private EditText mTags;
    private EditText mAddress;
    private String[] mArraySpinner;
    private Spinner mCategories;


    private final static int MaxTagsPerPost = 3;
    private final String[] categories = {"Problemy","V procese", "Vyriesene"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        postRef = mRootRef.child("Posts");

        mPostButton = (Button) findViewById(R.id.b_post);
        mPostText = (EditText) findViewById(R.id.et_postText);
        mAddress = (EditText) findViewById(R.id.et_location);
        mCategories = (Spinner) findViewById(R.id.sp_categories);
        mAddImage1 = (Button) findViewById(R.id.b_addImg1);
        mAddImage2 = (Button) findViewById(R.id.b_addImg2);
        mTags = (EditText) findViewById(R.id.et_tags);

        mArraySpinner = categories;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, mArraySpinner);
        mCategories.setAdapter(adapter);

        geocoder = new Geocoder(this);


        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> addresses = null;
                double longitude = 0;
                double latitude = 0;

                String tags = mTags.getText().toString();
                int numberOfTags = 0;

                for (int i = 0; i < tags.length(); ++i)
                {
                    if (tags.charAt(i) == ','){
                        numberOfTags++;
                    }
                }

                if (numberOfTags > MaxTagsPerPost){
                    Snackbar.make(v, "Maximalne 4 tagy", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                try {
                    addresses = geocoder.getFromLocationName(mAddress.getText().toString(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses.size() > 0) {
                        latitude= addresses.get(0).getLatitude();
                        longitude= addresses.get(0).getLongitude();
                }
                Post post = new Post(mPostText.getText().toString(), "anonymous", " ", " ", mTags.getText().toString(),mCategories.getSelectedItemId(), latitude, longitude);
                postRef.push().setValue(post);
            }
        });
    }
}
