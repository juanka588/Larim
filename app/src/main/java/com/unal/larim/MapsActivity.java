package com.unal.larim;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends ActionBarActivity {

    private GoogleMap mapa;
    private ArrayList<LatLng> marcadores = new ArrayList<LatLng>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mapa} is not null.
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
        if (mapa == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mapa != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mapa} is not null.
     */
    private void setUpMap() {
        LatLng point = new LatLng(10.421036, -75.548923);
        animarCamara(point, 17);
        mostrarMarcador(point, "LARIM", "Evento del Añol", 0);

    }

    private void mostrarMarcador(LatLng latLng, String title, String desc,
                                 int tipo) {
        mostrarMarcador(latLng.latitude, latLng.longitude, title, desc, tipo);

    }

    private void mostrarMarcador(double lat, double lng, String title,
                                 String desc, int tipo) {
        /*
         * float a = 0; switch (count) { case 0: a =
		 * BitmapDescriptorFactory.HUE_CYAN; break; case 1: a =
		 * BitmapDescriptorFactory.HUE_ORANGE; break; case 2: a =
		 * BitmapDescriptorFactory.HUE_VIOLET; break; case 3: a =
		 * BitmapDescriptorFactory.HUE_YELLOW; break; case 4: a =
		 * BitmapDescriptorFactory.HUE_ORANGE; break;
		 *
		 * default: break; }
		 */
        if (!marcadores.contains(new LatLng(lat, lng))) {
            marcadores.add(new LatLng(lat, lng));
            MarkerOptions k = null;
            if (tipo == 0) {
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
            mapa.addMarker(k);
        }
    }

    private void animarCamara(LatLng position, int zoom2) {
        CameraPosition camPos = new CameraPosition.Builder().target(position)
                .zoom(zoom2) // Establecemos el zoom en 19
                .bearing(0) // Establecemos la orientación con el noreste arriba
                .tilt(0) // Bajamos el punto de vista de la cámara 70 grados
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        mapa.animateCamera(camUpd3);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ItemSatelite:
                cambiar();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void cambiar() {
        if (mapa.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}
