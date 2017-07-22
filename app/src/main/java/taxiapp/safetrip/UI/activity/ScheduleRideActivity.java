package taxiapp.safetrip.UI.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


import taxiapp.safetrip.R;
/*import taxiapp.safetrip.RequestingScreenActivity;
import taxiapp.safetrip.models.pojos.PojoLocation;
import taxiapp.safetrip.ui.dialogs.DatePickerFragment;
import taxiapp.safetrip.ui.dialogs.TimePickerFragment;
import taxiapp.safetrip.utils.GlobalPojos;*/


public class ScheduleRideActivity extends AppCompatActivity {/*implements TimePickerFragment.TimeSelectorListner, DatePickerFragment.DateSelectionListner {

    private static final int PICKUP_AUTOCOMPLETE_REQUEST_CODE = 1403;
    private static final int DESTINATION_AUTOCOMPLETE_REQUEST_CODE = 1404;
    @BindView(R.id.et_pickup_date)
    EditText et_pickup_date;
    @BindView(R.id.pickuptime)
    EditText pickuptime;
    @BindView(R.id.radio_btn_source)
    TextView tvSource;
    @BindView(R.id.radio_btn_destination)
    TextView tvDestination;
    @BindView(R.id.tv_premiumcar)
    TextView tvPremiumcar;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.tv_fare)
    TextView tvFare;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.img_backarrow)
    ImageView img_backarrow;

    Typeface custom_font, custom_font2, custom_font3, custom_font4;
    private String TAG = "Scheduleride";

*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_ride_activity);
       /* ButterKnife.bind(this);

        tv_title.setText("Schedule A Ride");

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");

        tv_title.setTypeface(custom_font3);
        et_pickup_date.setTypeface(custom_font);
        pickuptime.setTypeface(custom_font);
        tvSource.setTypeface(custom_font);
        tvDestination.setTypeface(custom_font);
        tvPremiumcar.setTypeface(custom_font);
        tvChange.setTypeface(custom_font);
        tvFare.setTypeface(custom_font3);


        String pickupdate = et_pickup_date.getText().toString();
        String picktime = pickuptime.getText().toString();
*/
    }

   /* public void back(View view) {
        this.finish();
    }


    @OnClick(R.id.pickuptime)
    void pickupTime() {
        TimePickerFragment timePickerFragment = new TimePickerFragment();

        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @OnClick(R.id.et_pickup_date)
    void et_pickup_date() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @OnClick(R.id.radio_btn_source)
    void getPickupLocation() {

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PICKUP_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @OnClick(R.id.radio_btn_destination)
    void getDestinationLocation() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, DESTINATION_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKUP_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                String destintionAddress = String.valueOf(place.getAddress());
                tvSource.setText(destintionAddress);
                PojoLocation pojoPickupLocation = new PojoLocation();
                pojoPickupLocation.setLatLng(place.getLatLng());
                pojoPickupLocation.setPlaceAddress(destintionAddress.replace(System.getProperty("line.separator"), " "));

                GlobalPojos.pojoPickupLocation = pojoPickupLocation;
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.red_pin);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == DESTINATION_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                String destintionAddress = String.valueOf(place.getAddress());
                tvDestination.setText(destintionAddress);
                PojoLocation pojoDestinationLocation = new PojoLocation();
                pojoDestinationLocation.setLatLng(place.getLatLng());
                pojoDestinationLocation.setPlaceAddress(destintionAddress.replace(System.getProperty("line.separator"), " "));

                GlobalPojos.pojoDestinationLocation = pojoDestinationLocation;
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.red_pin);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


    }


    @Override
    public void onTimeSelected(String time) {
        pickuptime.setText(time);
    }

    @Override
    public void onDateSelected(String date) {
        et_pickup_date.setText(date);
    }

    @OnClick(R.id.btn_schedule)
    void onBtnScheduleClick() {
        //TODO implement
        if (GlobalPojos.pojoPickupLocation == null) {
            Toast.makeText(this, "Pleas select the starting position.", Toast.LENGTH_LONG).show();
        } else if (GlobalPojos.pojoDestinationLocation == null) {
            Toast.makeText(this, "Pleas select the destination position.", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(this, RequestingScreenActivity.class);
            startActivity(intent);
            finish();

        }
    }


    @OnLongClick(R.id.btn_schedule)
    boolean onBtnScheduleLongClick() {
        //TODO implement
        return true;
    }*/
}
