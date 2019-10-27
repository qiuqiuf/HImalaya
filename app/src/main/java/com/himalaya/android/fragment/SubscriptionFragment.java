package com.himalaya.android.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.himalaya.android.R;
import com.himalaya.android.base.BaseFragment;

public class SubscriptionFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView=layoutInflater.inflate(R.layout.fragment_subscription,container,false);
        return rootView;
    }
}
