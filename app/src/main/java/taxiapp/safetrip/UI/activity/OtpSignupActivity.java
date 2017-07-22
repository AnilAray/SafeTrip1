package taxiapp.safetrip.UI.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import taxiapp.safetrip.R;
import taxiapp.safetrip.application.AppController;
import taxiapp.safetrip.application.Config;
import taxiapp.safetrip.database.sharedpreference.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class OtpSignupActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvCodeSent;
    Button ButtonSubmit,btnResend;
    String id;
    Typeface custom_font, custom_font4;
    String OTP1, USERID1;

    public static final String OTP = "otp";
    public static final String USERID = "userId";
    private ProgressDialog progressDialog;
    private PrefManager pref;
    EditText etOtp1,etOtp2,etOtp3,etOtp4;
    private TextView otpCountDown;
    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpscreen_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        OTP1 = getIntent().getStringExtra("OTP");
        Log.d("OtpSignupActivity", "OTP........" + OTP1);

        USERID1 = getIntent().getStringExtra("USER_ID");
        Log.d("OtpSignupActivity", "USERID........" + USERID1);


        etOtp1 = (EditText)findViewById(R.id.et_otp1);
        etOtp2 = (EditText)findViewById(R.id.et_otp2);
        etOtp3 = (EditText)findViewById(R.id.et_otp3);
        etOtp4 = (EditText)findViewById(R.id.et_otp4);

        otpCountDown = (TextView) findViewById(R.id.otpCountDown);

        ButtonSubmit = (Button)findViewById(R.id.btn_submit);
        btnResend = (Button)findViewById(R.id.btn_resendcode);
        ButtonSubmit.setOnClickListener(this);
        btnResend.setOnClickListener(this);

 /*       custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");

        tvCodeSent.setTypeface(custom_font);
        ButtonSubmit.setTypeface(custom_font4);
*/

        // Checking for user session
        // if user is already logged in, take him to main activity
      /*  if (pref.isLoggedIn()) {
            Intent intent = new Intent(OtpSignupActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
*/


        etOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etOtp1.getText().length() == 1) {
                    etOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etOtp2.getText().length() == 1) {
                    etOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etOtp3.getText().length() == 1) {
                    etOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        countDown();


    }

    private void countDown() {
        new CountDownTimer(70000, 1000) { // adjust the milli seconds here

            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onTick(long millisUntilFinished) {
                otpCountDown.setVisibility(View.VISIBLE);
                otpCountDown.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) ));
            }

            public void onFinish() {
                otpCountDown.setVisibility(View.GONE);
                btnResend.setEnabled(true);
            }
        }.start();
    }

    public void back(View view) {
        this.finish();
    }

    private static final String TAG = "otpScreen";

    private void userLogin() {


        String char1 = etOtp1.getText().toString().trim();
        String char2 = etOtp2.getText().toString().trim();
        String char3 = etOtp3.getText().toString().trim();
        String char4 = etOtp4.getText().toString().trim();


        final String filledOtp = char1 + char2 + char3 + char4;
        Log.d("OtpSignupActivity", "filledOtp........" + filledOtp);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_API +
                Config.URL_UPDATE_VERIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        Log.d("OtpSignupActivity", "onresponse........" + response);


                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("OtpSignupActivity", "jsonObject........" + jsonObject);

                            JSONObject user = jsonObject.getJSONObject("response");
                            Log.d("OtpSignupActivity", "user........" + user);

                            String status = user.getString("status");
                            Log.d("OtpSignupActivity", "status........" + status);
                            if (status.equals("1")) {


                                JSONObject jsonObject1 = user.getJSONObject("data");
                                Log.d("OtpSignupActivity", "jsonObject1........" + jsonObject1);

                                id = jsonObject1.getString("id");
                                Log.d("OtpSignupActivity", "id........" + id);

                                String message = jsonObject1.getString("message");
                                Log.d("OtpSignupActivity", "message........" + message);


                                if(filledOtp.equals(OTP1)){

                                    Log.d("OtpSignupActivity", "filledOtp1........" + OTP.equals(filledOtp));
                                    Log.d("OtpSignupActivity", "filledOtp........" + filledOtp);
                                    Log.d("OtpSignupActivity", "OTP........" + OTP);


                                    MoveToNextActivity();
                                }




                            } else {
                                etOtp1.setText("");
                                etOtp2.setText("");
                                etOtp3.setText("");
                                etOtp4.setText("");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        //hidepDialog();
                    }
                }, new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                // hide the progress dialog
                //hidepDialog();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(USERID, USERID1);
                map.put(OTP, OTP1);

                return map;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void MoveToNextActivity() {
        Intent intent = new Intent(OtpSignupActivity.this, AccountCreate.class);
        intent.putExtra("USER_IDD",id);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_submit:
                Log.d("OtpSignupActivity", "clicked........"+ButtonSubmit );

                userLogin();
                break;
            case R.id.btn_resendcode:
                Log.d("OtpSignupActivity", "clicked........" +btnResend);

                countDown();
                break;

        }

    }
}
