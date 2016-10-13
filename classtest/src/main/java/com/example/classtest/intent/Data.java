package com.example.classtest.intent;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lichao on 16/10/9.
 * 用作在Activity间传递的对象
 */

public class Data implements Parcelable{
    //某一项数据
    int i = 10;


    public static final Creator<Data> CREATOR = new Creator<Data>() {

        @Override
        public Data createFromParcel(Parcel in) {
            //实例化一个保存之前数据的对象
            Data mData = new Data();
            //从Parcel中读取之前写入的数据
            mData.i = in.readInt();
            //返回
            return mData;
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //把需要保存的数据写入Parcel中
        dest.writeInt(i);
    }
}
