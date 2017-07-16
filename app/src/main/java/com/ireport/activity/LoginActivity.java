package com.ireport.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ireport.R;
import com.ireport.activity.officer.OfficerMain;
import com.ireport.activity.user.MainActivity;
import com.ireport.apiService.LoginService;
import com.ireport.constants.ApiConstants;
import com.ireport.constants.CommonConstants;
import com.ireport.gcm.MyFirebaseInstanceIDService;
import com.ireport.model.responseModel.userModel.loginModel.LoginMainModel;
import com.ireport.servicesAndGeneralInterface.IntentAndFragmentService;
import com.ireport.utils.CheckInternet;
import com.ireport.utils.ProgressLoader;
import com.ireport.utils.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Manoj on 11/23/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    GoogleCloudMessaging gcmObj;
    String regId = "null";
    GoogleApiClient mGoogleApiClient;
    int RC_SIGN_IN = 0;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    LoginButton loginButton;
    CallbackManager callbackManager;
    LoginService loginService;
    ProgressLoader progressLoader;
    CheckInternet checkInternet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.login_activity);

        /**
         * retrofit initialization and also the service
         * interface of retrofit
         * */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();
        loginService = retrofit.create(LoginService.class);

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("login succesfull", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("login canceled", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("login error", "facebook:onError", error);
                // ...
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Login success", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Login failed", "onAuthStateChanged:signed_out");
                }
            }
        };

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        progressLoader = new ProgressLoader();
        checkInternet = new CheckInternet(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.sign_in_button:
                signIn();
                break;

        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //handleSignInResult(result);

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     *
     * get the data after successful facebook login and the
     * request for Oauth authentication if authorized
     * then move to next fragment and pre load the
     * user  info
     *
     * */
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("auth success", "signInWithCredential:onComplete:" + task.getResult().getUser().getEmail());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        /**
                         * move to the next activity where user can enter his info
                         * and proceed next
                         * */
                        if(checkInternet.isConnectingToInternet()){
                            new LoginAction(task.getResult().getUser().getEmail(),"1","0").execute();
                        }else {
                            Toast.makeText(LoginActivity.this, "Please turn on the Internet connectio", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
    }

    /**
     *
     * get the data after successful g+ login and the
     * request for Oauth authentication if authorized
     * then move to next fragment and pre load the
     * user  info
     *
     * */
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        Log.d("acct id", "firebaseAuthWithGoogle:" + acct.getEmail());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("success login", "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        /**
                         * move to the next activity where user can enter his info
                         * and proceed next
                         * */
                        if(checkInternet.isConnectingToInternet()){
                            new LoginAction(acct.getEmail(),"2","1").execute();
                        }else {
                            Toast.makeText(LoginActivity.this, "Please turn on the Internet connectio", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private class LoginAction extends AsyncTask<String,String,String>{

        String email_id,account_type,social_account;
        public LoginAction(String email, String a_t, String s_a) {
            email_id = email;
            account_type = a_t;
            social_account = s_a;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressLoader.showProgress(LoginActivity.this);
        }

        @Override
        protected String doInBackground(String... strings) {
            MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
            myFirebaseInstanceIDService.onTokenRefresh();

            Call<LoginMainModel> loginActivity = loginService.loginActivity(
                                                    " ",
                                                    " ",
                                                    email_id,
                                                    email_id,
                                                    account_type,
                                                    social_account,
                                                    " ",
                                                    FirebaseInstanceId.getInstance().getToken(),
                                                    "android");
            loginActivity.enqueue(new Callback<LoginMainModel>() {
                @Override
                public void onResponse(Call<LoginMainModel> call, Response<LoginMainModel> response) {
                    progressLoader.DismissProgress();
                    if(response.isSuccess()){
                        if(response.body().getStatus().equals("success")){

                           SharedPreferencesUtil.getInstance(LoginActivity.this).setUserPreferenceData(
                                   response.body().getResponse().getUserId(),
                                   null,
                                   account_type,
                                   CommonConstants.deviceId,
                                   email_id,
                                   email_id,
                                   null);

                           SharedPreferencesUtil.getInstance(LoginActivity.this).setUserSettings(
                                   "true",
                                   "true",
                                   "false");

                           /**
                            * Once We have the data login to main screen
                            * */

                            if(SharedPreferencesUtil.getInstance(LoginActivity.this).getloginType().equals("1")){
                                /**
                                 * move to user main activity
                                 * */
                                CommonConstants.FragmentSwitchCallMain = CommonConstants.MainFragment;
                                IntentAndFragmentService.intentDisplay(LoginActivity.this,MainActivity.class,null);
                                finish();
                            }else {
                                /**
                                 * move to officer main activity
                                 * */
                                CommonConstants.FragmentSwitchCallMain = CommonConstants.MainFragment;
                                IntentAndFragmentService.intentDisplay(LoginActivity.this,OfficerMain.class,null);
                                finish();
                            }


                        }else {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Failed to register the user please try agin later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginMainModel> call, Throwable t) {
                    progressLoader.DismissProgress();
                }
            });
            return null;
        }
    }

}
