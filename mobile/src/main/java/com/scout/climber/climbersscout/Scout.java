package com.scout.climber.climbersscout;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Scout extends Activity implements LocationListener {
    LocationManager locationManager;
    String locationProvider;
    TextView latitude;
    TextView longitude;
    TextView altitude;
    TextView bearing;
    TextView accuracy;
    TextView speed;
    TextView provider;
    TextView address;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climbers_advisor);
        latitude = (TextView) findViewById(R.id.latLabel);
        longitude = (TextView) findViewById(R.id.lngLabel);
        altitude = (TextView) findViewById(R.id.altLabel);
        bearing = (TextView) findViewById(R.id.bearingLabel);
        accuracy = (TextView) findViewById(R.id.accLabel);
        speed = (TextView) findViewById(R.id.speedLabel);
        provider = (TextView) findViewById(R.id.providerLabel);
        address = (TextView) findViewById(R.id.addressLabel);
        //To set the underline
        TextView title = (TextView)findViewById(R.id.title);
        SpannableString content = new SpannableString("Climber's Scout");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        title.setText(content);

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
        String providerkc=location.getProvider();
        Float speedkc=location.getSpeed();
        //Getting the user's Address
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> address1=geocoder.getFromLocation(latitudekc,longitudekc,1);
            String addressKeeper="";
            if(address1!=null && address1.size()>0){
                for(int i=0;i<=address1.get(0).getMaxAddressLineIndex();i++){
                    addressKeeper+=address1.get(0).getAddressLine(i);
                }
                address.setText("Adderss" + addressKeeper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        latitude.setText("LATITUDE:" + latitudekc.toString());
        longitude.setText("LONGITUDE:" + longitudekc.toString());
        altitude.setText("ALTITUDE:" + altitudekc.toString() + "m");
        bearing.setText("BEARING:" + bearingkc.toString());
        accuracy.setText("ACCURACY:" + accuracykc.toString() + "m");
        provider.setText("PROVIDER:" + providerkc.toString());
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

