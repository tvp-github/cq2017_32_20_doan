package hcmus.android.lighttour.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import hcmus.android.lighttour.APIService.ResponseInviteService;
import hcmus.android.lighttour.AppUtils.Message;
import hcmus.android.lighttour.AppUtils.ResponseInviteBody;
import hcmus.android.lighttour.MyApplication;
import hcmus.android.lighttour.R;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import hcmus.android.lighttour.TourInformationActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteAdapter extends BaseAdapter {
    Activity context;
    int layout;
    List<Tour> listData;
    Tour tour;
    public InviteAdapter(Activity context, int layout, List<Tour> listData) {
        this.context = context;
        this.layout = layout;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
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
        tour = listData.get(i);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView txtHostName = view.findViewById(R.id.txt_host_name);
        TextView txtTourName = view.findViewById(R.id.txt_tour_name);
        ImageView imgAvatar = view.findViewById(R.id.img_host_avatar);
        Button btnTourInfo = view.findViewById(R.id.btn_tour_info);
        Button btnAccept = view.findViewById(R.id.btn_invite_accept);
        Button btnDec = view.findViewById(R.id.btn_invite_decline);
        txtHostName.setText(tour.getHostName());
        txtTourName.setText("You're invited to tour: "+tour.getName());
        if(tour.getHostAvatar()!=null && tour.getHostAvatar().length()>0)
            Glide.with(context).load(tour.getHostAvatar()).into(imgAvatar);

        btnTourInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TourInformationActivity.class);
                intent.putExtra("tour", new Gson().toJson(tour));
                context.startActivity(intent);
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInviteResponse(true);

            }
        });
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInviteResponse(false);
            }
        });
        return view;
    }

    private void sendInviteResponse(boolean b) {
        MyApplication myApplication = (MyApplication) context.getApplication();
        String token = myApplication.getToken();
        ResponseInviteService responseInviteService = ApiUtils.getResponseInviteSevice();
        responseInviteService.sendData(token, new ResponseInviteBody(tour.getId()+"" , b)).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code()==200){
                    context.startActivity(context.getIntent());
                    context.finish();
                }
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(context, "Unable to connect to server",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
