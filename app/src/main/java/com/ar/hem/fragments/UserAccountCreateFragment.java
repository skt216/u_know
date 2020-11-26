package com.ar.hem.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ar.hem.baseclasses.UKnowBaseFragment;
import com.ar.hem.constants.AppConstants;
import com.ar.hem.uknow.R;
import com.ar.hem.utils.UKnowUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.ar.hem.utils.UKnowUtils.disableButtonView;
import static com.ar.hem.utils.UKnowUtils.enableButtonView;


public class UserAccountCreateFragment extends UKnowBaseFragment implements View.OnClickListener {

    private MaterialButton verifyPhoneNumBtn;
    private TextInputEditText phoneNumEditText, userName;
    private String phoneNumValue;
    private TextInputLayout phoneError, usernameError;

    @Override
    public void init() {

    }

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_account_create, container, false);


        verifyPhoneNumBtn       = view.findViewById(R.id.verify_phone_num);
        phoneNumEditText        = view.findViewById(R.id.phone_num_edtTxt);
        phoneError              = view.findViewById(R.id.phone_error);
        usernameError           = view.findViewById(R.id.username_error);
        userName                = view.findViewById(R.id.username);


        verifyPhoneNumBtn.setOnClickListener(this);
        phoneNumEditText.setOnClickListener(this);
        phoneNumEditText.addTextChangedListener(new CustomTextWatcher(phoneNumEditText));
        userName.addTextChangedListener(new CustomTextWatcher(userName));

        disableButtonView(verifyPhoneNumBtn);
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

            case R.id.verify_phone_num:
                Log.d("skt", "print first");
                if (getOnFragmentInteractionListener( ) != null) {
                    getOnFragmentInteractionListener( ).sendData(AppConstants.DataTransferConstants.VERIFY_PHONE_FRAGMENT, phoneNumValue);

                }
                break;
        }

    }

    class CustomTextWatcher implements TextWatcher {
        private View view;

        public CustomTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId( )) {

                case R.id.username:
                    validateAndDisableButton(verifyPhoneNumBtn);
                    usernameError.setErrorEnabled(false);
                    break;
                case R.id.phone_num_edtTxt:
                    validateAndDisableButton(verifyPhoneNumBtn);
                    phoneError.setErrorEnabled(false);
                    phoneNumValue = String.valueOf(phoneNumEditText.getText( ));
                    Log.d("phonevalue", "vinu" + phoneNumValue);
                    break;

            }

        }

    }

    private void validateAndDisableButton(Button button) {
        if (!validateDetails( )) {
            disableButtonView(button);
        }
    }

    /**
     * Validating the user input details.
     *
     * @return the value of true or false based on validation
     */
    private boolean validateDetails() {

        if (UKnowUtils.isNullOrEmpty(phoneNumEditText)) {
            return false;
        }

        if (UKnowUtils.isNullOrEmpty(userName)) {
            return false;
        }

        enableButtonView(verifyPhoneNumBtn);
        return true;
    }


}
