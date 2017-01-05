package com.gnasirator.gps_test;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    TextView text_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_out = (TextView) findViewById(R.id.txt_output);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                3000,   // 3 sec
                10, this);}
        catch (SecurityException e) {
            text_out.setText("Error: Security!");
        }

        if (locationManager != null) {
            Location location;
            try {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                String str = "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude();
                text_out.append("\n" + str);
                String speed = location.hasSpeed() == TRUE ? "1" : "0";
                text_out.append("\nhas:\t" + speed);
                speed = Float.toString(location.getSpeed());
                text_out.append("\nv:\t" + speed);
                text_out.append("\np:\t" + location.getProvider());
            } catch (SecurityException e) {
                text_out.setText("Error: Security2!");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the location has changed.
     * <p>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        String str = "Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude();
        text_out.append("\n" + str);
        String speed = location.hasSpeed() == TRUE ? "1" : "0";
        text_out.append("\nhas:\t" + speed);
        speed = Float.toString(location.getSpeed());
        text_out.append("\nv:\t" + speed);
        text_out.append("\np:\t" + location.getProvider());
    }


    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *                 <p>
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *                 <p>
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        text_out.append("stat_changed");
    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {
        text_out.setText("provider enabled");
    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {
        text_out.setText("provider disabled");
    }
}
