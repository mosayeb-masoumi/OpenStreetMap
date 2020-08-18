package com.example.maptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView mapView = null;


    Button btn_myLocation;
    Button btn_enable_rotation;
    Button btn_addCompass;
    Button btn_addScalebar;
    Button btn_showListOfMarkers;
    Button btn_setIran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
        //tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_main);


        initView();

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);


        mapView.setMultiTouchControls(true); // touch zoom in / out
//        map.getController().setZoom(2);
        mapView.setMinZoomLevel(2.0);

        /************************************/
        // to show iran as center of the map
        IMapController iMapController = mapView.getController();
        iMapController.setZoom(5.8);
        GeoPoint geoPoint = new GeoPoint(32.4279,53.6880);
        iMapController.setCenter(geoPoint);

        /**************************/
       // How do I place icons on the map with a click listener
//your items
//        ArrayList<OverlayItem> items = new ArrayList<>();
//        items.add(new OverlayItem("Title1", "Description1", new GeoPoint(32.4279,53.6880))); // Lat/Lon decimal degrees
//        items.add(new OverlayItem("Title2", "Description2", new GeoPoint(32.4278,53.6879))); // Lat/Lon decimal degrees
//        items.add(new OverlayItem("Title3", "Description3", new GeoPoint(32.4277,53.6878))); // Lat/Lon decimal degrees
//
//
////the overlay
//        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
//                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
//                    @Override
//                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
//
//                        //do something
//
//                        int a = 5;
//
//                        return true;
//                    }
//                    @Override
//                    public boolean onItemLongPress(final int index, final OverlayItem item) {
//                        return false;
//                    }
//                }, this);
//        mOverlay.setFocusItemsOnTap(true);
//        mapView.getOverlays().add(mOverlay);

        /************************************************************************************************************************************/

        //show list of markers on map

        // get list from server
        List<LatLngModel> latLngList = new ArrayList<>();
        latLngList.add(new LatLngModel(35.4279, 53.6880 , "title1","description1"));
        latLngList.add(new LatLngModel(34.4279, 52.6990, "title2","description2"));
        latLngList.add(new LatLngModel(33.4279, 51.6770, "title3","description3"));
        latLngList.add(new LatLngModel(32.7279, 55.6660, "title4","description4"));

//        for (int i = 0; i <latLngList.size() ; i++) {
//            // show marker
//            GeoPoint startPoint = new GeoPoint(latLngList.get(i).getLat(), latLngList.get(i).getLng());
//            Marker startMarker = new Marker(mapView);
//            startMarker.setPosition(startPoint);
//            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//            mapView.getOverlays().add(startMarker);
//        }




        // add title and description to each marker
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i = 0; i <latLngList.size() ; i++) {
//




            GeoPoint startPoint = new GeoPoint(latLngList.get(i).getLat(), latLngList.get(i).getLng());  //white house
            Marker startMarker = new Marker(mapView);
            startMarker.setPosition(startPoint);
//            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setIcon(getResources().getDrawable(R.drawable.my_icon1));
            startMarker.setTitle(latLngList.get(i).getTitle());
            startMarker.setSnippet(latLngList.get(i).getDescription());
            startMarker.setSubDescription(latLngList.get(i).getDescription());
            mapView.getOverlays().add(startMarker);
            mapView.invalidate();


            // for click listener of each marker
//            items.add(new OverlayItem(latLngList.get(i).getTitle(), latLngList.get(i).getDescription(), new GeoPoint(latLngList.get(i).getLat(),latLngList.get(i).getLng())));

        }
////
//                ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
//                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
//                    @Override
//                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
//
//                        //do something
//                        int a = 5;
//
//                        return true;
//                    }
//                    @Override
//                    public boolean onItemLongPress(final int index, final OverlayItem item) {
//                        return false;
//                    }
//                }, this);
//        mOverlay.setFocusItemsOnTap(true);
//        mapView.getOverlays().add(mOverlay);



        /************************************************************************************************************************************/

        requestPermissionsIfNecessary(new String[] {
                // if you need to show the current location, uncomment the line below
                 Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });


    }

    private void initView() {
        btn_myLocation = findViewById(R.id.btn_myLocation);
        btn_enable_rotation = findViewById(R.id.enable_rotation);
        btn_addCompass  =findViewById(R.id.addCompass);
        btn_addScalebar = findViewById(R.id.addScalebar);
        btn_showListOfMarkers = findViewById(R.id.showListOfMarkers);
        btn_setIran = findViewById(R.id.setIran);
        btn_setIran.setOnClickListener(this);
        btn_showListOfMarkers.setOnClickListener(this);
        btn_addScalebar.setOnClickListener(this);
        btn_addCompass.setOnClickListener(this);
        btn_enable_rotation.setOnClickListener(this);
        btn_myLocation.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_myLocation:
                showCurrentLocation();
                break;

            case R.id.enable_rotation:
                enableRotation();
                break;


            case R.id.addCompass:
                addCompass();
                break;

            case R.id.addScalebar:
                addScalebar();
                break;

            case R.id.showListOfMarkers:
//                showListOfMarkers();
                break;

            case R.id.setIran:
                setIranAsCenter();
                break;


        }
    }

    private void setIranAsCenter() {

        // to show iran as center of the map
        IMapController iMapController = mapView.getController();
        iMapController.setZoom(5.8);
        GeoPoint geoPoint = new GeoPoint(32.4279,53.6880);
//        iMapController.setCenter(geoPoint);
        iMapController.animateTo(geoPoint);
    }



    private void addScalebar() {

        final DisplayMetrics dm = getResources().getDisplayMetrics();
        ScaleBarOverlay  mScaleBarOverlay = new ScaleBarOverlay(mapView);
        mScaleBarOverlay.setCentred(true);
//play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(mScaleBarOverlay);
    }

    private void enableRotation() {

        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(MainActivity.this, mapView);
        rotationGestureOverlay.setEnabled(true);
        mapView.setMultiTouchControls(true);
        mapView.getOverlays().add(rotationGestureOverlay);
    }


    private void addCompass() {

        CompassOverlay mCompassOverlay = new CompassOverlay(MainActivity.this, new InternalCompassOrientationProvider(MainActivity.this), mapView);
        mCompassOverlay.enableCompass();
        mapView.getOverlays().add(mCompassOverlay);

    }


    private void showCurrentLocation() {

        // default icon
        MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(mapView);
        myLocationoverlay.enableFollowLocation();
        myLocationoverlay.enableMyLocation();
        mapView.getOverlays().add(myLocationoverlay);


        // custom icon
//        MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(mapView);
//        myLocationoverlay.enableFollowLocation();
//        Drawable currentDraw = ResourcesCompat.getDrawable(getResources(), R.drawable.my_icon2, null);
//        Bitmap currentIcon = null;
//        if (currentDraw != null) {
//            currentIcon = Converter.drawableToBitmap(currentDraw);
//        }
//        myLocationoverlay.enableMyLocation();
//        myLocationoverlay.setPersonIcon(currentIcon);
//        mapView.getOverlays().add(myLocationoverlay);
    }







    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}