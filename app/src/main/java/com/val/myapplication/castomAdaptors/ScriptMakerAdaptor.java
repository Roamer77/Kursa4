package com.val.myapplication.castomAdaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.val.myapplication.Entity.SensorForScen;
import com.val.myapplication.Entity.Sensors;
import com.val.myapplication.R;

import java.util.List;

public class ScriptMakerAdaptor extends BaseAdapter {
    private List<SensorForScen> data;
    private Context context;
    private LayoutInflater layoutInflater;


    public ScriptMakerAdaptor(List<SensorForScen> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.script_listview_item,null,true);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView) convertView.findViewById(R.id.ScriptMaker_tv);
            viewHolder.checkBox=(CheckBox) convertView.findViewById(R.id.ScriptMaker_cb);
            convertView.setTag(viewHolder);
        }else {
           viewHolder=(ViewHolder)convertView.getTag();
        }
        SensorForScen sensors=this.data.get(position);
        viewHolder.textView.setText(sensors.getSensorName());
        viewHolder.checkBox.setChecked(sensors.getIsSelected());
        viewHolder.checkBox.setTag(R.integer.btnplusview,convertView);
        viewHolder.checkBox.setTag(position);
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tempview = (View) viewHolder.checkBox.getTag(R.integer.btnplusview);
                Integer pos = (Integer)  viewHolder.checkBox.getTag();
                Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(data.get(pos).getIsSelected()){
                    data.get(pos).setIsSelected(false);
                }else {
                    data.get(pos).setIsSelected(true);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView textView;
        CheckBox checkBox;
    }

    public List<SensorForScen> getData() {
        return data;
    }
}
