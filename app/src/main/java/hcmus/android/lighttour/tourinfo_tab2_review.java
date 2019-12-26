package hcmus.android.lighttour;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import hcmus.android.lighttour.APIService.AddStopPointsService;
import hcmus.android.lighttour.APIService.GetStopPointFeedbackService;
import hcmus.android.lighttour.APIService.GetTourReviewService;
import hcmus.android.lighttour.Adapter.ListCommentAdapter;
import hcmus.android.lighttour.Adapter.ListFeedbackAdapter;
import hcmus.android.lighttour.Adapter.ListMemberAdapter;
import hcmus.android.lighttour.Adapter.ListReviewAdapter;
import hcmus.android.lighttour.Adapter.ListStopPointAdapter;
import hcmus.android.lighttour.AppUtils.AddStopPointsBody;
import hcmus.android.lighttour.AppUtils.ListFeedback;
import hcmus.android.lighttour.AppUtils.ListReview;
import hcmus.android.lighttour.AppUtils.Message;
import hcmus.android.lighttour.Response.Comment;
import hcmus.android.lighttour.Response.Feedback;
import hcmus.android.lighttour.Response.Member;
import hcmus.android.lighttour.Response.Review;
import hcmus.android.lighttour.Response.StopPoint;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class tourinfo_tab2_review extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private tourinfo_tab2_review.OnFragmentInteractionListener mListener;
    ArrayList<Review> listReviewData;
    ArrayList<Comment> listCommentData;
    ArrayList<Member> listMemberData;
    ArrayList<StopPoint> listStopPointData;
    List<Integer> deleteIds = new ArrayList<>();
    ListReviewAdapter listReviewAdapter;
    ListCommentAdapter listCommentAdapter;
    ListStopPointAdapter listStopPointAdapter;
    ListMemberAdapter listMemberAdapter;
    Button btnApplyChanges;
    Button btnAddStopPoints;
    LinearLayout ctrlStopPoint;
    LinearLayout lnlAddMem;

    int selectedPos;
    String newLat;
    String newLong;
    boolean hasChange = false;
    boolean hasControl = false;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    ImageView imgUserAva;
    Tour tour;
    GetTourReviewService listReviewService;
    String token;
    boolean fromHistory = false;
    int tourId;
    int tab;
    public tourinfo_tab2_review() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab2_review.
     */
    // TODO: Rename and change types and number of parameters
    public static tourinfo_tab2_review newInstance(String param1, String param2) {
        tourinfo_tab2_review fragment = new tourinfo_tab2_review();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("FFF", "onCreateView: VL");

        View view=inflater.inflate(R.layout.tourinfo_tab2_review, container, false);
        tour= new Gson(). fromJson(getArguments().getString("tour"),Tour.class);
        tab = getArguments().getInt("tab");
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        token = myApplication.getToken();
        int userId = myApplication.getIdUser();
        fromHistory = getArguments().getBoolean("fromHistory");
        hasControl = fromHistory && (tour.getHostId() == userId);
        switch (tab) {
            case 2:
                Log.d("FFF", "onCreateView: VL" + tour.toString());
                tourId = tour.getId();

                listReviewData = new ArrayList<Review>();
                //Init Adapter, set Adapter to listReview
                listReviewAdapter = new ListReviewAdapter(getActivity(), R.layout.review_item, listReviewData);
                setListAdapter(listReviewAdapter);

                listReviewService = ApiUtils.getGetTourReviewService();

                //Gọi Retrofit Service để lấy dữ liệu từ API

                listReviewService.sendData(token, tourId, 1, "200").enqueue(new Callback<ListReview>() {
                    @Override
                    public synchronized void onResponse(Call<ListReview> call, Response<ListReview> response) {
                        Log.d("AAA", "onResponse: " + response.code());
                        if (response.code() == 200) {
                            updateListView(response.body().getReviews());
                        }
                    }

                    @Override
                    public void onFailure(Call<ListReview> call, Throwable t) {
                        Log.d("AAA", "onFailure: ");
                    }
                });
                break;
            case 3:
                listCommentData = new ArrayList<Comment>();
                Log.d(TAG, "onCreateView: "+ new Gson().toJson(tour).toString());
                listCommentData.addAll(tour.getComments());
                listCommentAdapter = new ListCommentAdapter(getActivity(),R.layout.review_item,listCommentData);
                setListAdapter(listCommentAdapter);
                break;
            case 4:
                lnlAddMem = view.findViewById(R.id.add_memberbar);
                listMemberData = new ArrayList<Member>();
                listMemberData.addAll(tour.getMembers());
                listMemberAdapter = new ListMemberAdapter(getActivity(),R.layout.review_item,listMemberData);
                setListAdapter(listMemberAdapter);
                if(hasControl){
                    lnlAddMem.setVisibility(View.VISIBLE);
                }
                break;
            case 5:
                listStopPointData = new ArrayList<StopPoint>();
                Log.d(TAG, "onCreateView: "+ new Gson().toJson(tour).toString());
                listStopPointData.addAll(tour.getStopPoints());
                listStopPointAdapter = new ListStopPointAdapter(getActivity(),R.layout.list_stop_points_item,listStopPointData);
                setListAdapter(listStopPointAdapter);
                if (hasControl) {
                    Log.d(TAG, "onCreateView: " + new Gson().toJson(listStopPointData));
                    for(int i = 0 ; i<listStopPointData.size(); i++){
                        deleteIds.add(listStopPointData.get(i).getId().intValue());
                        listStopPointData.get(i).setId(null);
                    }
                    ctrlStopPoint = view.findViewById(R.id.ctrl_stop_point);
                    ctrlStopPoint.setVisibility(View.VISIBLE);
                    btnApplyChanges = view.findViewById(R.id.btn_apply_changes);
                    btnApplyChanges.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Set data and send
                            AddStopPointsService addStopPointsService = ApiUtils.getAddStopPointsService();
                            addStopPointsService.sendData(token, new AddStopPointsBody(""+tour.getId(),listStopPointData,deleteIds)).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if(response.code() == 200){
                                        Toast.makeText(getActivity(), "Update tour success", Toast.LENGTH_SHORT).show();
                                        restartFragment();
                                    }
                                    else {
                                        try {
                                            Log.d("AAA", "onResponse: "+ response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Unable to connect to server", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }
                            });
                        }
                    });
                    btnAddStopPoints = view.findViewById(R.id.btn_add_stop_point);
                    btnAddStopPoints.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), MapsActivity.class);
                            intent.putExtra("tourId",""+tour.getId());
                            intent.putExtra("type", 0);
                            startActivityForResult(intent,001);
                        }
                    });
                }
                break;
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 001 && resultCode == 001) {
            restartFragment();
        }
        if(requestCode == 001 && resultCode == 002){
            StopPoint stoppoint = listStopPointData.get(selectedPos);
            newLat = ""+ data.getDoubleExtra("lat", Double.parseDouble(stoppoint.getLat()));
            newLong = ""+ data.getDoubleExtra("long", Double.parseDouble(stoppoint.getLat()));
            if (newLat.equals(stoppoint.getLat())&&newLong.equals(stoppoint.getLong())){
                listStopPointData.get(selectedPos).setLat(newLat);
                listStopPointData.get(selectedPos).setLong(newLong);
                btnApplyChanges.setVisibility(View.VISIBLE);
            }

        }
    }

    private void restartFragment() {
        startActivity(getActivity().getIntent());
        getActivity().finish();
    }
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (tab == 5) {
            displayStopPointDialog(listStopPointData.get(position), true);
            selectedPos = position;
        }
    }

    private void displayStopPointDialog(final StopPoint stopPoint, boolean isShow) {
        final LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.create_stop_point, null);
        //Ánh xạ các view từ layout
        final Spinner spinnerService = alertLayout.findViewById(R.id.spinner_serviceType);
        final Spinner spinnerProvice = alertLayout.findViewById(R.id.spinner_provice);
        final EditText edtName = alertLayout.findViewById(R.id.edit_stopPointname);
        final EditText edtAddress = alertLayout.findViewById(R.id.edit_address);
        final EditText edtMinCost = alertLayout.findViewById(R.id.edit_minCoststop);
        final EditText edtMaxCost = alertLayout.findViewById(R.id.edit_maxCoststop);
        final TextView txtDateArrive = alertLayout.findViewById(R.id.text_dateArrive);
        final TextView txtDateLeave = alertLayout.findViewById(R.id.text_dateLeave);
        final EditText edtTimeArrive = alertLayout.findViewById(R.id.text_timeArrive);
        final EditText edtTimeLeave = alertLayout.findViewById(R.id.text_timeLeave);
        ImageButton btnDateArrive = alertLayout.findViewById(R.id.btn_calendar_arrive);
        ImageButton btnDateLeave = alertLayout.findViewById(R.id.btn_calendar_leave);
        Button btnChooseLocation = alertLayout.findViewById(R.id.btn_choose_location);

        if (isShow) {
            spinnerService.setEnabled(false);
            spinnerProvice.setEnabled(false);
            edtName.setFocusable(false);
            edtAddress.setFocusable(false);
            edtMinCost.setFocusable(false);
            edtMaxCost.setFocusable(false);
            edtTimeArrive.setFocusable(false);
            edtTimeLeave.setFocusable(false);
            btnDateArrive.setEnabled(false);
            btnDateLeave.setEnabled(false);
        }

        final Calendar calendarArrivalAt = Calendar.getInstance();
        calendarArrivalAt.setTimeInMillis(stopPoint.getArrivalAt());
        final Calendar calendarLeaveAt = Calendar.getInstance();
        calendarLeaveAt.setTimeInMillis(stopPoint.getLeaveAt());
        txtDateArrive.setText(simpleDateFormat.format(calendarArrivalAt.getTime()));
        txtDateArrive.setTag(calendarArrivalAt);
        edtTimeArrive.setText("" + calendarArrivalAt.get(Calendar.HOUR) + ":" + calendarArrivalAt.get(Calendar.MINUTE));
        txtDateLeave.setText(simpleDateFormat.format(calendarLeaveAt.getTime()));
        txtDateLeave.setTag(calendarLeaveAt);
        edtTimeLeave.setText("" + calendarLeaveAt.get(Calendar.HOUR) + ":" + calendarLeaveAt.get(Calendar.MINUTE));

        btnDateArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = (Calendar) txtDateArrive.getTag();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DATE, i2);
                        //Xuất ra
                        txtDateArrive.setText(simpleDateFormat.format(calendar.getTime()));
                        txtDateArrive.setTag(calendar);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();

            }
        });
        btnDateLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = (Calendar) txtDateLeave.getTag();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DATE, i2);
                        //Xuất ra
                        txtDateLeave.setText(simpleDateFormat.format(calendar.getTime()));
                        txtDateLeave.setTag(calendar);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        //Đổ data cho spinner
        String[] service = {"Restaurant", "Hotel", "Rest station", "Other"};
        String[] province = {"Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Bình Dương", "Đồng Nai", "Khánh Hòa", "Hải Phòng",
                "Long An", "Quảng Nam", "Bà Rịa Vũng Tàu", "Đắk Lắk", "Cần Thơ", "Bình Thuận", "Lâm Đồng", "Thừa Thiên Huế",
                "Kiên Giang", "Bắc Ninh", "Quảng Ninh", "Thanh Hóa", "Nghệ An", "Hải Dương", "Gia Lai", "Bình Phước", "Hưng Yên",
                "Bình Định", "Tiền Giang", "Thái Bình", "Bắc Giang", "Hòa Bình", "An Giang", "Vĩnh Phúc", "Tây Ninh", "Thái Nguyên",
                "Lào Cai", "Nam Định", "Quảng Ngãi", "Bến Tre", "Đắk Nông", "Cà Mau", "Vĩnh Long", "Ninh Bình", "Phú Thọ", "Ninh Thuận",
                "Phú Yên", "Hà Nam", "Hà Tĩnh", "Đồng Tháp", "Sóc Trăng", "Kon Tum", "Quảng Bình", "Quảng Trị", "Trà Vinh", "Hậu Giang",
                "Sơn La", "Bạc Liêu", "Yên Bái", "Tuyên Quang", "Điện Biên", "Lai Châu", "Lạng Sơn", "Hà Giang", "Bắc Kạn", "Cao Bằng"};
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, service);
        ArrayAdapter<String> proviceAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, province);
        spinnerService.setAdapter(serviceAdapter);
        spinnerProvice.setAdapter(proviceAdapter);
        //Đổ dữ liệu ra widget
        spinnerProvice.setSelection(Math.max(stopPoint.getProvinceId() - 1, 0));
        spinnerService.setSelection(Math.max(stopPoint.getServiceTypeId() - 1, 0));
        edtName.setText(stopPoint.getName());
        edtAddress.setText(stopPoint.getAddress());
        edtMinCost.setText(stopPoint.getMinCost());
        edtMaxCost.setText(stopPoint.getMaxCost());
        //Tạo khung thông báo
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Stop Point Infomation");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", null);
        if (hasControl)
            if (isShow) {
                alert.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listStopPointData.remove(selectedPos);
                        listStopPointAdapter.notifyDataSetChanged();
                        btnApplyChanges.setVisibility(View.VISIBLE);
                    }
                });
                alert.setPositiveButton("Update this", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        displayStopPointDialog(stopPoint, false);
                    }
                });
            } else {
                alert.setPositiveButton("Update", null);
            }

        final AlertDialog dialog = alert.create();
        if (hasControl)
            if (!isShow) {
                btnChooseLocation.setVisibility(View.VISIBLE);
                btnChooseLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),MapsActivity.class);
                        intent.putExtra("type",1);
                        startActivityForResult(intent,002);
                    }
                });
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Calendar arrivalAt = (Calendar) (txtDateArrive.getTag());
                                Calendar leaveAt = (Calendar) (txtDateLeave.getTag());
                                int hour, minute;
                                String[] time;
                                if (validateHour(edtTimeArrive.getText().toString(), edtTimeLeave.getText().toString())) {
                                    //get Arrival Time
                                    time = edtTimeArrive.getText().toString().split(":");
                                    hour = Integer.parseInt(time[0]);
                                    minute = Integer.parseInt(time[1]);
                                    //set arrivalTime
                                    arrivalAt.set(Calendar.HOUR, hour);
                                    arrivalAt.set(Calendar.MINUTE, minute);
                                    //getLeaveTime
                                    time = edtTimeLeave.getText().toString().split(":");
                                    hour = Integer.parseInt(time[0]);
                                    minute = Integer.parseInt(time[1]);
                                    //setLeaveTime
                                    leaveAt.set(Calendar.HOUR, hour);
                                    leaveAt.set(Calendar.MINUTE, minute);
                                    //set Time date


                                    if (validate(edtName.getText().toString())) {

                                        if (edtName.getText().toString().equals(stopPoint.getName())
                                                && edtAddress.getText().toString().equals(stopPoint.getAddress())
                                                && spinnerService.getSelectedItemPosition() + 1 == stopPoint.getServiceTypeId()
                                                && spinnerProvice.getSelectedItemPosition() + 1 == stopPoint.getProvinceId()
                                                && edtMinCost.getText().toString().equals(stopPoint.getMinCost())
                                                && edtMaxCost.getText().toString().equals(stopPoint.getMaxCost())
                                                && calendarArrivalAt.getTimeInMillis() == stopPoint.getArrivalAt()
                                                && calendarLeaveAt.getTimeInMillis() == stopPoint.getLeaveAt()
                                        ) {
                                        } else {
                                            stopPoint.setName(edtName.getText().toString());
                                            stopPoint.setAddress(edtAddress.getText().toString());
                                            stopPoint.setServiceTypeId(spinnerService.getSelectedItemPosition() + 1);
                                            stopPoint.setProvinceId(spinnerProvice.getSelectedItemPosition() + 1);
                                            stopPoint.setMinCost(edtMinCost.getText().toString());
                                            stopPoint.setMaxCost(edtMaxCost.getText().toString());
                                            stopPoint.setArrivalAt(calendarArrivalAt.getTimeInMillis());
                                            stopPoint.setLeaveAt(calendarLeaveAt.getTimeInMillis());
                                            btnApplyChanges.setVisibility(View.VISIBLE);
                                            listStopPointAdapter.notifyDataSetChanged();
                                        }
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Invalid Stop Point input", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Invalid hour input", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        dialog.show();
    }

    private void updateListView(List<Review> listReview){
        listReviewData.addAll(listReview);
        listReviewAdapter.notifyDataSetChanged();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private boolean validateHour(String ...str){
        for (int i = 0 ; i<str.length; i++) {
            if (!str[i].matches("^([2][0-3]|[0-1][0-9]|[0-9]):([0-5][0-9]|[0-9])$"))
                return false;
        }
        return true;
    }
    private boolean validate(String ...str) {
        for (int i = 0 ; i< str.length ; i++ )
            if (str[i].length() == 0)
                return false;
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof tourinfo_tab2_review.OnFragmentInteractionListener) {
            mListener = (tourinfo_tab2_review.OnFragmentInteractionListener) context;
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
