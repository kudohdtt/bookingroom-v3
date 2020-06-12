package com.learnadroid.myfirstapp.ordered;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.actor.hotelOrdered;

import java.util.List;

public class hotelOrderedAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<hotelOrdered> hotelList;

    public hotelOrderedAdapter(Context context, int layout, List<hotelOrdered> hotelList) {
        this.context = context;
        this.layout = layout;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);
        //ánh xạ view
        TextView txtTen = (TextView) convertView.findViewById(R.id.txtTen);
        TextView txtThanhpho = (TextView) convertView.findViewById(R.id.txtThanhpho);
        TextView txtTrangthai = (TextView) convertView.findViewById(R.id.txtTrangthai);
        ImageView imgHinh = (ImageView) convertView.findViewById(R.id.imageviewHinh);
        ImageView imgCham = (ImageView) convertView.findViewById(R.id.imgCham);

        // gán giá trị
        hotelOrdered ht = hotelList.get(position);

        txtTen.setText(ht.getTen());
        txtThanhpho.setText(ht.getThanhpho());
        txtTrangthai.setText(ht.getTrangthai());
        imgHinh.setImageResource(ht.getHinh());
        imgCham.setImageResource(ht.getCham());

        return convertView;
    }
}