package com.ar.hem.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ar.hem.baseclasses.UKnowBaseFragment;
import com.ar.hem.constants.AppConstants;
import com.ar.hem.models.DoctorInfoModel;
import com.ar.hem.uknow.MainActivity;
import com.ar.hem.uknow.R;
import com.ar.hem.utils.UKnowUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import static com.ar.hem.utils.UKnowUtils.disableButtonView;
import static com.ar.hem.utils.UKnowUtils.enableButtonView;

public class DoctorDetailsFragment extends UKnowBaseFragment implements View.OnClickListener {

    private EditText firstName, lastName, email, qualificationDetails, specialisationEditText, PhoneNumber;
    private TextView termsConditions, privacyPolicy, iAgreeTc, iAgreePp;
    private CheckBox tcCheckbox, privacyCheckbox;
    private TextInputLayout fNameError, lNameError, emailError, specialisationError, qualificationError, PhoneError;
    private MaterialButton SubmitBtn;
    private String PhoneNumberValue;
    private DoctorInfoModel doctorInfoModel;


    @Override
    public void init() {

    }

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.doctor_detail_fragment_layout, container, false);

        firstName = view.findViewById(R.id.edit_first_name);
        lastName = view.findViewById(R.id.edit_last_name);
        email = view.findViewById(R.id.edit_email_address);
        qualificationDetails = view.findViewById(R.id.qualification_editText);
        specialisationEditText = view.findViewById(R.id.specialisation_editText);
        PhoneNumber = view.findViewById(R.id.edit_phone_number);

        fNameError = view.findViewById(R.id.fname_error);
        lNameError = view.findViewById(R.id.lname_error);
        emailError = view.findViewById(R.id.email_error);
        PhoneError = view.findViewById(R.id.phone_error);
        specialisationError = view.findViewById(R.id.specialisation_error);
        qualificationError = view.findViewById(R.id.qualification_error);
        SubmitBtn = view.findViewById(R.id.button_submit);

        termsConditions = view.findViewById(R.id.ll_terms_conditions);
        privacyPolicy = view.findViewById(R.id.ll_privacy_policy);
        iAgreeTc = view.findViewById(R.id.i_agree_tc);
        iAgreePp = view.findViewById(R.id.i_agree_pp);
        tcCheckbox = view.findViewById(R.id.tc_checkbox);
        privacyCheckbox = view.findViewById(R.id.privacy_checkbox);

        firstName.addTextChangedListener(new CustomTextWatcher(firstName));
        lastName.addTextChangedListener(new CustomTextWatcher(lastName));
        email.addTextChangedListener(new CustomTextWatcher(email));
        qualificationDetails.addTextChangedListener(new CustomTextWatcher(email));
        specialisationEditText.addTextChangedListener(new CustomTextWatcher(email));
        PhoneNumber.addTextChangedListener(new CustomTextWatcher(PhoneNumber));

        disableButtonView(SubmitBtn);

        tcCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!validateDetails( )) {
                    if (!tcCheckbox.isChecked( )) {
                        disableButtonView(SubmitBtn);
                    }

                }
            }
        });

        privacyCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!validateDetails( )) {
                    if (!privacyCheckbox.isChecked( )) {
                        disableButtonView(SubmitBtn);
                    }

                }
            }
        });


        return view;
    }

    @Override
    public void initListeners() {

        SubmitBtn.setOnClickListener(this);
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

            case R.id.button_submit:
                Log.d("skt", "Submit Button");
                if (getOnFragmentInteractionListener( ) != null) {
                    doctorInfoModel = new DoctorInfoModel();
                    doctorInfoModel.setFirstName(firstName.getText().toString());
                    doctorInfoModel.setLastName(lastName.getText().toString());
                    doctorInfoModel.setEmailAddress(email.getText().toString());
                    doctorInfoModel.setExpertise(specialisationEditText.getText().toString());
                    doctorInfoModel.setQualification(qualificationDetails.getText().toString());
                    doctorInfoModel.setTelephoneNumber(PhoneNumber.getText().toString());
                    ((MainActivity) getActivity( )).setDoctorInfoModel(doctorInfoModel);
                    getOnFragmentInteractionListener( ).changeFragment(AppConstants.FragmentConstants.VERIFY_DOCTOR_PHONE_FRAGMENT);

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
                case R.id.edit_first_name:
                    validateAndDisableButton(SubmitBtn);
                    fNameError.setErrorEnabled(false);
                    break;
                case R.id.edit_last_name:
                    validateAndDisableButton(SubmitBtn);
                    lNameError.setErrorEnabled(false);
                    break;
                case R.id.edit_email_address:
                    validateAndDisableButton(SubmitBtn);
                    emailError.setErrorEnabled(false);
                    break;
                case R.id.qualification_editText:
                    validateAndDisableButton(SubmitBtn);
                    qualificationError.setErrorEnabled(false);
                    break;
                case R.id.specialisation_editText:
                    validateAndDisableButton(SubmitBtn);
                    specialisationError.setErrorEnabled(false);
                    break;
                case R.id.edit_phone_number:
                    PhoneNumberValue = PhoneNumber.getText( ).toString( );
                    validateAndDisableButton(SubmitBtn);
                    PhoneError.setErrorEnabled(false);
                    break;

            }

        }

    }

    /**
     * METHOD for disable and enable signup button
     *
     * @param button The name of the desired item.
     */

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

        if (UKnowUtils.isNullOrEmpty(firstName)) {
            return false;
        }
        if (UKnowUtils.isNullOrEmpty(lastName)) {
            return false;
        }
        if (UKnowUtils.isNullOrEmpty(email)) {
            return false;
        }
        if (UKnowUtils.isNullOrEmpty(qualificationDetails)) {
            return false;
        }
        if (UKnowUtils.isNullOrEmpty(specialisationEditText)) {
            return false;
        }
        if (UKnowUtils.isNullOrEmpty(PhoneNumber)) {
            return false;
        }
        if (!tcCheckbox.isChecked( )) {
            return false;
        }
        if (!privacyCheckbox.isChecked( )) {
            return false;
        }
        enableButtonView(SubmitBtn);
        return true;
    }
}
