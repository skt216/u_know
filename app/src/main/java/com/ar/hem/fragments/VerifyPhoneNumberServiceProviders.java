package com.ar.hem.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ar.hem.baseclasses.UKnowBaseFragment;
import com.ar.hem.constants.AppConstants;
import com.ar.hem.models.DoctorInfoModel;
import com.ar.hem.uknow.MainActivity;
import com.ar.hem.uknow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

public class VerifyPhoneNumberServiceProviders extends UKnowBaseFragment implements View.OnClickListener {

    private FirebaseAuth          mAuth;
    private String                verificationCodeBySystem;
    private MaterialButton        verifyOtpBtn;
    private TextInputLayout       verifyPhoneOtpError;
    private EditText              verifyPhoneOtpValue;
    private    DoctorInfoModel    doctorInfoModel;
    private FirebaseFirestore     db;
    @Override
    public void init() {

    }

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_phonenum_service_providers, container, false);
        verifyOtpBtn             = view.findViewById(R.id.verify_otp);
        verifyPhoneOtpError      = view.findViewById(R.id.verify_phone_otp_error);
        verifyPhoneOtpValue      = view.findViewById(R.id.verify_phone_otp_value);

        mAuth                    = FirebaseAuth.getInstance( );
        db                       = FirebaseFirestore.getInstance();


        doctorInfoModel        = ((MainActivity)getActivity()).getDoctorInfoModel();
        sendVerificationCodeToUser(doctorInfoModel.getTelephoneNumber());
        Log.d("skt","a value" + doctorInfoModel.getTelephoneNumber());

        verifyOtpBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId( )) {

            case R.id.verify_otp:
                String otpCode = verifyPhoneOtpValue.getText( ).toString( );
                Log.d("skt", "OTP" + otpCode);

                if (otpCode.isEmpty( ) || otpCode.length( ) < 6) {
                    verifyPhoneOtpError.setError("Wrong OTP...");
                    verifyPhoneOtpError.requestFocus( );
                    return;
                }
                verifyCode(otpCode);
                break;
        }
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        Log.d("skt", "VerifyPhoneNumberServiceProviders" + phoneNumber);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity((Activity) getContext( ))   // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build( );
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks( ) {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
            String code = phoneAuthCredential.getSmsCode( );
            Log.d(TAG, "onVerificationCompleted:" + code);
            if (code != null) {

                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Log.w(TAG, "onVerificationFailed", e);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);;
            verificationCodeBySystem = s;
        }
    };

    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) getContext( ), new OnCompleteListener<AuthResult>( ) {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful( )) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult( ).getUser( );
                            Toast.makeText(getContext( ), "success", Toast.LENGTH_LONG).show( );
                            addDataToFireStore();
                            Log.d(TAG, "signInWithCredential:success" + task);
//                            if (getOnFragmentInteractionListener( ) != null) {
//                                Log.d("skt","HOME FRAGMENT");
//                                getOnFragmentInteractionListener( ).changeFragment(AppConstants.FragmentConstants.SHOW_HOME_SCREEN);
//
//                            }
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException( ));
                            if (task.getException( ) instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });

    }

    private void addDataToFireStore(){

        // Create a new user with a first and last name
        Map<String, Object> docInfo = new HashMap<>();
        docInfo.put("firstName",       doctorInfoModel.getFirstName());
        docInfo.put("lastName",        doctorInfoModel.getLastName());
        docInfo.put("emailAddress",    doctorInfoModel.getEmailAddress());
        docInfo.put("qualification",   doctorInfoModel.getQualification());
        docInfo.put("expertise",       doctorInfoModel.getExpertise());
        docInfo.put("TelephoneNumber", doctorInfoModel.getTelephoneNumber());

        db.collection("doctorInfo").document("INFO").set(docInfo)

                 .addOnCompleteListener(new OnCompleteListener<Void>( ) {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {

                         if(task.isSuccessful()){

                             Toast.makeText(getContext( ), "Values Added", Toast.LENGTH_LONG).show( );

                         }

                     }
                 });
    }

}
