package edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by goshenoy on 9/14/16.
 */
public class SignedInUser {

    private static GoogleSignInAccount mGoogleAccount;

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

    public GoogleSignInAccount getAccount() {
        return mGoogleAccount;
    }
}
