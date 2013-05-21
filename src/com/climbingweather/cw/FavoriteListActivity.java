package com.climbingweather.cw;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Favorite list activity
 */
public class FavoriteListActivity extends ListActivity {

	private FavoriteDbAdapter mDbHelper;
	
	private Cursor mCursor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
        LayoutInflater inflater = getLayoutInflater();
        TextView headerView = (TextView) inflater.inflate(R.layout.header_row, null);
        headerView.setText("Favorite Areas");
        getListView().addHeaderView(headerView);
        
        mDbHelper = new FavoriteDbAdapter(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());
    }
    
    /**
     * On destroy activity
     */
    public void onDestroy()
    {
        super.onDestroy();
        mDbHelper.close();
    }
    
    /**
     * On start activity
     */
    public void onStart()
    {
        super.onStart();
        mDbHelper.open();
        fillData();
    }
    
    /**
     * On stop activity
     */
    public void onStop()
    {
        super.onStop();
        mCursor.close();
        mDbHelper.close();
    }
    
    /**
     * On restart activity
     */
    public void onRestart()
    {
        super.onRestart();
        mDbHelper.open();
        fillData();
    }
    
    /**
     * On pause activity
     */
    public void onPause()
    {
        super.onPause();
        mDbHelper.close();
    }
    
    /**
     * On resume activity
     */
    public void onResume()
    {
        super.onResume();
        mDbHelper.open();
        fillData();
    }
    
    private void fillData() {
        // Get all of the rows from the database and create the item list
        mCursor = mDbHelper.fetchAllFavorites();
        startManagingCursor(mCursor);
        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{ FavoriteDbAdapter.KEY_NAME };
        
        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.name};
        
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter favorites = 
        	    new SimpleCursorAdapter(this, R.layout.list_row, mCursor, from, to);
        setListAdapter(favorites);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        if (position == 0) {
        	
        	return;
        	
        }
        
        FavoriteDbAdapter dbAdapter = new FavoriteDbAdapter(this);
        dbAdapter.open();
        Cursor fav = dbAdapter.fetchFavorite(id);
        startManagingCursor(fav);
        // launchForecast(fav.getString(fav.getColumnIndex(FavoriteDbAdapter.KEY_AREAID)));
        
        Intent i = new Intent(this, AreaActivity.class);
        i.putExtra("areaId", fav.getString(fav.getColumnIndex(FavoriteDbAdapter.KEY_AREAID)));
        i.putExtra("name", fav.getString(fav.getColumnIndex(FavoriteDbAdapter.KEY_NAME)));
        fav.close();
        dbAdapter.close();
        startActivity(i);
        
    }
}