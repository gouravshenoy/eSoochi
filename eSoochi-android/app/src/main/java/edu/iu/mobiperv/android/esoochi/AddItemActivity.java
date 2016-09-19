package edu.iu.mobiperv.android.esoochi;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.Constants;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.SignedInUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    Spinner group_select_spinner;
    String selectedGroup;

    Button addItemButton;
    EditText item_name_text;
    EditText store_addr_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        group_select_spinner = (Spinner) findViewById(R.id.group_select_spinner);
        addItemButton = (Button) findViewById(R.id.submit_item_button);
        item_name_text = (EditText) findViewById(R.id.item_name_text);
        store_addr_text = (EditText) findViewById(R.id.store_addr_text);

        group_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGroup = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, GlobalUtils.groupList);
        group_select_spinner.setAdapter(adapter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double[] coordinates = getGPSCordinates(store_addr_text.getText().toString());

                GlobalUtils.itemList.add(item_name_text.getText().toString());

                // TODO: Store item to backend coordinates.
                Log.d(TAG, "Adding item " + item_name_text.getText().toString() + " to group "
                        + selectedGroup + " with gps coordinates: "
                        + coordinates[0] + " | " + coordinates[1]);

                JSONObject data = new JSONObject();
                JSONObject request = new JSONObject();

                try {
                    data.put("addedByUserId", SignedInUser.getUserId());
                    data.put("address", store_addr_text.getText().toString());
                    data.put("latitude", coordinates[0]);
                    data.put("longitude", coordinates[1]);
                    data.put("itemName", item_name_text.getText().toString());
                    for(JSONObject group : SignedInUser.getGroups()) {
                        if(group.get("name").equals(selectedGroup)) {
                            data.put("addedInGroupId", group.get("id"));
                        }
                    }
                    request.put("inventoryItem", data);
                } catch (JSONException e) {
                    Log.e("ERROR", e.getMessage(), e);
                }

                Log.d(TAG, "Sending JSON data for Create User: " + request.toString());

                new AddItemTask().execute(request.toString());

                Intent intent = new Intent(addItemButton.getContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

    /***
     * Function that returns GPS coordinates based on location string.
     * coordinates[0] == x coordinate;
     * coordinate[1] == y coordinate;
     * @param location
     * @return
     */
    protected  Double[] getGPSCordinates(String location) {
        Double[] coordinates = new Double[2];

        coordinates[0] = 25.09434534;
        coordinates[1] = 70.34534343;

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(location, 1);

            Address addrToConsider = addresses.get(0);

            coordinates[0] = addrToConsider.getLatitude();
            coordinates[1] = addrToConsider.getLongitude();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return coordinates;
    }

    private class AddItemTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            try {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, data[0]);

                Request request = new Request.Builder()
                        .url(Constants.REST_ENDPOINT + "/item")
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
            Log.d(TAG, "Item: " + json);
        }
    }
}
