package taxiapp.safetrip.UI.activity;


import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;
import taxiapp.safetrip.database.sqlite.DBHelper;
import taxiapp.safetrip.utils.Utility;
import taxiapp.safetrip.utils.Utils;


public class AccountCreate extends AppCompatActivity {


    CircleImageView imgAvtaar;
    ImageView imgCamera;
    EditText etFname;
    EditText etLname;
    EditText etAddress;
    EditText etEmail;
    Button Save;
    private static final String TAG = "CreateAccount";
    String firstname, lastname, address, email;
    String id;
    TextView tv_title;

    Typeface custom_font, custom_font2, custom_font3, custom_font4;
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;
    private Bitmap photo = null;
    private ProgressDialog progressDialog;
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";
    public static final String IMAGE = "image";
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    DBHelper dbHelper;
    RelativeLayout relativeLayout;
    String USERID1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_create);
        Log.d("CreateAcc", "onresponse........");


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        imgAvtaar = (CircleImageView) findViewById(R.id.CreateAccountActivity_img_avtaar);
        imgCamera = (ImageView) findViewById(R.id.CreateAccountActivity_img_camera);
        etFname = (EditText) findViewById(R.id.CreateAccountActivity_et_fname);
        etLname = (EditText) findViewById(R.id.CreateAccountActivity_et_lname);
        etAddress = (EditText) findViewById(R.id.CreateAccountActivity_et_address);
        etEmail = (EditText) findViewById(R.id.CreateAccountActivity_et_email);
        tv_title = (TextView) findViewById(R.id.tv_title);
        Save = (Button) findViewById(R.id.CreateAccountActivity_btn_save1);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  userLogin();
                //userLogins();

                Log.d("CreateAccountActivity", "onresponse........");


            }
        });
        relativeLayout = (RelativeLayout) findViewById(R.id.container);

        imgCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        dbHelper = new DBHelper(this);
        loadImageFromDB();

        USERID1 = getIntent().getStringExtra("USER_IDD");
        Log.d("OtpSignupActivity", "USERID........" + USERID1);


        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");

        etFname.setTypeface(custom_font);
        etLname.setTypeface(custom_font);
        etAddress.setTypeface(custom_font);
        etEmail.setTypeface(custom_font);
        Save.setTypeface(custom_font4);

        tv_title.setTypeface(custom_font3);
        tv_title.setText("Create Account");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AccountCreate.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(AccountCreate.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = data.getData();
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);

                if (null != selectedImageUri) {

                    // Saving to Database...
                    if (saveImageInDB(selectedImageUri)) {
                        showMessage("Image Saved in Database...");
                        imgAvtaar.setImageURI(selectedImageUri);
                    }

                    // Reading from Database after 3 seconds just to show the message
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadImageFromDB()) {
                                showMessage("Image Loaded from Database...");
                            }
                        }
                    }, 3000);
                }
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
                if (null != selectedImageUri) {

                    // Saving to Database...
                    if (saveImageInDB(selectedImageUri)) {
                        showMessage("Image Saved in Database...");
                        imgAvtaar.setImageURI(selectedImageUri);
                    }

                    // Reading from Database after 3 seconds just to show the message
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadImageFromDB()) {
                                showMessage("Image Loaded from Database...");
                            }
                        }
                    }, 3000);
                }
            }
        }
    }


    // Save the
    Boolean saveImageInDB(Uri selectedImageUri) {

        try {
            dbHelper.open();
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = Utils.getBytes(iStream);
            dbHelper.insertImage(inputData);
            dbHelper.close();
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            dbHelper.close();
            return false;
        }

    }

    Boolean loadImageFromDB() {
        try {
            dbHelper.open();
            byte[] bytes = dbHelper.retreiveImageFromDB();
            dbHelper.close();
            // Show Image from DB in ImageView
            imgAvtaar.setImageBitmap(Utils.getImage(bytes));
            return true;
        } catch (Exception e) {
            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
            dbHelper.close();
            return false;
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgAvtaar.setImageBitmap(thumbnail);
        Log.d("response_status", "status" + thumbnail);


    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgAvtaar.setImageBitmap(bm);


    }

    // Show simple message using SnackBar
    void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(relativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();


    }


    public void back(View view) {
        this.finish();
    }

    private void userLogin() {


        firstname = etFname.getText().toString().trim();
        lastname = etLname.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        address = etAddress.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Config.URL_UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        Log.d("CreateAccountActivity", "onresponse........" + response);


                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("CreateAccountActivity", "jsonObject........" + jsonObject);

                            JSONObject user = jsonObject.getJSONObject("response");
                            Log.d("CreateAccountActivity", "user........" + user);

                            String status = user.getString("status");
                            Log.d("CreateAccountActivity", "status........" + status);
                            if (status.equals("1")) {


                                JSONObject jsonObject1 = user.getJSONObject("data");
                                Log.d("CreateAccountActivity", "jsonObject1........" + jsonObject1);

                                id = jsonObject1.getString("id");
                                Log.d("CreateAccountActivity", "id........" + id);

                                String message = jsonObject1.getString("message");
                                Log.d("CreateAccountActivity", "message........" + message);


                                MoveToNextActivity();

                            } else {
                                etFname.setText("");
                                etLname.setText("");
                                etEmail.setText("");
                                etAddress.setText("");
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
                map.put(USER_ID, USERID1);
                map.put(FIRST_NAME, firstname);
                map.put(LAST_NAME, lastname);
                map.put(ADDRESS, address);
                map.put(EMAIL, email);


                return map;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void MoveToNextActivity() {
        Intent intent = new Intent(AccountCreate.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    /*@Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.CreateAccountActivity_btn_save1:
                userLogin();
                // makeJsonObjectRequest();
                break;

        }

    }*/
}