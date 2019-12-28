package com.ygaps.travelapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.UserInfo;

public class UserAdapter extends ArrayAdapter {
    Activity context;
    int layout;
    ArrayList<UserInfo> listInfo;
    public UserAdapter(Activity context, int layout, ArrayList<UserInfo> listUser) {
        super(context,layout,listUser);
        this.context = context;
        this.layout = layout;
        this.listInfo = listUser;
    }

    @Override
    public int getCount() {
        return listInfo.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View view, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        ImageView avatar = view.findViewById(R.id.img_user_avatar);
        TextView txtName = view.findViewById(R.id.txt_user_name);
        TextView txtPhone = view.findViewById(R.id.txt_phone);
        TextView txtEmail = view.findViewById(R.id.txt_email);
        UserInfo user = listInfo.get(i);
        if(user.getAvatar()!=null && user.getAvatar().length()>0)
            Glide.with(context).load(user.getAvatar()).into(avatar);
        txtName.setText(user.getFullName());
        txtPhone.setText(user.getPhone());
        txtEmail.setText(user.getEmail());
        return view;
    }

    @Nullable
    @Override
    public UserInfo getItem(int position) {
        return listInfo.get(position);
    }


}