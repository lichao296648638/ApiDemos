package com.example.classtest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.classtest.R;

/**
 * Created by lichao on 16/10/10.
 * FragmentA
 */

public class FragmentC extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        Log.i("lichao", "C onCreateView");

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("lichao", "C onAttach");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("lichao", "C onCreate");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("lichao", "C onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("lichao", "C onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("lichao", "C onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("lichao", "C onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("lichao", "C onStop");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("lichao", "C onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("lichao", "C onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("lichao", "C onDetach");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("lichao", "C onSaveInstanceState");

    }

}
