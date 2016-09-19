package edu.iu.mobiperv.android.esoochi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;

public class GroupItemListActivity extends AppCompatActivity {

    private static final String TAG = "GroupItemListActivity";

    TextView grpNameTitle;
    ListView grpItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_item_list);

        grpNameTitle = (TextView) findViewById(R.id.group_name_text_view);
        grpItemList = (ListView) findViewById(R.id.group_item_list);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            String selectedGrp = b.getString("selectedGroup");

            grpNameTitle.setText(selectedGrp + " items");

            List<String> group_items = getItemsInGroup(selectedGrp);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, group_items);

            grpItemList.setAdapter(adapter);
        }

    }

    protected List<String> getItemsInGroup(String group) {
        List<String> itemList = null;

        // Mocking the response.
        itemList = new ArrayList<String>();
        itemList.add("Cabbage");
        itemList.add("Protein Drink");
        itemList.add("Cereal");

        return itemList;
    }
}
