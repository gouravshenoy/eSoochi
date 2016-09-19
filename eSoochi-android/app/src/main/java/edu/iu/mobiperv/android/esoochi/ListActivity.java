package edu.iu.mobiperv.android.esoochi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.Constants;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.GlobalUtils;
import edu.iu.mobiperv.android.esoochi.edu.iu.mobiperv.android.esoochi.util.SignedInUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";

    //private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FloatingActionButton fab;
    Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        //setUpNavigationDrawer();

        setContentView(R.layout.activity_list);

        Log.d(TAG, GlobalUtils.itemList.toString());

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.add_list_item_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked : " + Constants.ITEM_LIST_TAG);
                Intent intent = new Intent(fab.getContext(), AddItemActivity.class);
                startActivity(intent);

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "Tab Selected: " + tab.getText());

                if(tab.getText().equals(Constants.ITEM_LIST_TAG)) {
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "Clicked : " + Constants.ITEM_LIST_TAG);
                            Intent intent = new Intent(fab.getContext(), AddItemActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                else if(tab.getText().equals(Constants.GROUP_LIST_TAG)) {
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "Clicked : " + Constants.GROUP_LIST_TAG);
                            final EditText taskEditText = new EditText(fab.getContext());
                            AlertDialog dialog = new AlertDialog.Builder(fab.getContext())
                                    .setTitle("Add a new group")
                                    .setMessage("Enter group name")
                                    .setView(taskEditText)
                                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String group = String.valueOf(taskEditText.getText());
                                            Log.d(TAG, "Group to add: " + group);

                                            JSONObject data = new JSONObject();
                                            JSONObject request = new JSONObject();

                                            try {
                                                data.put("name", group);
                                                request.put("group", data);
                                            } catch (JSONException e) {
                                                Log.e("ERROR", e.getMessage(), e);
                                            }

                                            Log.d(TAG, "Sending JSON data for Create Group: " + request.toString());
                                            new CreateGroupTask().execute(request.toString());
                                            GlobalUtils.groupList.add(group);

                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create();
                            dialog.show();
                        }
                    });
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new item")
                        .setMessage("What do you want to add next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                Log.d(TAG, "Item to add: " + task);

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ItemFragment(), Constants.ITEM_LIST_TAG);
        adapter.addFragment(new GroupFragment(), Constants.GROUP_LIST_TAG);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private class CreateGroupTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            try {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, data[0]);

                Request request = new Request.Builder()
                        .url(Constants.REST_ENDPOINT + "/group")
                        .addHeader("user-id", SignedInUser.getUserId())
                        .post(body)
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
//            try {
//                GlobalUtils.groupList.add(new JSONObject(json).getJSONObject("userGroup").getString("name"));
//            } catch (JSONException e) {
//                Log.e("ERROR", e.getMessage(), e);
//            }
        }
    }
}