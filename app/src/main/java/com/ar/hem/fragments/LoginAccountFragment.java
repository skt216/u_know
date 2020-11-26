package com.ar.hem.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.hem.baseclasses.UKnowBaseFragment;
import com.ar.hem.constants.AppConstants;
import com.ar.hem.uknow.R;
import com.google.android.material.button.MaterialButton;

public class LoginAccountFragment extends UKnowBaseFragment implements View.OnClickListener {

    private MaterialButton loginUser, loginServiceProvider;

    @Override
    public void init() {

    }

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_screen_layout, container, false);

        loginUser = view.findViewById(R.id.login_user);
        loginServiceProvider = view.findViewById(R.id.login_service_provider);

        return view;
    }

    @Override
    public void initListeners() {

        loginUser.setOnClickListener(this);
        loginServiceProvider.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void toDoOnDetach() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId( )) {

            case R.id.login_user:
                if (getOnFragmentInteractionListener( ) != null) {
                    getOnFragmentInteractionListener( ).changeFragment(AppConstants.FragmentConstants.SHOW_USER_LOGIN);

                }
                break;

            case R.id.login_service_provider:

                if (getOnFragmentInteractionListener( ) != null) {
                    getOnFragmentInteractionListener( ).changeFragment(AppConstants.FragmentConstants.SHOW_SERVICE_PROVIDERS);

                }
                break;


        }
    }
}
