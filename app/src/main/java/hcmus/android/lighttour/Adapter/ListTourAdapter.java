package hcmus.android.lighttour.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import hcmus.android.lighttour.R;
import hcmus.android.lighttour.Response.Tour;

public class ListTourAdapter extends BaseAdapter{
    Context context;
    int layout;
    List<Tour> listTour;
    public ListTourAdapter(Context context, int layout, List<Tour> listTour) {
        this.context = context;
        this.layout = layout;
        this.listTour = listTour;
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
        Tour tour = listTour.get(i);
//        if(listTour.get(i).getAvatar()!=null && listTour.get(i).getAvatar().length()>0)
//            Glide.with(context).load(listTour.get(i).getAvatar()).into(imgAvatar);
        txtPrice.setText(tour.getMinCost()+" - "+tour.getMaxCost());
        txtName .setText(tour.getName());
        txtnumpeople.setText(tour.getAdults()+" Adults - "+tour.getChilds() + " Children");
        Calendar startDate, endDate;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        startDate.setTimeInMillis(Long.parseLong(tour.getStartDate()));
        endDate.setTimeInMillis(Long.parseLong(tour.getEndDate()));
        txtDate.setText(formatCalendar(startDate)+" - "+ formatCalendar(endDate));
        return view;
    }
    String formatCalendar(Calendar calendar){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(calendar.get(Calendar.DATE));

        return result;
    }
}

