package hcmus.android.lighttour;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import hcmus.android.lighttour.APIService.GetPointStarsService;
import hcmus.android.lighttour.AppUtils.ListPointStars;
import hcmus.android.lighttour.Response.PointStars;
import hcmus.android.lighttour.Response.StopPoint;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tourinfo_tab1_general extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GetPointStarsService PointStarsService;
    private tourinfo_tab1_general.OnFragmentInteractionListener mListener;

    TextView txtPrice;
    TextView txtName;
    TextView txtDate;
    TextView txtnumpeople;
    ImageView imgAvatar;
    RatingReviews ratingReviews;
    TextView text;
    TextView ratingPoint;
    TextView numberReviewer;
    RatingBar ratingBar;
    Tour tour;
    String token;
    int total=0;
    int point=0;
    int raters[] = new int[5];
    int colors[] = new int[]{
            Color.parseColor("#0e9d58"),
            Color.parseColor("#bfd047"),
            Color.parseColor("#ffc105"),
            Color.parseColor("#ef7e14"),
            Color.parseColor("#d36259")};

    public tourinfo_tab1_general() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab1_general.
     */
    // TODO: Rename and change types and number of parameters
    public static tourinfo_tab1_general newInstance(String param1, String param2) {
        tourinfo_tab1_general fragment = new tourinfo_tab1_general();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayout rate_now = (LinearLayout) getView().findViewById(R.id.rate_now);
        rate_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent write_review_intent = new Intent(getActivity(), WriteReviewActivity.class);
                write_review_intent.putExtra("tour", tour);
                startActivity(write_review_intent );
            }
        });

        RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.rb_rate_now);

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent write_review_intent = new Intent(getActivity(), WriteReviewActivity.class);
                    write_review_intent.putExtra("tour", tour);
                    startActivity(write_review_intent);
                }
                return true;
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.tourinfo_tab1_general, container, false);
        tour= (Tour) getArguments().getSerializable("tour");
        PointStarsService = ApiUtils.getGetPointStarsService();

        //Ánh xạ
        txtPrice = view.findViewById(R.id.txtprice);
        txtName = view.findViewById(R.id.txtname);
        txtnumpeople = view.findViewById(R.id.txtnumpeople);
        txtDate = view.findViewById(R.id.txtDate);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        ratingPoint = view.findViewById(R.id.ratingPoint);
        numberReviewer = view.findViewById(R.id.numberReviewer);
        ratingBar = view.findViewById(R.id.ratingBar);

        //Đổ dữ liệu
        if(tour.getAvatar()!=null && tour.getAvatar().length()>0)
        Glide.with(this).load(tour.getAvatar()).into(imgAvatar);
        txtPrice.setText(tour.getMinCost()+" - "+tour.getMaxCost());
        txtName .setText(tour.getName());
        txtnumpeople.setText(tour.getAdults()+" Adults - "+tour.getChilds() + " Children");
        Calendar startDate, endDate;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        if(tour.getStartDate()!=null)
            startDate.setTimeInMillis(Long.parseLong(tour.getStartDate()));
        else startDate.setTimeInMillis(0);
        if(tour.getEndDate()!=null)
            endDate.setTimeInMillis(Long.parseLong(tour.getEndDate()));
        else endDate.setTimeInMillis(0);
        txtDate.setText(formatCalendar(startDate)+" - "+ formatCalendar(endDate));

        ratingReviews = (RatingReviews) view.findViewById(R.id.rating_reviews);

//        int colors[] = new int[]{
//                Color.parseColor("#0e9d58"),
//                Color.parseColor("#bfd047"),
//                Color.parseColor("#ffc105"),
//                Color.parseColor("#ef7e14"),
//                Color.parseColor("#d36259")};
//
//        int raters[] = new int[]{
//                new Random().nextInt(100),
//                new Random().nextInt(100),
//                new Random().nextInt(100),
//                new Random().nextInt(100),
//                new Random().nextInt(100)
//        };
//
//        ratingReviews.createRatingBars(100, BarLabels.STYPE1, colors, raters);

        int TourId=tour.getId();
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        token = myApplication.getToken();
        sendTourId(TourId);


        return view;
    }

    String formatCalendar(Calendar calendar){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(calendar.getTime());

        return result;
    }

    public void sendTourId(int tourId ){
        PointStarsService.sendData(token,tourId).enqueue(new Callback<ListPointStars>() {
            @Override
            public synchronized void onResponse(Call<ListPointStars> call, Response<ListPointStars> response) {
                Log.d("AAA", "onResponse: Xong cmnr"+response.code());
                if(response.code()==200){
                    List<PointStars> otherlist = response.body().getPointStars();
                    point=0;
                    total=0;
                    for (int i = otherlist.size()-1; i>=0;i--){
                        PointStars pointStars=otherlist.get(i);
                        raters[5-pointStars.getPoint()]=pointStars.getTotal();
                        Log.d("AAA", "rater: "+raters[i] +"  "+i +"  "+ pointStars.getPoint());

                        point=point + (5-i)*raters[i];
                        total=total + pointStars.getTotal();

                    }
                    Log.d("AAA", "total: "+total);
                    Log.d("AAA", "point: "+point);
//                    for (int i = 0; i<otherlist.size();i++){
//                        PointStars pointStars=otherlist.get(i);
//                        raters[pointStars.getPoint()-1]=(int) (raters[pointStars.getPoint()-1] * 100 /total);
//                    }
                    Log.d("AAA", "onResponse: "+total);
                    ratingReviews.createRatingBars(total, BarLabels.STYPE1, colors, raters);
                    if (total != 0) {
                        float ratingpoint = Math.round(((float) point/(5*total))*100)/ (float)20;
                        Log.d("AAA", "rating point: "+ratingpoint);
                        ratingPoint.setText(String.valueOf(ratingpoint));
                        ratingBar.setRating(ratingpoint);
                    }
                    else {
                        ratingPoint.setText("0");
                        ratingBar.setRating(0);
                    }
                    numberReviewer.setText(total + " reviewers");
                }
            }

            @Override
            public void onFailure(Call<ListPointStars> call, Throwable t) {
                Log.d("AAA", "onFailure: ");
            }
        });

//        TourInfoService.sendData(tourId).enqueue(new Callback<ListPointStars>) {
//            @Override
//            public void onResponse(Call<ListPointStars> call, Response<ListPointStars> response) {
//                if(response.code()==200){
//                    List<PointStars> otherlist = response.body().getStopPoints();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TourInfo> call, Throwable t) {
//
//            }
//        });
//        TourInfoService.sendData(tourId).enqueue(new Callback<ListPointStars>) {
//            @Override
//            public void onResponse(Call<ListPointStars> call, Response<ListPointStars> response) {
//                if(response.code()==200){
//                    List<PointStars> otherlist = response.body().getStopPoints();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TourInfo> call, Throwable t) {
//
//            }
//        });
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof tourinfo_tab1_general.OnFragmentInteractionListener) {
            mListener = (tourinfo_tab1_general.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
