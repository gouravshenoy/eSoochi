package edu.iu.mobiperv.android.esoochi;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, GlobalUtils.groupList);

        groupListView.setAdapter(adapter);

        groupListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    final EditText taskEditText = new EditText(rootView.getContext());
                    AlertDialog dialog = new AlertDialog.Builder(rootView.getContext())
                            .setTitle("Add a new item")
                            .setMessage("What do you want to add next?")
                            .setView(taskEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(taskEditText.getText());
                                    Log.d(TAG, "Item to add: " + task);

                                    GlobalUtils.groupList.add(task);
                                    groupListView.invalidate();
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),
                                            android.R.layout.simple_list_item_1, GlobalUtils.groupList);
                                    groupListView.setAdapter(adapter);

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }
                return true;
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