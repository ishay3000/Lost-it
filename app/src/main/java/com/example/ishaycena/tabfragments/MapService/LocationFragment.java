package com.example.ishaycena.tabfragments.MapService;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.R;
import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "LocationFragment";

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(), "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mIsLocationPermissionsGranted) {
            /* TODO call a function which takes the LOCATION" bundle from the fragment, and sets the map to those coordination
             */
            setMapToLocation();

 /*           // get device's location on startup
            getDeviceLocation();

            // permissions check
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // set marker on my location
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            init();*/
        }
    }

    private void setMapToLocation() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Bundle locationBundle = getArguments();
        CustomLatLong latLong = (CustomLatLong) locationBundle.getSerializable("LOCATION");
        moveCamera(new LatLng(latLong.lat,
                latLong.lng), DEFAULT_ZOOM_LEVEL, "AdapterFound Location");


        // set marker on my location
//        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        init();
    }

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int RC_LOCATION_PERMISSION = 1;
    private static final float DEFAULT_ZOOM_LEVEL = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS =
            new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));


    // widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGPS;
    View view;


    // vars
    private boolean mIsLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private GeoDataClient geoDataClient;// = new GeoDataClient(MapActivity.this, new PlacesOptions.Builder().build());
    private LatLng mLatLng;
    private double mTmpLat = 0, mTmpLng = 0;


    public static LocationFragment newInstance(CustomLatLong customLatLong) {
        Bundle args = new Bundle();
        args.putSerializable("LOCATION", customLatLong);

        final LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_location, container, false);

            mSearchText = view.findViewById(R.id.input_search);
            mGPS = view.findViewById(R.id.ic_gps);
            geoDataClient = Places.getGeoDataClient(Objects.requireNonNull(getActivity()), null);

            getLocationPermissions();
        }

        return view;
    }


    /**
     * initiates the search adapter (autocomplete suggestions
     */
    private void init() {
        Log.d(TAG, "init: initialising auto complete");

        mGoogleApiClient = new GoogleApiClient
                .Builder(Objects.requireNonNull(getContext()))
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(Objects.requireNonNull(getActivity()), this)
                .build();
        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(
                Objects.requireNonNull(getActivity()), geoDataClient, LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    // search method here
                    geoLocate();
                }
                return false;
            }
        });

        mGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked GPS icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }

    /**
     * geo-locates the address the user has typed in the search box
     */
    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");

        String searchStr = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(Objects.requireNonNull(getActivity()));
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchStr, 1);
            // clear other markers
            mMap.clear();

        } catch (IOException ioe) {
            Log.d(TAG, "geoLocate: error: " + ioe.toString());
        }
        if (list.size() > 0) {
            Address address = list.get(0);

            mTmpLat = address.getLatitude();
            mTmpLng = address.getLongitude();

            Log.d(TAG, "geoLocate: found location: " + address.toString());
            moveCamera(new LatLng(mTmpLat,
                            mTmpLng),
                    DEFAULT_ZOOM_LEVEL, address.getAddressLine(0));
        }
    }

    /**
     * moves the camera to the device's current location
     */
    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting device current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        try {
            if (mIsLocationPermissionsGranted) {

                final Task locationTask = mFusedLocationProviderClient.getLastLocation();
                locationTask.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            // Log.d(TAG, "onComplete: is location null? printing... " + currentLocation.toString());

                            // move camera to the device current location
                            if (currentLocation != null) {
                                moveCamera(new LatLng(currentLocation.getLatitude(),
                                        currentLocation.getLongitude()), DEFAULT_ZOOM_LEVEL, "My Location");
                            }
                        } else {
                            Log.d(TAG, "onComplete: current location is null (not found!)");
                            Toast.makeText(Objects.requireNonNull(getActivity()), "unable to get current location", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        } catch (SecurityException se) {
            Log.d(TAG, "getDeviceLocation: no permissions");
        } catch (Exception ex) {
            Log.d(TAG, "getDeviceLocation: error: " + ex.toString());
        }
    }

    /**
     * moves the camera to a specified LatLng object
     *
     * @param latLng longitude-latitude location
     * @param zoom   zoom level
     * @param title  the title to be displayed on the marker
     */
    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, String.format("moveCamera: moving the camera to: {%s}, {%s}", latLng.latitude, latLng.longitude));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            // marking the location
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(markerOptions);
        }
        hideSoftKeyboard();
    }

    /**
     * initiates the google map fragment
     */
    private void initMap() {
        Log.d(TAG, "initMap: initiating map");
        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    /**
     * gets the location permissions
     */
    private void getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions: getting location permissions");
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mIsLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), permissions, RC_LOCATION_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), permissions, RC_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mIsLocationPermissionsGranted = false;

        switch (requestCode) {
            case RC_LOCATION_PERMISSION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mIsLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            // returning! important.
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mIsLocationPermissionsGranted = true;
                    // initialise the map
                    initMap();
                }
                break;
        }
    }

    /**
     * hides the keyboard after the user searches for a place
     */
    private void hideSoftKeyboard() {
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        Objects.requireNonNull(imm).hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
    }


    /*
        --------------------------- google places API autocomplete suggestions ---------------------------
     */

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeId = Objects.requireNonNull(item).getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "onResult: place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);
            try {

            } catch (NullPointerException ex) {
                Log.d(TAG, "onResult: nullptrexception: " + ex.toString());
            } catch (Exception ex) {
                Log.d(TAG, "onResult: exception: " + ex.toString());

            }
            mMap.clear();
            mLatLng = place.getLatLng();
            mTmpLat = mLatLng.latitude;
            mTmpLng = mLatLng.longitude;

            moveCamera(new LatLng(place.getViewport().getCenter().latitude, place.getViewport().getCenter().longitude)
                    , DEFAULT_ZOOM_LEVEL, place.getName().toString());


            try {


            } catch (Exception ex) {
                Log.d(TAG, "onResult: exception: " + ex.toString());
            }

        }
    };
}
