package edu.iu.mobiperv.android.esoochi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;


public class ItemFragment extends Fragment {

    ListView itemListView;

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
                android.R.layout.simple_list_item_1, GlobalUtils.itemList);

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
    public List<String> getItemsFromServer() {

        List<String> itemsFromServer;

        // TODO: Return data from server. Returning dummy data with following dummy logic.
        if(GlobalUtils.itemList != null && GlobalUtils.itemList.size() != 0) {
            itemsFromServer = GlobalUtils.itemList;
        } else {
            itemsFromServer = new ArrayList<String>();
            itemsFromServer.add("Cabbage");
            itemsFromServer.add("Chicken");
            itemsFromServer.add("Protein Drink");
            itemsFromServer.add("Cereals");
        }

        return itemsFromServer;
    }
}