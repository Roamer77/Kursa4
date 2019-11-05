package com.val.myapplication.castomAdaptors;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.val.myapplication.R;
import com.val.myapplication.Entity.Sensors;

import java.util.List;

public class CustomLayOutAdaptor extends BaseAdapter {

    private List<Sensors> listOfData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomLayOutAdaptor(List<Sensors> listOfData, Context context) {
        this.listOfData = listOfData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listOfData.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.list_item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.imageView_flag);
            viewHolder.sensorNameView =(TextView)convertView.findViewById(R.id.textView_countryName);
            viewHolder.someInfoView =(TextView) convertView.findViewById(R.id.textView_currentState);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Sensors sensors =this.listOfData.get(position);
        viewHolder.sensorNameView.setText(sensors.getSensorName());
        viewHolder.imageView.setImageResource(getImageIdByName(sensors.getSensorImgName()));
        viewHolder.someInfoView.setText("Текущее состояние: "+sensors.getSensorState());
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.slide_left_listview);
        convertView.startAnimation(animation);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.e("MyTag","Меня уведомили");
    }

    private   int getImageIdByName(String name){
        String  pkgName=context.getPackageName();
        int resId=context.getResources().getIdentifier(name,"mipmap",pkgName);
        Log.i("CustomListView", "Res Name: "+ name+"==> Res ID = "+ resId);
        return resId;
    }
   static class ViewHolder{
        ImageView imageView;
        TextView sensorNameView;
        TextView someInfoView;
    }
}
