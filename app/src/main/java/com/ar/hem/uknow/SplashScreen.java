package com.ar.hem.uknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import static android.content.ContentValues.TAG;

public class SplashScreen extends AppCompatActivity {

    int SPLASH_TIME_OUT = 2000;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private TextView                helloTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        initViews();
        moveToScreenAfterCheck();
    }


    private void initViews() {

        helloTextView  = findViewById(R.id.hello_text_view);
        helloTextView.setText("Welcome");

    }


    private void moveToScreenAfterCheck() {

            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3600)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
            fetchValuesRemoteConfig();

    }

    private  void fetchValuesRemoteConfig(){

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);

                            Toast.makeText(SplashScreen.this, "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SplashScreen.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }


                         displayWelcomeMessage();
                    }
                });
    }

    private void displayWelcomeMessage() {

        try {

            Thread.sleep(SPLASH_TIME_OUT);
            Intent in = null;

            in = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(in);
            finish( );
        } catch (InterruptedException e) {
            finish( );
        }
            Intent in = null;
        helloTextView.setText(mFirebaseRemoteConfig.getString("welcome_text"));
    }
}