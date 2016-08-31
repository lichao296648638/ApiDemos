package com.example.animtest.transition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.animtest.R;


/**
 * Created by Administrator on 2016/8/31.
 */

public class TransitionAdapter extends BaseAdapter {
    Context mContext;

    public TransitionAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.item_transition, null);
        return convertView;
    }
}
