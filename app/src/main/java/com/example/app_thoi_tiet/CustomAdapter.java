package com.example.app_thoi_tiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_listview, null);

        ThoiTiet thoiTiet = arrayList.get(i);
        TextView txtDay = (TextView) convertView.findViewById(R.id.textviewNgay);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.textviewTrangthai);
        TextView txtMaxtemp = (TextView) convertView.findViewById(R.id.textviewMaxtemp);
        //TextView txtMintemp = (TextView) convertView.findViewById(R.id.textviewMintemp);
        ImageView imgView = (ImageView)  convertView.findViewById(R.id.imageviewtrangthai);

        txtDay.setText(thoiTiet.Day);
        txtStatus.setText(thoiTiet.Status);
        txtMaxtemp.setText(thoiTiet.Maxtemp+"°C");
        //txtMintemp.setText(thoiTiet.Mintemp+"°C");

        Picasso.with(context).load("https://openweathermap.org/img/wn/"+thoiTiet.Image+".png").into(imgView);
        return convertView;
    }
}
