package hermax_Lab.cyphr.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class DrawerActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;


    private CharSequence mTitle;
    CustomDrawerAdapter adapter;
Fragment fragment;
    List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getActionBar();
        actionBar.setLogo(R.drawable.cyphr_logo);
        actionBar.setTitle("");
        setContentView(R.layout.activity_main);



        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        // Add Drawer Item to dataList
        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("Invites", R.drawable.ic_invites));
        dataList.add(new DrawerItem("Activity", R.drawable.ic_activity));
        dataList.add(new DrawerItem("Likes", R.drawable.ic_likes));
        dataList.add(new DrawerItem("Favorites", R.drawable.ic_favorites));
        dataList.add(new DrawerItem("Login", R.drawable.ic_login));
        dataList.add(new DrawerItem("Home", R.drawable.ic_home));
        dataList.add(new DrawerItem("Settings", R.drawable.ic_settings));
        dataList.add(new DrawerItem("About", R.drawable.ic_about));





        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
              //  getActionBar().show();
                invalidateOptionsMenu();

                 // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().hide();
                invalidateOptionsMenu();
                // creates call to
                // onPrepareOptionsMenu()
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }
    }

    public void SelectItem(int position) {


        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                fragment = new SongFragment();


                break;
            case 1:
                fragment = new SongFragment();


                break;
            case 2:
                fragment = new SongFragment();


                break;
            case 3:
                fragment = new SongFragment();



                break;
            case 4:
                fragment = new LoginFragment();
                break;

            case 5:
                fragment = new SongFragment();
                break;

            case 6:
                fragment = new AboutFragment();


                break;
            default: fragment = new SongFragment();

                break;

        }


          fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .commit();


        mDrawerList.setItemChecked(position,true);
        mDrawerList.setPressed(true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);

    }



    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setSubtitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
          fragment = null;

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }



        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);






        }
    }


}