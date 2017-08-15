package oak.oakapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import oak.oakapplication.Post;

/**
 * Created by viktor on 9.8.2017.
 */

public class PostArrayAdapter extends ArrayAdapter<Post> {
    public PostArrayAdapter (Context context, ArrayList<Post> posts) {
        super(context,0, posts);

    }

    private TextView mTitle;
    private TextView mText;
    private ImageView mImage;

    private TextView mNoImgTitle;
    private TextView mNoImgText;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
        }

        mTitle = (TextView) convertView.findViewById(R.id.tv_titlePost);
        mText = (TextView) convertView.findViewById(R.id.tv_textPost);
        mImage = (ImageView) convertView.findViewById(R.id.iv_postimg);

        mNoImgTitle = (TextView) convertView.findViewById(R.id.tv_noImgTitle);
        mNoImgText = (TextView) convertView.findViewById(R.id.tv_noImgText);

        if (post.mImgUrl1 == null && post.mImgUrl2 == null) {
            mTitle.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mText.setVisibility(View.GONE);
            mTitle.setText(post.mTitle);
            mText.setText(post.mText);
        }
        else {
            mNoImgText.setVisibility(View.GONE);
            mNoImgTitle.setVisibility(View.GONE);
            mNoImgTitle.setText(post.mTitle);
            mNoImgText.setText(post.mText);
        }


        if (post.mImgUrl1 != null) {
            Glide.with(mImage.getContext()).load(post.mImgUrl1).into(mImage);
        }
        else if (post.mImgUrl2 != null) {
            Glide.with(mImage.getContext()).load(post.mImgUrl2).into(mImage);
        }



        return convertView;
    }
}
