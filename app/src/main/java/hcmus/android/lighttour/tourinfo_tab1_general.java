package hcmus.android.lighttour;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import hcmus.android.lighttour.APIService.GetPointStarsService;
import hcmus.android.lighttour.APIService.GetReviewPointStarsService;
import hcmus.android.lighttour.APIService.UpdateTourService;
import hcmus.android.lighttour.AppUtils.ListPointStars;
import hcmus.android.lighttour.AppUtils.UpdateTourBody;
import hcmus.android.lighttour.Response.PointStars;
import hcmus.android.lighttour.Response.Review;
import hcmus.android.lighttour.Response.StopPoint;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class tourinfo_tab1_general extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GetReviewPointStarsService ReviewPointStarsService;
    private tourinfo_tab1_general.OnFragmentInteractionListener mListener;
    UpdateTourService updateTourService;

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
    Button btnEdit;
    Button btnDelete;

    ScrollView updateTour;
    ConstraintLayout showTourInfo;
    LinearLayout lnlControl;

    //Update Tour View
    EditText edtName;
    TextView txtStartDate;
    TextView txtEndDate;
    EditText edtAdults;
    EditText edtChildren;
    EditText edtMinCost;
    EditText edtMaxCost;
    CheckBox checkPrivate;
    Button btnUpdate;
    Button btnCancel;
    ImageButton btnStartDate;
    ImageButton btnEndDate;
    Button btnFollow;

    Tour tour;
    String token;
    int total=0;
    int point=0;
    boolean hasControl= false;
    boolean hasFollow = false;
    int raters[] = new int[5];
    int colors[] = new int[]{
            Color.parseColor("#0e9d58"),
            Color.parseColor("#bfd047"),
            Color.parseColor("#ffc105"),
            Color.parseColor("#ef7e14"),
            Color.parseColor("#d36259")};

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                write_review_intent.putExtra("tour", new Gson().toJson(tour));
                startActivity(write_review_intent );
            }
        });

        RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.rb_rate_now);

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent write_review_intent = new Intent(getActivity(), WriteReviewActivity.class);
                    write_review_intent.putExtra("tour", new Gson().toJson(tour));
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
        Log.d("rrrr", "onCreateView: ABCXYZ");
        View view=inflater.inflate(R.layout.tourinfo_tab1_general, container, false);
        tour= new Gson(). fromJson(getArguments().getString("tour"),Tour.class);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        int userId = myApplication.getIdUser();
        token = myApplication.getToken();
        hasFollow = getArguments().getBoolean("fromHistory");
        hasControl = getArguments().getBoolean("fromHistory") && (tour.getHostId() == userId);
        ReviewPointStarsService = ApiUtils.getGetReviewPointStarsService();

        //Ánh xạ
        txtPrice = view.findViewById(R.id.txtprice);
        txtName = view.findViewById(R.id.txtname);
        txtnumpeople = view.findViewById(R.id.txtnumpeople);
        txtDate = view.findViewById(R.id.txtDate);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        ratingPoint = view.findViewById(R.id.ratingPoint);
        numberReviewer = view.findViewById(R.id.numberReviewer);
        ratingBar = view.findViewById(R.id.ratingBar);
        lnlControl = view.findViewById(R.id.lnlControl);
        btnFollow = view.findViewById(R.id.btnFollow);
        //Đổ dữ liệu
        if (hasFollow){
            btnFollow.setVisibility(view.VISIBLE);
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent follow_tour = new Intent(getActivity(), FollowTourActivity.class);
                    follow_tour.putExtra("tour", new Gson().toJson(tour));
                    startActivity(follow_tour );
                }
            });
        }
        if ( hasControl ){
            showTourInfo = view.findViewById(R.id.ShowTourInfo);
            updateTour = view.findViewById(R.id.updateTour);

            // Ánh xạ cho update tour
            edtName = view.findViewById(R.id.edit_tour_name);
            txtStartDate = view.findViewById(R.id.startDate);
            txtEndDate = view.findViewById(R.id.endDate);
            edtAdults = view.findViewById(R.id.edit_adults);
            edtChildren = view.findViewById(R.id.edit_children);
            edtMinCost = view.findViewById(R.id.edit_minCost);
            edtMaxCost = view.findViewById(R.id.edit_maxCost);
            checkPrivate = view.findViewById(R.id.check_privateTour);
            btnUpdate = view.findViewById(R.id.btn_update_tour);
            btnCancel = view.findViewById(R.id.btn_cancel);
            btnStartDate = view.findViewById(R.id.btnStartDate);
            btnEndDate = view.findViewById(R.id.btnEndDate);


            lnlControl.setVisibility(View.VISIBLE);
            updateTourService = ApiUtils.getUpdateTourService();
            btnDelete = view.findViewById(R.id.btnDeleteTour);
            btnEdit = view.findViewById(R.id.btnEditTour);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Do you want to delete this tour?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    updateTourService.sendData(token, new UpdateTourBody(tour.getId(),tour.getName(),Long.parseLong(tour.getStartDate()),
                                            Long.parseLong(tour.getEndDate()),tour.getAdults(),tour.getChilds(),
                                            Integer.parseInt(tour.getMinCost()),Integer.parseInt(tour.getMaxCost()),tour.getIsPrivate(),-1)).enqueue(new Callback<Tour>() {
                                        @Override
                                        public void onResponse(Call<Tour> call, Response<Tour> response) {
                                            if(response.code()==200){
                                                tourinfo_tab1_general.this.getActivity().finish();
                                            }
                                            else {
                                                try {
                                                    Toast.makeText(getActivity(), ""+ response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Tour> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTourInfo.setVisibility(View.INVISIBLE);
                    updateTour.setVisibility(View.VISIBLE);


                    //Đổ dữ liệu ra màn hình chỉnh sửa thông tin tour
                    edtName.setText(tour.getName());
                    Calendar calendarStartDate = Calendar.getInstance();
                    calendarStartDate.setTimeInMillis(Long.parseLong(tour.getStartDate()));
                    Calendar calendarEndDate = Calendar.getInstance();
                    calendarEndDate.setTimeInMillis(Long.parseLong(tour.getEndDate()));

                    txtStartDate.setText(simpleDateFormat.format(calendarStartDate.getTime()));
                    txtStartDate.setTag(calendarStartDate);
                    txtEndDate.setText(simpleDateFormat.format(calendarEndDate.getTime()));
                    txtEndDate.setTag(calendarEndDate);
                    Log.d(TAG, "onClick: "+ new Gson().toJson(tour));
                    edtMinCost.setText(tour.getMinCost());
                    edtMaxCost.setText(tour.getMaxCost());
                    edtAdults.setText(""+tour.getAdults());
                    edtChildren.setText(""+tour.getChilds());
                    checkPrivate.setChecked(tour.getIsPrivate());
                    btnStartDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Tạo calendar lưu giá trị nhập vào
                            final Calendar calendarStart = (Calendar) txtStartDate.getTag();
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    //cập nhật calendar
                                    calendarStart.set(Calendar.YEAR, i);
                                    calendarStart.set(Calendar.MONTH, i1);
                                    calendarStart.set(Calendar.DATE, i2);
                                    //Xuất ra
                                    txtStartDate.setText(simpleDateFormat.format(calendarStart.getTime()));
                                    txtStartDate.setTag(calendarStart);
                                }
                            }, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE));
                            datePickerDialog.show();
                        }
                    });
                    btnEndDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Tạo calendar lưu giá trị nhập vào
                            final Calendar calendarEnd = (Calendar) txtEndDate.getTag();
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    //cập nhật calendar
                                    calendarEnd.set(Calendar.YEAR, i);
                                    calendarEnd.set(Calendar.MONTH, i1);
                                    calendarEnd.set(Calendar.DATE, i2);
                                    //Xuất ra
                                    txtEndDate.setText(simpleDateFormat.format(calendarEnd.getTime()));
                                    txtEndDate.setTag(calendarEnd);
                                }
                            }, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DATE));
                            datePickerDialog.show();
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showTourInfo.setVisibility(View.VISIBLE);
                            updateTour.setVisibility(View.GONE);
                        }
                    });
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String name = edtName.getText().toString();
                            long startDate = ((Calendar)(txtStartDate.getTag())).getTimeInMillis();
                            long endDate = ((Calendar)(txtEndDate.getTag())).getTimeInMillis();
                            Boolean isPrivate = checkPrivate.isChecked();
                            if(validate(name, edtAdults.getText().toString(), edtChildren.getText().toString(), edtMaxCost.getText().toString(), edtMinCost.getText().toString())) {
                                int adults = Integer.parseInt(edtAdults.getText().toString());
                                int children = Integer.parseInt(edtChildren.getText().toString());
                                int minCost = Integer.parseInt(edtMinCost.getText().toString());
                                int maxCost = Integer.parseInt(edtMaxCost.getText().toString());
                                //Validate data
                                updateTourService.sendData(token, new UpdateTourBody(tour.getId(), name, startDate, endDate, adults, children, minCost, maxCost, isPrivate, tour.getStatus())).enqueue(new Callback<Tour>() {
                                    @Override
                                    public void onResponse(Call<Tour> call, Response<Tour> response) {
                                        if (response.code() == 200) {
                                            getActivity().finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Tour> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Unable to connect to server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                                Toast.makeText(getActivity(), "Invalid value", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


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
        token = myApplication.getToken();
        sendTourId(TourId);


        return view;
    }

    private boolean validate(String ...str) {
        for (int i = 0 ; i< str.length ; i++ )
            if (str[i].length() == 0)
                return false;
        return true;
    }

    String formatCalendar(Calendar calendar){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(calendar.getTime());

        return result;
    }

    public void sendTourId(int tourId ){
        ReviewPointStarsService.sendData(token,tourId).enqueue(new Callback<ListPointStars>() {
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
