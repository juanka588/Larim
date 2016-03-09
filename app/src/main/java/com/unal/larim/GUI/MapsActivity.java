package com.unal.larim.GUI;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unal.larim.DataSource.SponsorContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mGoogleMap;
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private ArrayList<LatLng> markersList = new ArrayList<LatLng>();
    private static final String MAP_MARKER_TYPE = "2";
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        manageToolbar();
        setUpMapIfNeeded();
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.title_activity_maps));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mGoogleMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mGoogleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mGoogleMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mGoogleMap} is not null.
     */
    private void setUpMap() {
        getMapData();
        act = this;
        moveCamera(markersList.get(0), 4);
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Util.irA(marker.getSnippet(), act);
            }
        });
    }

    private void getMapData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(SponsorContent.buildMapsUri(MAP_MARKER_TYPE)
                , null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        for (int i = 0; i < mat.length; i++) {
            showMarker(new LatLng(Double.parseDouble(mat[i][2] + ""), Double.parseDouble(mat[i][3] + "")),
                    mat[i][0] + "", mat[i][1] + "", 0);
        }
        cursor.close();
    }

    private void showMarker(LatLng latLng, String title, String desc,
                            int type) {
        showMarker(latLng.latitude, latLng.longitude, title, desc, type);

    }

    /**
     * @param lat   latitude
     * @param lng   longitude
     * @param title title displayed
     * @param desc  snippet
     * @param type  describes if we want to change visual aspects of marker
     */
    private void showMarker(double lat, double lng, String title,
                            String desc, int type) {
        if (!markersList.contains(new LatLng(lat, lng))) {
            markersList.add(new LatLng(lat, lng));
            MarkerOptions k = null;
            if (type == 0) {
                k = new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title(title)
                        .snippet(desc)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

            } else {
                k = new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title(title)
                        .snippet(desc)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                // .fromResource(R.drawable.edificiop2));
            }
            mGoogleMap.addMarker(k);
        }
    }

    private void moveCamera(LatLng position, int zoom2) {
        CameraPosition camPos = new CameraPosition.Builder().target(position)
                .zoom(zoom2) // Establecemos el zoom en 19
                .bearing(0) // Establecemos la orientación con el noreste arriba
                .tilt(0) // Bajamos el punto de vista de la cámara 70 grados
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        mGoogleMap.animateCamera(camUpd3);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ItemSatellite:
                changeMapType();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void changeMapType() {
        if (mGoogleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}
