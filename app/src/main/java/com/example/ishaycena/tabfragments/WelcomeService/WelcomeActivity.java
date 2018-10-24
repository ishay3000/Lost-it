package com.example.ishaycena.tabfragments.WelcomeService;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.MainService.MainActivity;
import com.example.ishaycena.tabfragments.R;
import com.example.ishaycena.tabfragments.SignupService.RegisterActivity;
import com.example.ishaycena.tabfragments.UserSingleton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "WelcomeActivity";
    // widgets
    RelativeLayout mRelativeLayout;
    Button mBtnSignIn, mBtnRegister;

    // vars
    AnimationDrawable mAnimationDrawable;

    // google sign in vars
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setListeners();
        startAnimation();
        setupGoogle();
    }

    private void setupGoogle() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getApplicationContext()), gso);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            // if user is logged in, sign them out


            mAuth.signOut();

            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "completed sign out", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setListeners() {
        mRelativeLayout = findViewById(R.id.welcome_relative_layout);
        mBtnSignIn = findViewById(R.id.welcome_btn_sign_in);
        mBtnRegister = findViewById(R.id.welcome_btn_register);

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });
    }

    /**
     * signing in using the google sign in intent
     */
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

    private void firebaseAuthWithGoogle(GoogleSignInAccount account, final RegisterActivity.User userTemplate) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            UserSingleton.setUserInstance(userTemplate);
                            // go to main activity
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(WelcomeActivity.this, "User NOT logged", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void authenticateUser(final GoogleSignInAccount account) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String commaSeparatedEmail = account.getEmail();
        commaSeparatedEmail = Objects.requireNonNull(commaSeparatedEmail).replace('.', ',');

        databaseReference
                .child("Users")
                .child(commaSeparatedEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            RegisterActivity.User user = dataSnapshot.getValue(RegisterActivity.User.class);
                            // such user exists, return true
                            firebaseAuthWithGoogle(account, user);
                        } else {
                            // user does not exist, need to register, return false
                            Toast.makeText(WelcomeActivity.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                authenticateUser(account);
                /*
                if(user exists in database){
                firebaseAuthWithGoogle(account);
                }
                */
                /*else{
                display register message
                * }*/
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
}
