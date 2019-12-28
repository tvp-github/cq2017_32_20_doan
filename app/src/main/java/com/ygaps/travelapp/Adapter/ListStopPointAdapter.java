package com.ygaps.travelapp.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.StopPoint;

import java.util.List;

public class ListStopPointAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<StopPoint> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StopPoint getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public ListStopPointAdapter(Context context, int layout, List<StopPoint> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        view = inflater.inflate(layout,null);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtAddress = view.findViewById(R.id.txtAddress);
        txtName.setText(list.get(i).getName());
        txtAddress.setText(list.get(i).getAddress());
        return view;
    }
}
