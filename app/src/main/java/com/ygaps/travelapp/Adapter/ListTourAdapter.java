package com.ygaps.travelapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.Tour;

public class ListTourAdapter extends BaseAdapter{
    Context context;
    int layout;
    Tour tour;
    List<Tour> listTour;
    List<Tour> list;
    public ListTourAdapter(Context context, int layout, List<Tour> listTour) {
        this.context = context;
        this.layout = layout;
        this.listTour = listTour;
        this.list = new ArrayList<>();
        this.list.addAll(listTour);
    }



    @Override
    public int getCount() {
        return listTour.size();
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
        TextView txtPrice;
        TextView txtName;
        TextView txtnumpeople;
        TextView txtDate;
        ImageView imgAvatar;
        //Khởi tạo
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        //Ánh xạ
        txtPrice = view.findViewById(R.id.txtprice);
        txtName = view.findViewById(R.id.txtname);
        txtnumpeople = view.findViewById(R.id.txtnumpeople);
        txtDate = view.findViewById(R.id.txtDate);
        imgAvatar = view.findViewById(R.id.imgAvatar);


        //Đổ dữ liệu
        tour = listTour.get(i);
        if(tour.getAvatar()!=null && tour.getAvatar().length()>0)
            Glide.with(context).load(listTour.get(i).getAvatar()).into(imgAvatar);
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
        return view;
    }
    String formatCalendar(Calendar calendar){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(calendar.getTime());

        return result;
    }

//    public Filter getFilter() {
//        return new Filter() {
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                final FilterResults oReturn = new FilterResults();
//                final ArrayList<Tour> results = new ArrayList<Tour>();
//                if (list == null)
//                    list = listTour;
//                Log.d("AAA","size :"+list.size());
//                if (constraint != null) {
//                    if (list != null && list.size() > 0) {
//                        for (final Tour tour : list) {
//                            if (tour.getName().toLowerCase()
//                                    .contains(constraint.toString()))
//                                results.add(tour);
//                        }
//                    }
//                    oReturn.values = results;
//                }
//                return oReturn;
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void publishResults(CharSequence constraint,
//                                          FilterResults results) {
//                listTour = (ArrayList<Tour>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

     //Filter Class
    public void filter(String name) {
        Log.d("AAA","search");
        name = name.toLowerCase();
        listTour.clear();
        Log.d("AAA","size :"+list.size());
        Log.d("AAA","size :"+list.size());
        if (name.length() == 0) {
            listTour.addAll(list);
        } else {
            for (final Tour wp : list) {
                Log.d("111",wp.toString());
                if (wp.getName()!=null){
                    if (wp.getName().toLowerCase().contains(name)) {
                        listTour.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


}

