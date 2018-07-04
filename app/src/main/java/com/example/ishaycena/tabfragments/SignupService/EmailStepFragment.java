package com.example.ishaycena.tabfragments.SignupService;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Objects;

public class EmailStepFragment extends Fragment implements BlockingStep {
    private static final String TAG = "PageFragment";
    private static final int RC_SIGN_IN = 9001;

    public static EmailStepFragment newInstance(int page, boolean isLast) {
        Bundle args = new Bundle();
        args.putInt("page", page);

        if (isLast) {
            args.putBoolean("isLast", true);
        }
        final EmailStepFragment fragment = new EmailStepFragment();
        fragment.setArguments(args);

        return fragment;

    }

    // vars
    View view;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    // views
    SignInButton btnSignIn;
    Button btnSignOut;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.layout_register1_fragment, container, false);

            btnSignIn = view.findViewById(R.id.signup_btnSignIn);
            btnSignOut = view.findViewById(R.id.signup_btnSignOut);


            mAuth = FirebaseAuth.getInstance();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);

            // leave this code here!!!
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAuth.getCurrentUser() == null) {
                        // if user didn't choose an account, let him choose one
                        Intent intent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(intent, RC_SIGN_IN);
                    } else {
                        // otherwise, if he regrets clicking that account, he can choose again
                        signOut();
                    }
                }
            });

            if (mAuth.getCurrentUser() != null) {
                signOut();
            }
        }

        return view;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
//        FirebaseAuth.getInstance().signOut();
        mAuth.signOut();

        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "completed sign out", Toast.LENGTH_SHORT).show();
            }
        });

//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//                        updateUI(null);
//                    }
//                });

        Toast.makeText(getContext(), "User signed out", Toast.LENGTH_SHORT).show();
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "User logged in: " + Objects.requireNonNull(user).getEmail(), Toast.LENGTH_SHORT).show();

                            // signed in, go to next STEP
                            ((RegisterActivity) getActivity()).goToNextStep();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "User NOT logged", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            final int page = getArguments().getInt("page", 0);
        }

        if (getArguments().containsKey("isLast")) {
            // TODO add "completion page" logic here
        }
    }

    /*
    ===================== BLOCKING STEP LOGIC =====================
    * */

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {
        if (mAuth.getCurrentUser() == null) {
            // if the user didn't sign in
            return new VerificationError("You need to sign in!");
        } else {
            ((RegisterActivity) Objects.requireNonNull(getActivity())).user.emailAddress = mAuth.getCurrentUser().getEmail();
            return null;
        }
    }

    @Override
    public void onSelected() {
//        Toast.makeText(getContext(), "Selected: " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Log.d(TAG, "onError: error: " + error.getErrorMessage());

        Toast.makeText(getContext(), "Error: " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
}