package edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by goshenoy on 9/14/16.
 */
public class GoogleAuthClient {

    private static GoogleApiClient mGoogleApiClient;
    private static GoogleAuthClient instance = null;

    protected GoogleAuthClient() {}

    public static GoogleAuthClient getInstance(GoogleApiClient apiClient) {
        if(instance == null) {
            instance = new GoogleAuthClient();

            if(mGoogleApiClient == null) {
                mGoogleApiClient = apiClient;
            }
        }

        return instance;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

}
