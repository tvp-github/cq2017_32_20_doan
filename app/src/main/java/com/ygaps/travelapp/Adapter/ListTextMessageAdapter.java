package com.ygaps.travelapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.Notification;

public class ListTextMessageAdapter extends ArrayAdapter {
    Activity context;
    int layout;
    List<Notification> listNoti;
    Notification noti;
    String token;


    public ListTextMessageAdapter (Activity context, int layout, List<Notification> listNoti) {
        super(context, layout, listNoti);
        this.context = context;
        this.layout = layout;
        this.listNoti = listNoti;
    }


    @Override
    public int getCount() {
        //Log.d("FFF", "getCount: " + listNoti.size());
        return listNoti.size();
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
        TextView txtName;
        TextView txtNoti;
        //Khởi tạo
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        //Ánh xạ
        txtName = view.findViewById(R.id.txtName);
        txtNoti = view.findViewById(R.id.txtNoti);


        //Đổ dữ liệu
        noti = listNoti.get(i);
        txtName.setText(noti.getName());
        txtNoti.setText(noti.getNotification());
        return view;
    }
}