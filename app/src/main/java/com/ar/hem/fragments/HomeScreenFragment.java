package com.ar.hem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.hem.baseclasses.UKnowBaseFragment;
import com.ar.hem.uknow.R;

public class HomeScreenFragment extends UKnowBaseFragment {
    @Override
    public void init() {

    }

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);
        return view;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void toDoOnDetach() {

    }
}
