package edu.iu.mobiperv.android.esoochi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.SignedInUser;


public class GroupFragment extends Fragment {

    private static final String TAG = "GroupFragment";

    ListView groupListView;
    FloatingActionButton addGroupButton;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       final View rootView = inflater.inflate(R.layout.fragment_groups,
                container, false);
        groupListView = (ListView) rootView.findViewById(R.id.list_groups);

        //addGroupButton = (FloatingActionButton) rootView.findViewById(R.id.add_group_button);

        GlobalUtils.groupList = getGroupsFromServer();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, GlobalUtils.groupList);

        groupListView.setAdapter(adapter);

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedGrp = (String) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(rootView.getContext(), GroupItemListActivity.class);
                Bundle b = new Bundle();
                b.putString("selectedGroup", selectedGrp);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Function that retrieves groups for the user from server.
     * @return
     */
    public List<String> getGroupsFromServer() {

        List<String> groupsFromServer = new ArrayList<String>();

        for(JSONObject group : SignedInUser.getGroups()) {
            try {
                groupsFromServer.add(group.getString("name"));
            } catch (JSONException e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }

        return groupsFromServer;
    }

}