package com.himalaya.android.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.himalaya.android.R;
import com.himalaya.android.base.BaseFragment;

public class RecommendFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView=layoutInflater.inflate(R.layout.fragment_recommend,container,false);
        return rootView;
    }
}
