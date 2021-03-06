package com.ygaps.travelapp;

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

import com.ygaps.travelapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ygaps.travelapp.APIService.AddStopPointsService;
import com.ygaps.travelapp.APIService.GetTourInfoService;
import com.ygaps.travelapp.APIService.GetTourReviewService;
import com.ygaps.travelapp.APIService.InviteService;
import com.ygaps.travelapp.APIService.SearchUserService;
import com.ygaps.travelapp.Adapter.ListCommentAdapter;
import com.ygaps.travelapp.Adapter.ListMemberAdapter;
import com.ygaps.travelapp.Adapter.ListReviewAdapter;
import com.ygaps.travelapp.Adapter.ListStopPointAdapter;
import com.ygaps.travelapp.Adapter.UserAdapter;
import com.ygaps.travelapp.AppUtils.AddStopPointsBody;
import com.ygaps.travelapp.AppUtils.InviteBody;
import com.ygaps.travelapp.AppUtils.ListReview;
import com.ygaps.travelapp.AppUtils.Message;
import com.ygaps.travelapp.Response.Comment;
import com.ygaps.travelapp.Response.Member;
import com.ygaps.travelapp.Response.Review;
import com.ygaps.travelapp.Response.SearchUsers;
import com.ygaps.travelapp.Response.StopPoint;
import com.ygaps.travelapp.Response.Tour;
import com.ygaps.travelapp.Response.UserInfo;
import com.ygaps.travelapp.Retrofit.ApiUtils;
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

    EditText edtInviteName;
    Button btnInvite;
    LinearLayout lnlAddMem;
    ArrayList<UserInfo> listUserData;
    UserAdapter userAdapter;
    SearchUserService searchUserService;
    Integer currentPage = 1;
    Button btnNext;
    Button btnPrev;


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
        tourId = tour.getId();
        switch (tab) {
            case 2:
                Log.d("FFF", "onCreateView: VL" + tour.toString());

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
                if(fromHistory){
                    lnlAddMem.setVisibility(View.VISIBLE);
                    btnInvite = view.findViewById(R.id.btnAddMem);
                    edtInviteName = view.findViewById(R.id.edtMemName);
                    listUserData = new ArrayList<>();
                    userAdapter = new UserAdapter(getActivity(),R.layout.users_list_item,listUserData);
                    btnInvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LayoutInflater layoutInflater = getLayoutInflater();
                            View layout = layoutInflater.inflate(R.layout.list_user,null);
                            ListView lvUser = layout.findViewById(R.id.lv_users);
                            lvUser.setAdapter(userAdapter);
                            lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, final int li, long l) {
                                    AlertDialog.Builder confirm = new AlertDialog.Builder(getActivity());
                                    confirm.setTitle("Do you want to invite this person?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    sendInvite(listUserData.get(li).getId());
                                                }
                                            })
                                            .setNegativeButton("No",null)
                                            .create()
                                            .show();
                                }
                            });
                            btnNext = layout.findViewById(R.id.btn_nextPage);
                            btnPrev = layout.findViewById(R.id.btn_prevPage);
                            btnNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    currentPage++;
                                    searchUser();
                                }
                            });
                            btnPrev.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    currentPage--;
                                    searchUser();
                                }
                            });
                            //Show diaglog
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("User list")
                                    .setView(layout)
                                    .setNegativeButton("Continue",null)
                                    .create()
                                    .show();
                            searchUserService = ApiUtils.getSearchUserService();
                            searchUser();
                        }
                    });
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
                            sendData(0);
                        }
                    });
                    btnAddStopPoints = view.findViewById(R.id.btn_add_stop_point);
                    btnAddStopPoints.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), MapsActivity.class);
                            intent.putExtra("tourId",""+tour.getId());
                            intent.putExtra("type", 2);
                            startActivityForResult(intent,001);
                        }
                    });
                }
                break;
        }

        return view;
    }

    private void updateData(){
        GetTourInfoService getTourInfoService = ApiUtils.getGetTourInfoService();
        getTourInfoService.sendData(token, tourId ).enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                List<StopPoint> listStopPoint = response.body().getStopPoints();
                if(response.code() == 200){
                    Log.d("AAA", "onResponse: "+ new Gson().toJson(listStopPoint));
                    for(int i = 0 ; i<listStopPointData.size(); i++)
                    {
                        for (int j = 0 ; j<listStopPoint.size(); j++){
                            if (listStopPointData.get(i).getServiceId().equals(listStopPoint.get(j). getServiceId()))
                            {
                                listStopPointData.get(i).setId(listStopPoint.get(j).getId());
                                listStopPointData.get(i).setServiceId(null);
                                break;
                            }
                        }
                    }
                    sendData(2);
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {

            }
        });
    }
    //
    private void sendData(final int mode) {
        AddStopPointsService addStopPointsService = ApiUtils.getAddStopPointsService();
        Log.d("AAA", "onClick: " + new Gson().toJson(listStopPointData) + "\n" + tourId);
        List<StopPoint> sendList = (mode == 0)? null : listStopPointData;
        List<Integer> sendDelete = (mode == 0)? deleteIds: null;
        AddStopPointsBody b = new AddStopPointsBody(""+tourId, sendList, sendDelete);
        addStopPointsService.sendData(token, b).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() == 200) {

                    if (mode == 0){
                        sendData(1);
                    }
                    else {
                        if (mode == 1) {
                            updateData();
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            restartFragment();
                        }
                    }

                } else {
                    try {
                        Log.d("AAA", "onResponse: " + tourId);
                        Toast.makeText(getActivity(), "" + response.code() + '-' + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //get New Stoppoint
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 001 && resultCode == 001) {
            Type StopPointList = new TypeToken<List<StopPoint>>(){}.getType();
            List<StopPoint> list = new Gson().fromJson(data.getStringExtra("stoppoints"),StopPointList);
            listStopPointData.addAll(list);
            listStopPointAdapter.notifyDataSetChanged();
            btnApplyChanges.setVisibility(View.VISIBLE);
        }
        //get lat long
        if(requestCode == 002 && resultCode == 001){
            Toast.makeText(getActivity(), "Loai 2", Toast.LENGTH_SHORT).show();
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
    private void sendInvite(int userId){
        InviteService inviteService = ApiUtils.getInviteUserSevice();
        int tourId = tour.getId();
        inviteService.sendData(token,new InviteBody(""+tourId,""+userId,true)).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code()==200)
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }
    private void searchUser(){
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        searchUserService.sendData(token,edtInviteName.getText().toString(),currentPage,5).enqueue(new Callback<SearchUsers>() {
            @Override
            public void onResponse(Call<SearchUsers> call, Response<SearchUsers> response) {
                if(response.code() == 200){
                    listUserData.clear();
                    listUserData.addAll(response.body().getUsers());
                    int total = Integer.parseInt(response.body().getTotal());
                    for (UserInfo user: listUserData){
                        if(user.getId() == ((MyApplication)getActivity().getApplication()).getIdUser())
                        {
                            listUserData.remove(user);
                            total --;
                            break;
                        }
                    }
                    int totalPage = (total / 5) + (total%5 == 0 ? 0 : 1);
                    userAdapter.notifyDataSetChanged();
                    if (currentPage == 1)
                        btnPrev.setEnabled(false);
                    if (currentPage == totalPage)
                        btnNext.setEnabled(false);
                    userAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<SearchUsers> call, Throwable t) {

            }
        });
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
        final Button btnChooseLocation = alertLayout.findViewById(R.id.btn_choose_location);

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
        btnChooseLocation.setVisibility(View.VISIBLE);
        btnChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopPoint markerStopPoint = new StopPoint(listStopPointData.get(selectedPos));
                markerStopPoint.setId(markerStopPoint.getServiceId());
                Intent intent = new Intent(getActivity(), PointInformationActivity.class);
                intent.putExtra("stopPoint", markerStopPoint);
                startActivity(intent);
            }
        });
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
                btnChooseLocation.setText("Choose Location");
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
