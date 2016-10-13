package com.example.classtest.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.classtest.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/10.
 * @description 自定义ListView适配器
 */

public class MyAdapter extends BaseAdapter{
    //数据源
    ArrayList<String> mData;
    //上下文
    Context mContext;
    /**
     * @description 本适配器构造器，传入字符串列表以装载数据
     *
     * @param data    载入的数据源
     * @param context 上下文
     */
    public MyAdapter(Context context, ArrayList<String> data) {
        mData = data;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //声明ViewHolder
        MyAdapter.ViewHolder holder = null;
        //若为第一次显示当前item
        if (convertView == null) {
            //获取布局加载器
            LayoutInflater inflater = LayoutInflater.from(mContext);
            //使用布局加载器加载item布局
            convertView = inflater.inflate(R.layout.item_list, null);
            //保存ViewHolder的状态，存在当前convertView中
            holder = new MyAdapter.ViewHolder();
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            convertView.setTag(holder);
        }
        //否则
        else {
            holder = (MyAdapter.ViewHolder) convertView.getTag();
        }

        //从数据源中装载数据
        holder.tv_data.setText(mData.get(position));
        return convertView;
    }


    /**
     * 用于优化性能的ViewHolder，保存Item布局中的控件
     */
    public class ViewHolder {
        public TextView tv_data;//数据项
    }
}
