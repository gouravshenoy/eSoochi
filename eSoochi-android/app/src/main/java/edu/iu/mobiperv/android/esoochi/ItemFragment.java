package edu.iu.mobiperv.android.esoochi;

import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.Constants;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.SignedInUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ItemFragment extends Fragment {

    ListView itemListView;
    private static final String TAG = "eSoochi.ItemFragment";

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_myitems,
                container, false);
        itemListView = (ListView) rootView.findViewById(R.id.list_items);

        GlobalUtils.itemList = getItemsFromServer();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, new ArrayList<>(GlobalUtils.itemList));
        itemListView.setAdapter(adapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(itemListView.getContext(), DetailItemActivity.class);
                Bundle b = new Bundle();
                b.putString("selectedItem", selectedItem);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Function that retrieves items for the user from server.
     * @return
     */
    public Set<String> getItemsFromServer() {
        try {
            for(JSONObject group : SignedInUser.getGroups()) {
                new GetGroupTask().execute(group.getString("id"));
//                if(group.has("items")) {
//                    Object items = group.get("items");
//                    if (items instanceof JSONArray) {
//                        for (int i = 0; i < ((JSONArray) items).length(); i++) {
//                            GlobalUtils.itemList.add(((JSONArray) items).getJSONObject(i).getString("itemName"));
//                        }
//                    } else {
//                        GlobalUtils.itemList.add(((JSONObject) items).getString("itemName"));
//                    }
//                }
            }
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        return GlobalUtils.itemList;
    }

    private class GetGroupTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(Constants.REST_ENDPOINT + "/group/" + data[0])
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            Log.d(TAG, "Group: " + json);
            try {
                JSONObject group = new JSONObject(json);
                if(group.getJSONObject("userGroup").has("items")) {
                    Object items = group.getJSONObject("userGroup").get("items");
                    if (items instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) items).length(); i++) {
                            GlobalUtils.itemList.add(((JSONArray) items).getJSONObject(i).getString("itemName"));
                        }
                    } else {
                        GlobalUtils.itemList.add(((JSONObject) items).getString("itemName"));
                    }
                } else {
                    Log.d(TAG, "no items");
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }

            Log.d(TAG, "ItemList: " + GlobalUtils.itemList);

//            try {
//                GlobalUtils.groupList.add(new JSONObject(json).getJSONObject("userGroup").getString("name"));
//            } catch (JSONException e) {
//                Log.e("ERROR", e.getMessage(), e);
//            }
        }
    }
}