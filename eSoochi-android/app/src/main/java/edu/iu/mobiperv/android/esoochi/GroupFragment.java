package edu.iu.mobiperv.android.esoochi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;


public class GroupFragment extends Fragment {

    ListView groupListView;

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


       View rootView = inflater.inflate(R.layout.fragment_groups,
                container, false);
        groupListView = (ListView) rootView.findViewById(R.id.list_groups);

        GlobalUtils.groupList = getGroupsFromServer();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, GlobalUtils.groupList);

        groupListView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Function that retrieves groups for the user from server.
     * @return
     */
    public List<String> getGroupsFromServer() {

        List<String> groupsFromServer = new ArrayList<String>();

        // TODO: Return data from server. Returning dummy data for now.
        groupsFromServer.add("Party group");
        groupsFromServer.add("Roommates group");
        groupsFromServer.add("3 Hoosiers");
        groupsFromServer.add("Rambo group");

        return groupsFromServer;
    }

}