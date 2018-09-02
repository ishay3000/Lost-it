package com.example.ishaycena.tabfragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    // widgets
    RelativeLayout mRelativeLayout;

    // vars
    AnimationDrawable mAnimationDrawable;

    // google sign in vars
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setViews();
        startAnimation();
        setupGoogle();
    }

    private void setupGoogle() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setViews() {
        mRelativeLayout = findViewById(R.id.welcome_relative_layout);
    }

    private void startAnimation() {
        mAnimationDrawable = (AnimationDrawable) mRelativeLayout.getBackground();

        mAnimationDrawable.setEnterFadeDuration(5000);
        mAnimationDrawable.setExitFadeDuration(2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAnimationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mAnimationDrawable.stop();
    }
}
