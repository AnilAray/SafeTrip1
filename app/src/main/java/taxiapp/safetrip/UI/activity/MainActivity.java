package taxiapp.safetrip.UI.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.InvocationTargetException;

import de.hdodenhof.circleimageview.CircleImageView;
import taxiapp.safetrip.R;
import taxiapp.safetrip.application.Config;
import taxiapp.safetrip.database.session.SessionManager;
import taxiapp.safetrip.database.sqlite.DBHelper;
import taxiapp.safetrip.dialogs.WarningDialog;
import taxiapp.safetrip.modal.pojos.PojoLocation;
import taxiapp.safetrip.services.FetchAddressIntentService;
import taxiapp.safetrip.services.GeoCoderConstants;
import taxiapp.safetrip.utils.Utils;

public class MainActivity extends BaseActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, View.OnClickListener {




    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1000;
    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1001;
    // private GoogleMap mMap;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private GoogleApiClient mGoogleApiClient;
    TextView Logout,search_result;

    ImageView imageView_menu;
    TextView textViewPickup;
    private DrawerLayout drawer;
    private SessionManager session;
    RelativeLayout relativeLayout;
    Typeface custom_font, custom_font2, custom_font3, custom_font4;
    DBHelper dbHelper;
    CircleImageView circleImageView;
    TextView drawertitle;
    String firstName, lastName;
    ImageButton btn_myLocation;
    TextView textView4;

    TextView textView5;

    TextView textView6;

    TextView textView7;





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        imageView_menu = (ImageView) findViewById(R.id.menu_icon);
        Logout = (TextView) findViewById(R.id.tv_signout);
        search_result = (TextView) findViewById(R.id.textView11);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        btn_myLocation = (ImageButton) findViewById(R.id.btn_myLocation);
        btn_myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateMe();
            }
        });
        relativeLayout = (RelativeLayout) findViewById(R.id.linear_bottom12);
       // circleImageView = (CircleImageView) findViewById(R.id.drawer_pic);
        //drawertitle = (TextView) findViewById(R.id.drawer_title);
        imageView_menu.setOnClickListener(this);
/*
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");*/

       // loadImageFromDB();

        SharedPreferences prefs = getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        firstName = prefs.getString("firstName", null);
        Log.e("Emaill_response", "firstName" + firstName);

        lastName = prefs.getString("lastName", null);
        Log.e("Emaill_response", "firstName" + lastName);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.drawer_title);
        circleImageView = (CircleImageView)hView.findViewById(R.id.drawer_pic);
        nav_user.setText(firstName + " "+ lastName);




        //drawertitle.setText(firstName + " "+ lastName);

    /*    if (dbHelper.retreiveImageFromDB().length>0){
            dbHelper.open();
            dbHelper = new DBHelper(this);
            byte[] bytes = dbHelper.retreiveImageFromDB();
            circleImageView.setImageBitmap(Utils.getImage(bytes));
        }
        else {
            dbHelper.close();
        }*/


   //     circleImageView.loadImageFromDB();

//        Picasso.with(getApplicationContext())
//                .load(String.valueOf(loadImageFromDB()))
//                .into(circleImageView);

       // Picasso.with(this).load( "http://graph.facebook.com/"+userID+"/picture?type=small").into(circleImageView);
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {

            Logout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);

        } else {

            Logout.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);

        }

    }


    public void pickStartPosition(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    String pickupPlace;
    PojoLocation pojoLocation = new PojoLocation();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                pickupPlace = (String) place.getAddress();
                textViewPickup.setText(place.getAddress());
                pojoLocation.setLatLng(place.getLatLng());
                pojoLocation.setPlaceAddress(pickupPlace.replace(System.getProperty("line.separator"), " "));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), Config.MAP_ZOOM_LEVEL_PICKUP_LOCATION));

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraIdleListener(this);
        requestLocationPermission();

//        LatLng sydney = new LatLng(27.700769, 85.300140);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Kathmandu, Nepal"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.setOnMarkerDragListener(this);
        //mMap.setOnMapLongClickListener(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }


    }



    void locateMe() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestLocationPermission();
            return;
        }
        if (checkWeatherGPSEnabled()) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,Config.MAP_ZOOM_LEVEL_PICKUP_LOCATION));
                            }
                        }
                    });
        }
    }


    private boolean requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                WarningDialog warningDialog = new WarningDialog("Permission required", "We need the permission to locate you.", this);
                warningDialog.show(getFragmentManager(), "tag");
            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {
            locateMe();
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locateMe();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //region non map region
    void openDrawer() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }
    }

    public void openHome(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }

    //code here


    public void myAccount(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(this, MyAccountActivity.class);
            startActivity(intent);
        }
    }

    public void rideHistory(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            startActivity(new Intent(this, TripHistoryActivity.class));
        }
    }

    public void help(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);

            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }
    }

    public void settings(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(this, AccountSettingsActivity.class);
            startActivity(intent);
        }
    }

    public void logout(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            logoutUser();

        }
    }

    public void privacy(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(this, PrivacyPolicyActivity.class);
            startActivity(intent);
        }
    }

    public void aboutus(View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void scheduleRide(View view) {
        startActivity(new Intent(this, ScheduleRideActivity.class));
    }

    public void onClicksetpickuplocation(View view) {
        if (pickupPlace != null) {
            Intent intent = new Intent(this, DestinationActivity.class);
            intent.putExtra(Config.BUNDLE_KEY_PICKUP, pojoLocation.getPlaceAddress());
            intent.putExtra(Config.BUNDLE_KEY_PICKUP_LAT_LNG, pojoLocation.getLatLng());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please select the pickup location", Toast.LENGTH_SHORT).show();
        }
    }


    public void logoutUser() {
        session.setLogin(false);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
        try {
            if (dbHelper.retreiveImageFromDB().length > 0) {
                dbHelper.open();
                dbHelper.DeleteImage();
            } else {
                dbHelper.close();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

       // dbHelper.DeleteImage();
    }





    public void invalidateOthers(int id) {
        switch (id) {
            case R.id.textView4:
                Log.d("click", "clicked4..........." + textView4);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon, null), null, null);
                    textView6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon, null), null, null);
                    textView7.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon, null), null, null);
                } else {
                    textView5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon), null, null);
                    textView6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon), null, null);
                    textView7.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon), null, null);

                }
                break;
            case R.id.textView5:
                Log.d("click", "clicked5..........." + textView5);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon, null), null, null);
                    textView6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon, null), null, null);
                    textView7.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon, null), null, null);
                } else {
                    textView4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon), null, null);
                    textView6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon), null, null);
                    textView7.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon), null, null);

                }
                break;
            case R.id.textView6:
                Log.d("click", "clicked6..........." + textView6);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon, null), null, null);
                    textView4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon, null), null, null);
                    textView7.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon, null), null, null);
                } else {
                    textView5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon), null, null);
                    textView4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon), null, null);
                    textView7.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon), null, null);

                }
                break;
            case R.id.textView7:
                Log.d("click", "clicked7..........." + textView7);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon, null), null, null);
                    textView6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon, null), null, null);
                    textView4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon, null), null, null);
                } else {
                    textView5.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon), null, null);
                    textView6.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon), null, null);
                    textView4.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon), null, null);

                }
                break;
        }
    }

    public void ems(View view) {
//        //Todo change intent
//
        if (view.getTag().equals("0")) {

            invalidateOthers(view.getId());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon_active, null), null, null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon_active), null, null);
            }
            view.setTag("1");

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon, null), null, null);
            } else {

                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ems_icon), null, null);
            }

            view.setTag("0");
        }
//
    }

    //
    public void premiumcar(View view) {

        if (view.getTag().equals("0")) {
            invalidateOthers(view.getId());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon_active, null), null, null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon_active), null, null);
            }
            view.setTag("1");
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon, null), null, null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.car1_icon), null, null);

            }

            view.setTag("0");
        }
    }

    //
    public void van(View view) {
//
        if (view.getTag().equals("0")) {
            invalidateOthers(view.getId());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon_active, null), null, null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon_active), null, null);

            }
            view.setTag("1");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon, null), null, null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.van_icon), null, null);
            }

            view.setTag("0");
        }
    }

    //
    public void wheelcar(View view) {
        if (view.getTag().equals("0")) {
            invalidateOthers(view.getId());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon_active, null), null, null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon_active), null, null);

            }
            view.setTag("1");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon, null), null, null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.wheel_icon), null, null);

            }

            view.setTag("0");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    //endregion


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(MainActivity.this, "onMarkerDrag", Toast.LENGTH_SHORT).show();

    }


    //geocoding methods
    public LatLng mapCenterLatLng;
    private boolean didCameraMoved = false;

    @Override
    public void onCameraMove() {
        mapCenterLatLng = mMap.getCameraPosition().target;
        didCameraMoved = true;
    }

    @Override
    public void onCameraIdle() {
        if (didCameraMoved) {
            startIntentService();
            didCameraMoved = false;
        }

    }

    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;

    // ...

    protected void startIntentService() {
        mResultReceiver = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(GeoCoderConstants.RECEIVER, mResultReceiver);
        intent.putExtra(GeoCoderConstants.LOCATION_DATA_EXTRA, mapCenterLatLng);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        openDrawer();
    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(GeoCoderConstants.RESULT_DATA_KEY);
            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == GeoCoderConstants.SUCCESS_RESULT) {

                pickupPlace = resultData.getString(GeoCoderConstants.RESULT_DATA_KEY);
                textViewPickup.setText(pickupPlace);

                pojoLocation.setPlaceAddress(textViewPickup.getText().toString().replace(System.getProperty("line.separator"), " "));
                pojoLocation.setLatLng(mapCenterLatLng);
            } else {
//                showToast(getString(R.string.address_found));

            }
        }
    }

    private void displayAddressOutput() {

    }

    Boolean loadImageFromDB() {
        try {
            dbHelper.open();
            byte[] bytes = dbHelper.retreiveImageFromDB();
            dbHelper.close();
            // Show Image from DB in ImageView
            circleImageView.setImageBitmap(Utils.getImage(bytes));
            return true;
        } catch (Exception e) {
            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
//            dbHelper.close();
            return false;
        }
    }
}
