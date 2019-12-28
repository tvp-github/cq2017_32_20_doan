package com.ygaps.travelapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.Member;

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
