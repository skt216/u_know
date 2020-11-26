package com.ar.hem.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.hem.baseclasses.UKnowBaseFragment;
import com.ar.hem.constants.AppConstants;
import com.ar.hem.uknow.R;

import androidx.cardview.widget.CardView;

public class ServiceProvidersFragment extends UKnowBaseFragment implements View.OnClickListener{

    private CardView doctorCardView;
    @Override
    public void init() {

    }

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view       = inflater.inflate(R.layout.fragment_service_providers, container, false);

        doctorCardView  =  view.findViewById(R.id.doctor_card_view);

        return view;
    }

    @Override
    public void initListeners() {

        doctorCardView.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void toDoOnDetach() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.doctor_card_view:
                if (getOnFragmentInteractionListener( ) != null) {
                    getOnFragmentInteractionListener( ).changeFragment(AppConstants.FragmentConstants.SHOW_DOCTOR_INFORMATION);

                }

                break;
        }
    }
}
