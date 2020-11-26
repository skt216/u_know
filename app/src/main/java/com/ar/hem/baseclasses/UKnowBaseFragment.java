package com.ar.hem.baseclasses;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.ar.hem.interfaces.OnFragmentInteractionListener;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Created by U47736 on 6/6/2017.
 */

public abstract class UKnowBaseFragment extends Fragment {

    private Context mContext;
    private Activity mActivity;

    public Toolbar appToolbar;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        View rootView                       = initViews(inflater, container, savedInstanceState);
        initListeners();
        initData();
        return rootView;//super.onCreateView(inflater, container, savedInstanceState)
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setCurrentContext(context);
    }

    @Override
    public void onDetach() {
        toDoOnDetach();
        super.onDetach();
    }

    public void setCurrentContext(Context context) {
        mContext                            = context;
    }

    public Context getCurrentContext() {
        if(mContext != null)
            return mContext;
        else
            throw new NullPointerException("Context is not initialized");
    }

    public void setData(Object data){
        UKnowBaseActivity activity        = (UKnowBaseActivity) getActivity();
        if (activity != null) {
            activity.setData(data);
        }
    }

    public Object getData() {
        UKnowBaseActivity activity        = (UKnowBaseActivity) getActivity();
        if (activity != null) {
            return activity.getData();
        }

        return null;
    }

    public Activity getActivityParent() {
        if(mActivity == null)
            mActivity                       = getActivity();

        return mActivity;
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener l) {
        onFragmentInteractionListener               = l;
    }

    public OnFragmentInteractionListener getOnFragmentInteractionListener() {
        return onFragmentInteractionListener;
    }

    public void initToolbarMenu() {
        if (appToolbar != null){

        }
    }

    public abstract void init();
    public abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void initListeners();
    public abstract void initData();
    public abstract void toDoOnDetach();
}
