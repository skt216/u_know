package com.ar.hem.uknow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ar.hem.constants.AppConstants;
import com.ar.hem.fragments.DoctorDetailsFragment;
import com.ar.hem.fragments.HomeScreenFragment;
import com.ar.hem.fragments.LoginAccountFragment;
import com.ar.hem.fragments.ServiceProvidersFragment;
import com.ar.hem.fragments.UserAccountCreateFragment;
import com.ar.hem.fragments.VerifyPhoneNumberFragment;
import com.ar.hem.fragments.VerifyPhoneNumberServiceProviders;
import com.ar.hem.interfaces.OnFragmentInteractionListener;
import com.ar.hem.models.DoctorInfoModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private DoctorInfoModel doctorInfoModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        showUserSelectionPage();
    }


    private void initViews() {

    }

    public DoctorInfoModel getDoctorInfoModel() {
        return doctorInfoModel;
    }

    public void setDoctorInfoModel(DoctorInfoModel doctorInfoModel) {
        this.doctorInfoModel = doctorInfoModel;
    }

    private void  showUserSelectionPage(){

        FragmentTransaction fragment = getSupportFragmentManager( ).beginTransaction( );
        LoginAccountFragment loginAccountFragment = new LoginAccountFragment( );
        loginAccountFragment.setOnFragmentInteractionListener(onFragmentInteractionListener);
        fragment.replace(R.id.content_frame, loginAccountFragment);
        fragment.addToBackStack("userLoginPage");
        fragment.commit( );

    }

    private void showUserLoginPage() {
        Log.d("skt", "print second");
        FragmentTransaction fragment = getSupportFragmentManager( ).beginTransaction( );
        UserAccountCreateFragment userAccountCreateFragment = new UserAccountCreateFragment( );
        userAccountCreateFragment.setOnFragmentInteractionListener(onFragmentInteractionListener);
        fragment.replace(R.id.content_frame, userAccountCreateFragment);
        fragment.addToBackStack("userLoginPage");
        fragment.commit( );


    }


    private void showServiceProviders(){
        FragmentTransaction fragment = getSupportFragmentManager( ).beginTransaction( );
        ServiceProvidersFragment serviceProvidersFragment = new ServiceProvidersFragment();
        serviceProvidersFragment.setOnFragmentInteractionListener(onFragmentInteractionListener);
        fragment.replace(R.id.content_frame, serviceProvidersFragment);
        fragment.addToBackStack("showServiceProviders");
        fragment.commit( );

    }

    private void showDoctorInfo(){
        FragmentTransaction fragment = getSupportFragmentManager( ).beginTransaction( );
        DoctorDetailsFragment doctorDetailsFragment = new DoctorDetailsFragment();
        doctorDetailsFragment.setOnFragmentInteractionListener(onFragmentInteractionListener);
        fragment.replace(R.id.content_frame, doctorDetailsFragment);
        fragment.addToBackStack("doctorDetailsInfo");
        fragment.commit( );

    }

    private void ShowVerifyPhonePage(String PhoneNumber) {
        Log.d("skt", "print fourth" + PhoneNumber);
        FragmentTransaction fragment = getSupportFragmentManager( ).beginTransaction( );
        VerifyPhoneNumberFragment verifyPhoneNumberFragment = new VerifyPhoneNumberFragment( );
        verifyPhoneNumberFragment.setPhoneNumber(PhoneNumber);
        verifyPhoneNumberFragment.setOnFragmentInteractionListener(onFragmentInteractionListener);
        fragment.replace(R.id.content_frame, verifyPhoneNumberFragment);
        fragment.addToBackStack("verifyPhoneNumber");
        fragment.commit( );


    }

    private void ShowServiceProviderVerifyPhonePage() {
        FragmentTransaction fragment = getSupportFragmentManager( ).beginTransaction( );
        VerifyPhoneNumberServiceProviders verifyPhoneNumberServiceProviders = new VerifyPhoneNumberServiceProviders( );
        verifyPhoneNumberServiceProviders.setOnFragmentInteractionListener(onFragmentInteractionListener);
        fragment.replace(R.id.content_frame, verifyPhoneNumberServiceProviders);
        fragment.addToBackStack("verifyPhoneNumberServiceProvider");
        fragment.commit( );


    }

    private void showHomeScreen() {
        FragmentTransaction fragment = getSupportFragmentManager( ).beginTransaction( );
        HomeScreenFragment homeScreenFragment = new HomeScreenFragment( );
        fragment.replace(R.id.content_frame, homeScreenFragment);
        fragment.addToBackStack("showHomeScreen");
        fragment.commit( );
    }

    private OnFragmentInteractionListener onFragmentInteractionListener = new OnFragmentInteractionListener( ) {
        @Override
        public void changeFragment(String tag) {
            switch (tag) {
                case AppConstants.FragmentConstants.SHOW_HOME_SCREEN:
                    Log.d("skt", "HomeScreen");
                    showHomeScreen();
                    break;
                case AppConstants.FragmentConstants.SHOW_USER_LOGIN:
                    Log.d("skt", "showUserLogin");
                    showUserLoginPage();
                    break;
                case AppConstants.FragmentConstants.SHOW_SERVICE_PROVIDERS:
                    Log.d("skt", "showServiceProvider");
                    showServiceProviders();
                    break;
                case AppConstants.FragmentConstants.SHOW_DOCTOR_INFORMATION:
                    Log.d("skt", "doctorDetailsInfo");
                    showDoctorInfo();
                    break;
                case AppConstants.FragmentConstants.VERIFY_DOCTOR_PHONE_FRAGMENT:
                    ShowServiceProviderVerifyPhonePage();
                    break;
                default:
                    Log.d("skt", "Clicked item = ");
                    break;
            }
        }

        @Override
        public void sendData(String... value) {
            Log.d("skt", "Clicked item = " + value);
            if (value != null && value.length > 0) {
                if (value[0].equals(AppConstants.DataTransferConstants.VERIFY_PHONE_FRAGMENT)) {
                    ShowVerifyPhonePage(value[1]);
                }

            }

        }
    };
}