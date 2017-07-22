package taxiapp.safetrip.UI.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;

import taxiapp.safetrip.R;
import taxiapp.safetrip.application.AppController;
import taxiapp.safetrip.application.Config;
import taxiapp.safetrip.database.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etMobilenumber;
    EditText etPassword;
    EditText etConfpassword;
    TextView tvLoginnow;
    Button btn_signup;
    Typeface custom_font, custom_font2, custom_font3, custom_font4;
    private ProgressDialog progressDialog;

    String id, otp;
    private static final String TAG = "SignupActivity";
    Context context;

    private AwesomeValidation awesomeValidation;

    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DEVICE_TOKEN = "deviceToken";
    String Mobile, password, conform_password;


    private SessionManager session;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        etMobilenumber = (EditText) findViewById(R.id.et_mobilenu);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfpassword = (EditText) findViewById(R.id.et_confpassword);


        btn_signup = (Button) findViewById(R.id.btn_signup);
        tvLoginnow = (TextView) findViewById(R.id.tv_loginnow);
        session = new SessionManager(this);

        btn_signup.setOnClickListener(this);
        tvLoginnow.setOnClickListener(this);
      /*  addValidationToViews(this);*/

    }

 /*   private void addValidationToViews(Activity activity) {

        awesomeValidation.addValidation(activity, R.id.et_mobilenu, RegexTemplate.TELEPHONE, R.string.err_msg_phone);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(activity, R.id.et_password, regexPassword, R.string.passworderror);
        awesomeValidation.addValidation(activity, R.id.et_confpassword, R.id.et_password, R.string.confirmpassworderror);


    }
*/

    private void submitRequestForm() {
     /*   if (awesomeValidation.validate()) {*/
        // Toast.makeText(getActivity(), "Validation Successfull", Toast.LENGTH_LONG).show();

        Mobile = etMobilenumber.getText().toString();
        Log.d("SignUpActivity", "Mobile........" + Mobile);

        password = etPassword.getText().toString();
        Log.d("SignUpActivity", "password........" + password);

        conform_password = etConfpassword.getText().toString();
        Log.d("SignUpActivity", "conform_password........" + conform_password);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_API +
                Config.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        Log.d("SignUpActivity", "onresponse........" + response);


                /*    if (awesomeValidation.validate()) {*/
                        // Here, we are sure that form is successfully validated. So, do your stuffs now...

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("SignUpActivity", "jsonObject........" + jsonObject);

                            JSONObject user = jsonObject.getJSONObject("response");
                            Log.d("SignUpActivity", "user........" + user);

                            String status = user.getString("status");
                            Log.d("SignUpActivity", "status........" + status);
                            if (status.equals("1")) {

                                JSONObject jsonObject1 = user.getJSONObject("data");
                                Log.d("SignUpActivity", "jsonObject1........" + jsonObject1);

                                id = jsonObject1.getString("id");
                                Log.d("SignUpActivity", "id........" + id);


                                otp = jsonObject1.getString("otp");
                                Log.d("SignUpActivity", "otp........" + otp);

                                String message = jsonObject1.getString("message");
                                Log.d("SignUpActivity", "message........" + message);

                                MoveToNextActivity();


                            } else {

                                etMobilenumber.setText("");
                                etPassword.setText("");
                                etConfpassword.setText("");
                                // Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    /*}*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_MOBILE, Mobile);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_DEVICE_TOKEN, "1234");

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);
/*
        }*/

    }

    private void MoveToNextActivity() {
        Intent intent = new Intent(SignupActivity.this, OtpSignupActivity.class);
        intent.putExtra("USER_ID", id);
        intent.putExtra("OTP", otp);
        startActivity(intent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                submitRequestForm();
                break;
            case R.id.tv_loginnow:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                break;
        }

    }


}
