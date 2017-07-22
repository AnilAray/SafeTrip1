package taxiapp.safetrip.UI.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import taxiapp.safetrip.R;


public class DestinationActivity extends FragmentActivity{ /*implements OnMapReadyCallback {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 10001;
    private static final String TAG = "destination";
    private GoogleMap mMap;
    PojoLocation pojoPickupLocation = new PojoLocation();
    PojoLocation pojoDestinationLocation = new PojoLocation();
    @BindView(R.id.tv_destin)
    TextView tvDestin;
    @BindView(R.id.tv_pickup_point)
    TextView tvPickupPoint;
    @BindView(R.id.button_debit)
    Button ButtonDebit;
    @BindView(R.id.button_requestt)
    Button ButtonRequest;

    Typeface custom_font, custom_font2, custom_font3, custom_font4;

    String pickupLocation;

    private Intent intent;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
       /* ButterKnife.bind(this);

        intent = getIntent();
        pickupLocation = intent.getStringExtra(AppConstants.BUNDLE_KEY_PICKUP);

        pojoPickupLocation.setPlaceAddress(pickupLocation);
        pojoPickupLocation.setLatLng((LatLng) intent.getParcelableExtra(AppConstants.BUNDLE_KEY_PICKUP_LAT_LNG));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");

        ButtonDebit.setTypeface(custom_font2);
        tvDestin.setTypeface(custom_font);

        initViews();*/
    }

    /*private void initViews() {
        tvPickupPoint.setTypeface(custom_font);
        tvPickupPoint.setText(pickupLocation);

    }

    public void selectDestination(View view) {
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

    String destintionAddress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                destintionAddress = String.valueOf(place.getAddress());
                tvDestin.setText(destintionAddress);
                pojoDestinationLocation.setLatLng(place.getLatLng());
                pojoDestinationLocation.setPlaceAddress(destintionAddress.replace(System.getProperty("line.separator"), " "));

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.red_pin);

                mMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng())
                        .icon(icon)
                );
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), AppConstants.MAP_ZOOM_LEVEL_DESTINATION));

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    *//**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.green_pin);

        mMap.addMarker(new MarkerOptions()
                .position(pojoPickupLocation.getLatLng())
                .icon(icon)
        );
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pojoPickupLocation.getLatLng(), AppConstants.MAP_ZOOM_LEVEL_DESTINATION));


        // Add a marker in Sydney and move the camera

    }


    public void btnDebitClickes(View view) {
        Intent intent = new Intent(this, ChoosePaymentTypeActivity.class);
        startActivity(intent);

    }

    public void btnRequestOnClick(View view) {
        if (pojoPickupLocation.getPlaceAddress().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please choose your  pickup location location", Toast.LENGTH_SHORT).show();
            return;
        } else if (pojoDestinationLocation.getPlaceAddress() != null) {
            if (pojoDestinationLocation.getPlaceAddress().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter your destination location", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Intent intent = new Intent(this, ConfirmRequestActivity.class);
                intent.putExtra("pickupAddress", pojoPickupLocation.getPlaceAddress());
                intent.putExtra("destinationAddress", pojoDestinationLocation.getPlaceAddress());
                intent.putExtra("pickupLatLng", pojoPickupLocation.getLatLng());
                intent.putExtra("destinationLatLng", pojoDestinationLocation.getLatLng());
                startActivity(intent);
            }


        }
    }*/
}