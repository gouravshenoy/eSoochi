package edu.iu.mobiperv.android.esoochi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.Constants;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.SignedInUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserDetailsActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    // Text views
    private TextView mDispNameTxtView;
    private TextView mUserIdTxtView;
    private TextView mEmailTxtView;
    private TextView mGrpTxtView;

    // Image views
    private ImageView userImg;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount mGoogleAccount;
    private static final String TAG = "eSoochi.UserDetlActivty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Views
        mDispNameTxtView = (TextView) findViewById(R.id.displayname);
        mUserIdTxtView = (TextView) findViewById(R.id.userid);
        mEmailTxtView = (TextView) findViewById(R.id.email);
        userImg = (ImageView) findViewById(R.id.user_icon);

        // button listeners
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.proceed_btn).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]
    }

    @Override
    protected void onStart() {
        super.onStart();

        // get the google-account
        mGoogleAccount = SignedInUser.getLoggedInUser(null).getAccount();
        Log.d(TAG, "SignedInUser: " + mGoogleAccount.getDisplayName());

        Picasso.with(this).load(mGoogleAccount.getPhotoUrl()).into(userImg);
        mDispNameTxtView.setText(mGoogleAccount.getDisplayName());
        mUserIdTxtView.setText(mGoogleAccount.getId());
        mEmailTxtView.setText(mGoogleAccount.getEmail());

        JSONObject data = new JSONObject();
        JSONObject request = new JSONObject();

        try {
            data.put("googleId", mGoogleAccount.getId());
            data.put("photoUrl", mGoogleAccount.getPhotoUrl());
            data.put("emailAddress", mGoogleAccount.getEmail());
            data.put("fullName", mGoogleAccount.getDisplayName());
            request.put("user", data);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        Log.d(TAG, "Sending JSON data for Create User: " + request.toString());

        new CreateUserTask().execute(request.toString());
    }

    // [START signOut]
    private void signOut() {
        Log.d(TAG, "Initial Status:" + String.valueOf(mGoogleApiClient.isConnected()));
        if(mGoogleApiClient.isConnected()) {
            Log.d(TAG, "GoogleApiClient is connected!!");
        } else {
            mGoogleApiClient.connect();
            Log.d(TAG, "After trying to reconnect: " + String.valueOf(mGoogleApiClient.isConnected()));
        }

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    // [START_EXCLUDE]
                    Log.d(TAG, "Navigating to SigininActivity");
                    Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                    startActivity(i);
                    // [END_EXCLUDE]
                }
            });
    }
    // [END signOut]

    // [START proceed]
    private void proceed() {
        Log.d(TAG, "Starting List Activity");
        Intent i = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(i);
    }
    // [END proceed]

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.proceed_btn:
                proceed();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private class CreateUserTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            try {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, data[0]);

                Request request = new Request.Builder()
                        .url("http://54.85.51.216:8085/esoochi/rest/user")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            findViewById(R.id.proceed_btn).setVisibility(View.VISIBLE);
            try {
                JSONObject jsonObj = new JSONObject(json);
                SignedInUser.setUserId(jsonObj.getJSONObject("user").getString("id"));
                Log.d(TAG, "UserID: " + SignedInUser.getUserId());

                if(jsonObj.getJSONObject("user").has("groups")) {
                    Object groups = jsonObj.getJSONObject("user").get("groups");
                    if (groups instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) groups).length(); i++) {
                            SignedInUser.getGroups().add(((JSONArray) groups).getJSONObject(i));
                        }
                    } else {
                        SignedInUser.getGroups().add((JSONObject) groups);
                    }
                }
                Log.d(TAG, "Groups: " + SignedInUser.getGroups());
            } catch (JSONException e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }
    }

}
