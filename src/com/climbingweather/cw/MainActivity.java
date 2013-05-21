package com.climbingweather.cw;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends SherlockFragmentActivity {
    
    private static final String[] CONTENT = new String[] { "Home", "Favorites", "Nearby", "By State", "Map" };
    
    /**
     * Location manager for location updates
     */
    private LocationManager lm;
    
    /**
     * Location listener to receive location updates
     */
    private LocationListener locationListener;
    
    /**
     * User latitude
     */
    private double latitude;
    
    /**
     * User longitude
     */
    private double longitude;
    
    /** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.main);
        setContentView(R.layout.simple_tabs);
        
        /*
        // Favorite text and image
        TextView favoriteText = (TextView)findViewById(R.id.favorite_text);
        favoriteText.setOnClickListener(favoriteListener);
        
        ImageView favoriteImage = (ImageView) findViewById(R.id.favorite_image);
        favoriteImage.setOnClickListener(favoriteListener);
        
        LinearLayout favoriteLayout = (LinearLayout) findViewById(R.id.favorite);
        favoriteLayout.setOnClickListener(favoriteListener);
        
        LinearLayout stateLayout = (LinearLayout) findViewById(R.id.state);
        stateLayout.setOnClickListener(stateListener);
        
        LinearLayout nearestLayout = (LinearLayout) findViewById(R.id.closest);
        nearestLayout.setOnClickListener(closestListener);
        
        // Search text
        final EditText searchEdit = (EditText) findViewById(R.id.search_edit);

        // Capture key actions
        searchEdit.setOnKeyListener(new OnKeyListener() {
            
            // Capture key actions
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                
                // If enter is pressed, do search
                if ((event.getAction() == KeyEvent.ACTION_DOWN) 
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    doSearch();
                    return true;
                    
                }
                
                return false;
            }
        });
        
        // Search button
        ImageView searchButton = (ImageView) findViewById(R.id.search_button);
        searchButton.setOnClickListener(searchListener);
        */
        
        // Start location manager
        
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        /*
        // Get last known location
        Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        
        // If GPS location is found, use that
        if (loc != null) {
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
            
        // If no GPS location is found, try network provider
        } else {
            Location locNetwork = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            
            if (locNetwork != null) {
                latitude = locNetwork.getLatitude();
                longitude = locNetwork.getLongitude();
            }
        }
        */
        /*
        
        // Add location listener
        addLocationListener();
        */
        /*
        final ActionBar actionBar = getSupportActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab,
                    FragmentTransaction ft) { }

            public void onTabUnselected(ActionBar.Tab tab,
                    FragmentTransaction ft) { }

            public void onTabReselected(ActionBar.Tab tab,
                    FragmentTransaction ft) { }
        };

        // Add 3 tabs.
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }
        */
        
        FragmentPagerAdapter adapter = new CwPagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }
    
    /**
     * CW Pager adapter
     */
    class CwPagerAdapter extends FragmentPagerAdapter {
        
        /**
         * Constructor
         * @param fm
         */
        public CwPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 3) {
                return new StateListFragment();
            } else if (position == 1) {
                return new FavoriteListFragment();
            } else {
                return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
          return CONTENT.length;
        }
    }
    
    /**
     * On destroy activity
     */
    public void onDestroy()
    {
        super.onDestroy();
        removeLocationListener();
    }
    
    /**
     * On start activity
     */
    public void onStart()
    {
        super.onStart();
        addLocationListener();
    }
    
    /**
     * On stop activity
     */
    public void onStop()
    {
        super.onStop();
        removeLocationListener();
    }
    
    /**
     * On restart activity
     */
    public void onRestart()
    {
        super.onRestart();
        addLocationListener();
    }
    
    /**
     * On pause activity
     */
    public void onPause()
    {
        super.onPause();
        removeLocationListener();
    }
    
    /**
     * On resume activity
     */
    public void onResume()
    {
        super.onResume();
        addLocationListener();
    }
    
    /**
     * On click listener for favorites button
     */
    private OnClickListener favoriteListener = new OnClickListener() {
        
        public void onClick(View v) {
            
            Intent i = new Intent(getApplicationContext(), FavoriteListActivity.class);
            startActivity(i);
        }
        
    };
    
    /**
     * On click listener for favorites button
     */
    private OnClickListener searchListener = new OnClickListener() {
        
        public void onClick(View v) {
            
            doSearch();

        }
        
    };
    
    /**
     * On click listener for states button
     */
    private OnClickListener stateListener = new OnClickListener() {
        
        public void onClick(View v) {
            launchStates();
        }
    };
    
    /**
     * On click listener for closest areas
     */
    private OnClickListener closestListener = new OnClickListener() {
    
        /**
         * On click
         */
        public void onClick(View v) {

            // If location cannot be determined, do something
            if (latitude == 0.0) {
                
                Toast.makeText(getBaseContext(), "Unable to determine location", Toast.LENGTH_SHORT).show();

            // Location has been determined, start area list activiy with lat/long info
            } else {
            
                Intent i = new Intent(getApplicationContext(), AreaListActivity.class);
                i.putExtra("latitude", Double.toString(latitude));
                i.putExtra("longitude", Double.toString(longitude));
                startActivity(i);
                
            }
            
        }
    };
    
    private boolean doSearch() {

        EditText searchEdit = (EditText) findViewById(R.id.search_edit);
        String srch = searchEdit.getText().toString();
        try {
            srch = URLEncoder.encode(srch, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Clean-up search string - URL encode
        Intent i = new Intent(getApplicationContext(), AreaListActivity.class);
        i.putExtra("srch", srch);
        startActivity(i);
        return true;
        
    }
    
    /**
     * Create options menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    /**
     * Handle options menu clicks
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
        case R.id.about:
            
            Dialog dialog = new Dialog(this);

            dialog.setContentView(R.layout.about);
            dialog.setTitle("About");
            
            dialog.show();
            return true;
        case R.id.settings:
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return false;

    }
    
    /**
     * Launches the Forecast activity to display a forecast
     */
    protected void launchForecast(String id) {
        Intent i = new Intent(getApplicationContext(), DailyActivity.class);
        i.putExtra("areaId", id);
        startActivity(i);
    }
    
    /**
     * Launches areas activity
     */
    protected void launchAreas() {
        Intent i = new Intent(this, AreaListActivity.class);
        startActivity(i);
    }
    
    /**
     * Launches states activity
     */
    protected void launchStates() {
        Intent i = new Intent(this, StateListActivity.class);
        startActivity(i);
    }
    
    /**
     * Launches states activity
     */
    protected void launchTest() {
        Intent i = new Intent(this, DailyActivity.class);
        i.putExtra("areaId", "3");
        startActivity(i);
    }
    
    /**
     * Location listener
     */
    private class MyLocationListener implements LocationListener 
    {
        /**
         * On location change, update lat/long
         */
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, 
            Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
    
    /**
     * Add location listener
     */
    private void addLocationListener()
    {
        lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                600000,
                2000,
                locationListener);
    }

    /**
     * Remove location listener
     */
    private void removeLocationListener()
    {
        lm.removeUpdates(locationListener);
    }

}
