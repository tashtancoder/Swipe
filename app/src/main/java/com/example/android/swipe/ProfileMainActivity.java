package com.example.android.swipe;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

//import android.app.ListFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.swipe.CalendarFragment.CalendarFragment;
import com.example.android.swipe.vbs.HttpRequestResponse.HttpRequestTask;
import com.example.android.swipe.vbs.Parsers.jsonParser;
import com.example.android.swipe.vbs.activeUsers;
import com.example.android.swipe.vbs.userClasses.ConstantValues;
import com.example.android.swipe.vbs.userClasses.User;

public class ProfileMainActivity extends AppCompatActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    public static User user = null;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_main);
        Intent vbsMainIntent = getIntent();
        user = (User) vbsMainIntent.getParcelableExtra("userObject");
        //Log.d("Name", vbsMainIntent.getExtras().getString("name"));
        Log.d("Name", user.getName());
        Log.d("Surname", user.getSurname());
        //getActionBar().setTitle(user.getName() + "_" + user.getSurname());
        //getActionBar().setTitle("Beyza" + "_" + "KIR");
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(user.getName() + "_" + user.getSurname());
        //String uri = "http://192.168.1.109:84/WebServices/MenuService.aspx?usr=" + userName + "&ps=" + password;
        /*StringBuilder uri = new StringBuilder();
        uri.append(ConstantValues.uriMenuFetch);
        uri.append("userNo=").append(user.getUserNo()).append("&userType=").append(user.getUserType())
            .append("&sube=").append(user.getSube());
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        String [] menuList, menuIndex, menuIcon;
        try {
            String response = httpRequestTask.execute(uri.toString()).get();
            Log.d("JSON MENU", response);
            menuList = jsonParser.jsonParserToArray("Text", response);
            menuIndex = jsonParser.jsonParserToArray("Sayi", response);
            menuIcon = jsonParser.jsonParserToArray("Icon", response);
            Log.d("MenuList", menuList.toString());
            Log.d("MenuIndex", menuIndex.toString());
            Log.d("MenuIcon", menuIcon.toString());
            //Log.d("JSON MENU TEXT", .toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_main, menu);
        menu.add(Menu.NONE, 0, Menu.NONE, R.string.menuBilgilerim);
        menu.add(Menu.NONE, 1, Menu.NONE, R.string.menuSifreChange);
        menu.add(Menu.NONE, 2, Menu.NONE, R.string.menuChangeUser);
        menu.add(Menu.NONE, 3, Menu.NONE, R.string.menuSecureLogout);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("Item", item.toString());
        Log.d("ItemID", "" + id);
        switch (id){
            case 2:
                Intent addUserIntent = new Intent(getApplicationContext(), activeUsers.class);
                startActivity(addUserIntent);
                break;

        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new profileTabFragment();
                /*case 1:
                    fragment = new Profile();
                    //return new Profile();*/
                    break;
                case 1:
                    //fragment = new MessagesTabFragment();
                    //fragment = new CalendarTabFragment();
                    fragment = new CalendarFragment();
                    break;
                case 2:
                    fragment = new MessagesTabFragment();
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragment;
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                //case 1:
                //    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
