package com.climbingweather.cw;

import android.content.Context;
import android.content.Intent;

public class CwApiServiceHelper {

    private static CwApiServiceHelper instance;
    
    protected CwApiServiceHelper() {}
    
    public static CwApiServiceHelper getInstance() {
        if (instance == null) {
            instance = new CwApiServiceHelper();
        }
        return instance;
    }
    
    public void startFavorites(Context context) {
        Intent serviceIntent = new Intent(context, CwApiService.class);
        serviceIntent.setData(AreasContract.FAVORITES_URI);
        
        context.startService(serviceIntent);
    }
    
    public void startNearby(Context context, Double latitude, Double longitude) {
        Intent serviceIntent = new Intent(context, CwApiService.class);
        serviceIntent.setData(AreasContract.NEARBY_URI);
        serviceIntent.putExtra("latitude", latitude);
        serviceIntent.putExtra("longitude", longitude);
        
        context.startService(serviceIntent);
    }
}
