package taxiapp.safetrip.UI.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import taxiapp.safetrip.database.session.SessionManager;
import taxiapp.safetrip.database.sharedpreference.MySharedPref;
import taxiapp.safetrip.database.sharedpreference.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSION_FINE_LOC = 1001;
    private TextView signupnow;
    public static final String KEY_EMAIL = "mobile";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DEVICE_TOKEN = "deviceToken";

    EditText et_mobilenumber;

    EditText et_password;

    Button BtnLogin;

    Button BtnForget;

    String mobilenumber, password, device_token;

    Typeface custom_font, custom_font2, custom_font3, custom_font4;
    private MySharedPref mSharedPref;
    private SessionManager session;
    SharedPreferences sharedpreferences;

    private PrefManager pref;
    ProgressDialog progressDialog;
    private static String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //request permissions
        if (!checkMultiplePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_FINE_LOC);
        }
        mSharedPref = new MySharedPref(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        et_mobilenumber = (EditText) findViewById(R.id.et_mobilenumber);
        et_password = (EditText) findViewById(R.id.et_password);
        BtnForget = (Button) findViewById(R.id.btn_forget);
        BtnLogin = (Button) findViewById(R.id.btn_login);
        signupnow = (TextView) findViewById(R.id.signupnow);


        BtnForget.setOnClickListener(this);
        BtnLogin.setOnClickListener(this);
        signupnow.setOnClickListener(this);
        session = new SessionManager(this);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
        }



/*        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");


        et_mobilenumber.setTypeface(custom_font);
        et_password.setTypeface(custom_font);
        textView1.setTypeface(custom_font);
        signupnow.setTypeface(custom_font);
        BtnLogin.setTypeface(custom_font4);
        BtnForget.setTypeface(custom_font);*/

        MySharedPref sharedPref = new MySharedPref(this);
        sharedPref.isLogined();


        // Checking for user session
        // if user is already logged in, take him to main activity
      /*  if (pref.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }*/


    }


    private void userLogin() {

        mobilenumber = et_mobilenumber.getText().toString().trim();
        password = et_password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_API +
                Config.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        Log.d("LoginActivity", "onresponse........" + response);


                        try {

                            session.setLogin(true);
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("SignUpActivity", "jsonObject........" + jsonObject);

                            JSONObject user = jsonObject.getJSONObject("response");
                            Log.d("SignUpActivity", "user........" + user);

                            String status = user.getString("status");
                            Log.d("SignUpActivity", "status........" + status);
                            if (status.equals("1")) {


                                JSONObject jsonObject1 = user.getJSONObject("data");
                                Log.d("SignUpActivity", "jsonObject1........" + jsonObject1);

                                final String id = jsonObject1.getString("id");
                                Log.d("SignUpActivity", "id........" + id);

                                String message = jsonObject1.getString("message");
                                Log.d("SignUpActivity", "message........" + message);

                                JSONObject jsonObject2 = jsonObject1.getJSONObject("userDetails");
                                Log.d("SignUpActivity", "jsonObject1........" + jsonObject1);


                               final  String ids = jsonObject2.getString("id");
                                Log.d("LoginActivity", "ids........" + ids);

                                final String mobile = jsonObject2.getString("mobile");
                                Log.d("LoginActivity", "mobile........" + mobile);

                                String password = jsonObject2.getString("password");
                                Log.d("LoginActivity", "password........" + password);

                                final String deviceToken = jsonObject2.getString("deviceToken");
                                Log.d("LoginActivity", "deviceToken........" + deviceToken);

                                final String currentLat = jsonObject2.getString("currentLat");
                                Log.d("LoginActivity", "currentLat........" + currentLat);

                                final String currentLon = jsonObject2.getString("currentLon");
                                Log.d("LoginActivity", "currentLon........" + currentLon);

                                final String otp = jsonObject2.getString("otp");
                                Log.d("LoginActivity", "otp........" + otp);

                                final String isVerified = jsonObject2.getString("isVerified");
                                Log.d("LoginActivity", "isVerified........" + isVerified);

                                final String statuss = jsonObject2.getString("status");
                                Log.d("LoginActivity", "statuss........" + statuss);

                                final String registeredOn = jsonObject2.getString("registeredOn");
                                Log.d("LoginActivity", "registeredOn........" + registeredOn);

                                final String lastLogin = jsonObject2.getString("lastLogin");
                                Log.d("LoginActivity", "lastLogin........" + lastLogin);

                                final String userId = jsonObject2.getString("userId");
                                Log.d("LoginActivity", "userId........" + userId);

                                final String firstName = jsonObject2.getString("firstName");
                                Log.d("LoginActivity", "firstName........" + firstName);

                                final String lastName = jsonObject2.getString("lastName");
                                Log.d("LoginActivity", "lastName........" + lastName);

                                final String image = jsonObject2.getString("image");
                                Log.d("LoginActivity", "image........" + image);

                                final String email = jsonObject2.getString("email");
                                Log.d("LoginActivity", "email........" + email);

                                final String address = jsonObject2.getString("address");
                                Log.d("LoginActivity", "address........" + address);

                                final String lastModified = jsonObject2.getString("lastModified");
                                Log.d("LoginActivity", "lastModified........" + lastModified);




                                sharedpreferences = getApplicationContext().getSharedPreferences("sharedPrefName", 0);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("id", id);
                                editor.putString("idd", ids);
                                editor.putString("mobile", mobile);
                                editor.putString("deviceToken", deviceToken);
                                editor.putString("currentLat", currentLat);
                                editor.putString("currentLon", currentLon);
                                editor.putString("otp", otp);
                                editor.putString("isVerified", isVerified);
                                editor.putString("statuss", statuss);
                                editor.putString("registeredOn", registeredOn);
                                editor.putString("lastLogin", lastLogin);
                                editor.putString("userId", userId);
                                editor.putString("firstName", firstName);
                                editor.putString("lastName", lastName);
                                editor.putString("image", "");
                                editor.putString("email", email);
                                editor.putString("address", address);
                                editor.putString("lastModified", lastModified);

                                editor.apply();

                                MoveToNextActivity();

                            } else {
                                et_mobilenumber.setText("");
                                et_password.setText("");
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
                map.put(KEY_EMAIL, mobilenumber);
                map.put(KEY_PASSWORD, password);
                map.put(KEY_DEVICE_TOKEN, "1234");
                return map;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void MoveToNextActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                userLogin();
                // makeJsonObjectRequest();
                break;
            case R.id.btn_forget:
                Intent btn_forget = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(btn_forget);
                break;
            case R.id.signupnow:
                Intent signupnow = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signupnow);
                break;
        }

    }


}
