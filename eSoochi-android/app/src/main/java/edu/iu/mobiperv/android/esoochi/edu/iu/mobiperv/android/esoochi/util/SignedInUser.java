package edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goshenoy on 9/14/16.
 */
public class SignedInUser {

    private static GoogleSignInAccount mGoogleAccount;

    private static String userId;

    private static List<JSONObject> groups = new ArrayList<JSONObject>();

    private static SignedInUser instance = null;

    protected SignedInUser() {}

    public static SignedInUser getLoggedInUser(GoogleSignInAccount account) {
        if(instance == null) {
            instance = new SignedInUser();
        }

        // set the google-signin-account
        if(account != null) {
            mGoogleAccount = account;
        }



        // return the instance
        return instance;
    }

    public static List<JSONObject> getGroups() {
        return groups;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        SignedInUser.userId = userId;
    }

    public GoogleSignInAccount getAccount() {
        return mGoogleAccount;
    }
}
