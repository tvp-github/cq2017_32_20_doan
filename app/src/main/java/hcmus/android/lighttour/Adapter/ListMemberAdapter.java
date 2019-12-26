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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hcmus.android.lighttour.APIService.SendReportReviewService;
import hcmus.android.lighttour.MyApplication;
import hcmus.android.lighttour.R;
import hcmus.android.lighttour.Response.Member;
import hcmus.android.lighttour.Response.Message;
import hcmus.android.lighttour.Response.Review;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMemberAdapter extends ArrayAdapter {
    Activity context;
    int layout;
    ArrayList<Member> listMember;
    Member member;
    ImageButton btnReport;
    public ListMemberAdapter(Activity context, int layout, ArrayList<Member> listMember) {
        super(context,layout,listMember);
        this.context = context;
        this.layout = layout;
        this.listMember = listMember;
    }



    @Override
    public int getCount() {
        Log.d("FFF", "getCount: "+ listMember.size());
        return listMember.size();
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
        final TextView txtPhone;
        TextView txtName;
        ImageView imgAvatar;
        RatingBar ratingBar;

        //Khởi tạo
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        //Ánh xạ
        txtName = view.findViewById(R.id.review_name);
        ratingBar = view.findViewById(R.id.review_ratingbar);
        ratingBar.setVisibility(View.GONE);
        txtPhone = view.findViewById(R.id.review);
        imgAvatar = view.findViewById(R.id.review_avatar);


        //Đổ dữ liệu
        member = listMember.get(i);
        if(member.getAvatar()!=null && member.getAvatar().length()>0)
            Glide.with(context).load(listMember.get(i).getAvatar()).into(imgAvatar);
        txtPhone .setText(member.getPhone());
        if(member.getName()!= null)
        txtName.setText(member.getName());
        else
            txtName.setText("Người dùng ẩn danh");
        btnReport = view.findViewById(R.id.btn_report);
        btnReport.setVisibility(View.GONE);
        return view;
    }


}
