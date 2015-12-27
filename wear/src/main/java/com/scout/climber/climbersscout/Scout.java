package com.scout.climber.climbersscout;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class Scout extends Activity implements LocationListener { //Enables the LocationListener

    private TextView mTextView;
    LocationManager locationManager;
    String locationProvider;
    TextView latitude;
    TextView longitude;
    TextView altitude;
    TextView bearing;
    TextView accuracy;
    TextView speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
        latitude = (TextView) findViewById(R.id.latLabel);
        longitude = (TextView) findViewById(R.id.lngLabel);
        altitude = (TextView) findViewById(R.id.altLabel);
        bearing = (TextView) findViewById(R.id.bearingLabel);
        accuracy = (TextView) findViewById(R.id.accLabel);
        speed = (TextView) findViewById(R.id.speedLabel);
        //For getting the user's location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.getBestProvider(new Criteria(), false);
        Location location = locationManager.getLastKnownLocation(locationProvider);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(locationProvider, 200, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitudekc= location.getLatitude();
        Double longitudekc=location.getLongitude();
        Double altitudekc=location.getAltitude();
        Float bearingkc=location.getBearing();
        Float accuracykc=location.getAccuracy();
        Float speedkc=location.getSpeed();
        latitude.setText("LATITUDE:" + latitudekc.toString());
        longitude.setText("LONGITUDE:" + longitudekc.toString());
        altitude.setText("ALTITUDE:" + altitudekc.toString() + "m");
        bearing.setText("BEARING:" + bearingkc.toString());
        accuracy.setText("ACCURACY:" + accuracykc.toString() + "m");
        speed.setText("SPEED:" + speedkc.toString() + "m/sec");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }
}
