package edu.iu.mobiperv.android.esoochi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailItemActivity extends AppCompatActivity {

    TextView item_detail_title;
    TextView item_name_detail;
    TextView item_group_detail;
    TextView item_add_user_detail;
    TextView item_source_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        item_detail_title = (TextView) findViewById(R.id.item_detail_title);
        item_name_detail = (TextView) findViewById(R.id.item_name_detail);
        item_group_detail = (TextView) findViewById(R.id.item_group_detail);
        item_add_user_detail = (TextView) findViewById(R.id.item_add_user_detail);
        item_source_detail = (TextView) findViewById(R.id.item_source_detail);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            String selectedItem = b.getString("selectedItem");

            item_detail_title.setText(selectedItem + " Details");

            // Dummy Data
            String name = selectedItem;
            String group = "Dummy Grp";
            String add_user = "Dummy User";
            String source = "Dummy Stores";

            // Set details as per the fetched data.
            item_name_detail.setText(name);
            item_group_detail.setText(group);
            item_add_user_detail.setText(add_user);
            item_source_detail.setText(source);
        }
    }
}
