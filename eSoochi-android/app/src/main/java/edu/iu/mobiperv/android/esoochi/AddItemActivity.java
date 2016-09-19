package edu.iu.mobiperv.android.esoochi;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    Button addItemButton;
    EditText item_name_text;
    EditText store_addr_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemButton = (Button) findViewById(R.id.submit_item_button);
        item_name_text = (EditText) findViewById(R.id.item_name_text);
        store_addr_text = (EditText) findViewById(R.id.store_addr_text);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double[] coordinates = getGPSCordinates(store_addr_text.getText().toString());

                GlobalUtils.itemList.add(item_name_text.getText().toString());

                // TODO: Store item to backend coordinates.
                Log.d(TAG, "Adding item " + item_name_text.getText().toString() + " to backend with gps coordinates: "
                        + coordinates[0] + " | " + coordinates[1]);

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
}
