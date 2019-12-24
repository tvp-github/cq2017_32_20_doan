package hcmus.android.lighttour;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

import hcmus.android.lighttour.APIService.GetStopPointFeedbackService;
import hcmus.android.lighttour.APIService.GetTourReviewService;
import hcmus.android.lighttour.Adapter.ListFeedbackAdapter;
import hcmus.android.lighttour.Adapter.ListReviewAdapter;
import hcmus.android.lighttour.AppUtils.ListFeedback;
import hcmus.android.lighttour.AppUtils.ListReview;
import hcmus.android.lighttour.Response.Feedback;
import hcmus.android.lighttour.Response.Review;
import hcmus.android.lighttour.Response.StopPoint;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tourinfo_tab2_review extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private tourinfo_tab2_review.OnFragmentInteractionListener mListener;
    static ArrayList<Review> listReviewData;
    ListView listReview;
    ListReviewAdapter listReviewAdapter;
    ImageView imgUserAva;
    Tour tour;
    GetTourReviewService listReviewService;
    String token;
    int tourId;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("FFF", "onCreateView: VL");
        View view=inflater.inflate(R.layout.tourinfo_tab2_review, container, false);
        tour= (Tour) getArguments().getSerializable("tour");
        Log.d("FFF", "onCreateView: VL" + tour.toString());
        tourId= tour.getId();

        listReviewData = new ArrayList<Review>();
        //Init Adapter, set Adapter to listReview
        listReviewAdapter = new ListReviewAdapter( getActivity(), R.layout.review_item,listReviewData);
        setListAdapter(listReviewAdapter);

        listReviewService = ApiUtils.getGetTourReviewService();
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        token = myApplication.getToken();
        //Gọi Retrofit Service để lấy dữ liệu từ API

        listReviewService.sendData(token,tourId,1,"200").enqueue(new Callback<ListReview>() {
            @Override
            public synchronized void onResponse(Call<ListReview> call, Response<ListReview> response) {
                Log.d("AAA", "onResponse: "+response.code() );
                if(response.code()==200){
                    updateListView(response.body().getReviews());
                }
            }

            @Override
            public void onFailure(Call<ListReview> call, Throwable t) {
                Log.d("AAA", "onFailure: ");
            }
        });


        return inflater.inflate(R.layout.fragment_tab2_review, container, false);
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
