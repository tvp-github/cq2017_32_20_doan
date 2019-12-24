package hcmus.android.lighttour.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hcmus.android.lighttour.AppUtils.ListReview;
import hcmus.android.lighttour.R;
import hcmus.android.lighttour.Response.Review;

public class ListReviewAdapter extends ArrayAdapter {
    Activity context;
    int layout;
    ArrayList<Review> listReview;
    public ListReviewAdapter(Activity context, int layout, ArrayList<Review> listReview) {
        super(context,layout,listReview);
        this.context = context;
        this.layout = layout;
        this.listReview = listReview;
    }



    @Override
    public int getCount() {
        Log.d("FFF", "getCount: "+ listReview.size());
        return listReview.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txtReview;
        TextView txtName;
        ImageView imgAvatar;
        RatingBar ratingBar;
        //Khởi tạo
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        //Ánh xạ
        txtName = view.findViewById(R.id.review_name);
        ratingBar = view.findViewById(R.id.review_ratingbar);
        txtReview = view.findViewById(R.id.review);
        imgAvatar = view.findViewById(R.id.review_avatar);
        //Đổ dữ liệu

        Review review = listReview.get(i);
        if(review.getAvatar()!=null && review.getAvatar().length()>0)
            Glide.with(context).load(listReview.get(i).getAvatar()).into(imgAvatar);
        txtName .setText(review.getName());
        ratingBar.setRating(review.getPoint() );
        txtReview.setText(review.getReview());
        Log.d("RRR", "getView: "+ review.getReview() );
        return view;
    }


}
