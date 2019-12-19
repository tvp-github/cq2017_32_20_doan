package hcmus.android.lighttour.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmus.android.lighttour.R;
import hcmus.android.lighttour.Response.Feedback;


public class ListFeedbackAdapter extends BaseAdapter {
        Context context;
        int layout;
        List<Feedback> listFeedback;
        public ListFeedbackAdapter(Context context, int layout, List<Feedback> listFeedback) {
            this.context = context;
            this.layout = layout;
            this.listFeedback = listFeedback;
        }



        @Override
        public int getCount() {
            return listFeedback.size();
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
            TextView txtFeedback;
            TextView txtName;
            ImageView imgAvatar;
            RatingBar ratingBar;
            //Khởi tạo
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            //Ánh xạ
            txtName = view.findViewById(R.id.fb_name);
            ratingBar = view.findViewById(R.id.fb_ratingbar);
            txtFeedback = view.findViewById(R.id.fb_fb);
            imgAvatar = view.findViewById(R.id.fb_avatar);
            //Đổ dữ liệu
            Feedback feedback = listFeedback.get(i);
            if(feedback.getAvatar()!=null && feedback.getAvatar().length()>0)
                Glide.with(context).load(listFeedback.get(i).getAvatar()).into(imgAvatar);
            txtName .setText(feedback.getName());
            ratingBar.setNumStars(Integer.parseInt(feedback.getPoint()) );
            txtFeedback.setText(feedback.getFeedback());
            return view;
        }


}
