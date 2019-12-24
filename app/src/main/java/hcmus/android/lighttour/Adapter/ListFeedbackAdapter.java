package hcmus.android.lighttour.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hcmus.android.lighttour.APIService.SendReportFeedbackService;
import hcmus.android.lighttour.APIService.SendReportReviewService;
import hcmus.android.lighttour.MyApplication;
import hcmus.android.lighttour.R;
import hcmus.android.lighttour.Response.Feedback;
import hcmus.android.lighttour.Response.Message;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFeedbackAdapter extends ArrayAdapter {
        Activity context;
        int layout;
        ArrayList<Feedback> listFeedback;
        ImageButton btnReport;
        Feedback feedback;
        SendReportFeedbackService sendReportFeedbackService;
        String token;
        int feedbackId;
        public ListFeedbackAdapter(Activity context, int layout, ArrayList<Feedback> listFeedback) {
            super(context,layout,listFeedback);
            this.context = context;
            this.layout = layout;
            this.listFeedback = listFeedback;
        }



        @Override
        public int getCount() {
            Log.d("FFF", "getCount: "+ listFeedback.size());
            return listFeedback.size();
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
            final TextView txtFeedback;
            TextView txtName;
            ImageView imgAvatar;
            RatingBar ratingBar;
            //Khởi tạo
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            //Ánh xạ
            txtName = view.findViewById(R.id.fb_name);
            ratingBar = view.findViewById(R.id.fb_ratingbar);
            txtFeedback = view.findViewById(R.id.fb_fb);
            imgAvatar = view.findViewById(R.id.fb_avatar);
            //Đổ dữ liệu

            feedback = listFeedback.get(i);
            if(feedback.getAvatar()!=null && feedback.getAvatar().length()>0)
                Glide.with(context).load(listFeedback.get(i).getAvatar()).into(imgAvatar);
            txtName .setText(feedback.getName());
            ratingBar.setRating(Integer.parseInt(feedback.getPoint()) );
            txtFeedback.setText(feedback.getFeedback());
            Log.d("RRR", "getView: "+ feedback.getFeedback() );

            btnReport = view.findViewById(R.id.btn_report);
            btnReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayReportDialog(feedback.getId(),true);
                }

                private void displayReportDialog(Integer id, boolean b) {

                    final int feedbackId= id;
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View alertLayout = inflater.inflate(R.layout.feedback_detail, null);

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Feedback Detail");
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
                            alert.setTitle("Do you want to report this feedback?");
//                          alert.setView(alertLayout);
                            alert.setCancelable(false);
                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendReportFeedbackService = ApiUtils.getSendReportFeedbackService();
                                    MyApplication myApplication = (MyApplication) context.getApplication();
                                    token = myApplication.getToken();
                                    sendReportFeedbackService.sendData(token, feedbackId).enqueue(new Callback<Message>() {
                                        @Override
                                        public void onResponse(Call<Message> call, Response<Message> response) {
                                            Log.d("AAA", "onResponse: "+response.code());
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
