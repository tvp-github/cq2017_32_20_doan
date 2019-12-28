package com.ygaps.travelapp.Adapter;

import android.app.Activity;
import android.content.Context;
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
import com.ygaps.travelapp.Response.Comment;

public class ListCommentAdapter extends ArrayAdapter {
    Activity context;
    int layout;
    ArrayList<Comment> listComment;
    Comment comment;
    ImageButton btnReport;
    public ListCommentAdapter(Activity context, int layout, ArrayList<Comment> listComment) {
        super(context,layout,listComment);
        this.context = context;
        this.layout = layout;
        this.listComment = listComment;
    }



    @Override
    public int getCount() {
        return listComment.size();
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
        final TextView txtComment;
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
        txtComment = view.findViewById(R.id.review);
        imgAvatar = view.findViewById(R.id.review_avatar);
        //Đổ dữ liệu
        comment = listComment.get(i);
        if(comment.getAvatar()!=null && comment.getAvatar().length()>0)
            Glide.with(context).load(listComment.get(i).getAvatar()).into(imgAvatar);
        txtName .setText(comment.getName());
        txtComment.setText(comment.getComment());
        btnReport = view.findViewById(R.id.btn_report);
        btnReport.setVisibility(View.INVISIBLE);
        return view;
    }


}
