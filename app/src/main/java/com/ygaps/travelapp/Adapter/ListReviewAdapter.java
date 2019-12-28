package com.ygaps.travelapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.ygaps.travelapp.APIService.SendReportReviewService;
import com.ygaps.travelapp.MyApplication;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.Message;
import com.ygaps.travelapp.Response.Review;
import com.ygaps.travelapp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListReviewAdapter extends ArrayAdapter implements Filterable {
    Activity context;
    int layout;
    ArrayList<Review> listReview;
    Review review;
    SendReportReviewService sendReportReviewService;
    String token;
    int reviewId;
    ImageButton btnReport;
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
        final TextView txtReview;
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
        review = listReview.get(i);
        if(review.getAvatar()!=null && review.getAvatar().length()>0)
            Glide.with(context).load(listReview.get(i).getAvatar()).into(imgAvatar);
        txtName .setText(review.getName());
        ratingBar.setRating(review.getPoint() );
        txtReview.setText(review.getReview());
        Log.d("RRR", "getView: "+ review.getReview() );

        btnReport = view.findViewById(R.id.btn_report);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayReportDialog(Integer.parseInt(review.getId()),true);
            }

            private void displayReportDialog(Integer id, boolean b) {

                reviewId= id;
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = inflater.inflate(R.layout.review_detail, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Review Detail");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final Button Report = alertLayout.findViewById(R.id.item_report);
                Report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        View alertLayout = inflater.inflate(R.layout.report_dialog, null);

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Do you want to report this review?");
//                        alert.setView(alertLayout);
                        alert.setCancelable(false);
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendReportReviewService = ApiUtils.getSendReportReviewService();
                                MyApplication myApplication = (MyApplication) context.getApplication();
                                token = myApplication.getToken();
                                sendReportReviewService.sendData(token, reviewId).enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        Log.d("AAA", "onResponse: "+response.code() + response.body().toString());
                                        if(response.code()==200){
                                            Toast.makeText(context,"Report successful",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {

                                    }
                                });
                            }
                        });

                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }


        });
        return view;
    }


}
