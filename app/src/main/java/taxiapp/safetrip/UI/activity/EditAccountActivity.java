package taxiapp.safetrip.UI.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import taxiapp.safetrip.R;
import taxiapp.safetrip.application.AppController;
import taxiapp.safetrip.application.Config;
import taxiapp.safetrip.database.sqlite.DBHelper;
import taxiapp.safetrip.utils.Utility;
import taxiapp.safetrip.utils.Utils;


public class EditAccountActivity extends AppCompatActivity/* implements PopupMenu.OnMenuItemClickListener*/ {

    CircleImageView imgAvtaar;
    ImageView imgCamera;
    EditText etFname;
    EditText etLname;
    EditText etAddress;
    EditText etEmail;
    TextView etUsermobile;
    TextView tvTitle;
    Typeface custom_font, custom_font2, custom_font3, custom_font4;

    private final int REQUEST_CODE_GALLERY = 1;
    private final int REQUEST_CODE_CAMERA = 2;
    private AlertDialog builder;
    LinearLayout linearLayout;
    DBHelper dbHelper;
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";
    public static final String IMAGE = "image";

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String userChoosenTask;
    String firstName, lastName, address, email, mobile;
    String firstName1, lastName1, address1, email1, mobile1, id, USERID1;
    Button edit_account_btn_save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);
        Log.d("EditAccountActivity", "USERID........" );


        imgAvtaar = (CircleImageView) findViewById(R.id.edit_account_img_avtaar);
        imgCamera = (ImageView) findViewById(R.id.edit_account_img_camera);
        etFname = (EditText) findViewById(R.id.edit_account_et_fname);
        etLname = (EditText) findViewById(R.id.edit_account_et_lname);
        etAddress = (EditText) findViewById(R.id.edit_account_et_address);
        etEmail = (EditText) findViewById(R.id.edit_account_et_email);
        etUsermobile = (TextView) findViewById(R.id.edit_account_et_usermobile);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        linearLayout = (LinearLayout) findViewById(R.id.edit_account_linearLayout);
        edit_account_btn_save_button = (Button) findViewById(R.id.edit_account_btn_save);
        edit_account_btn_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        USERID1 = getIntent().getStringExtra("USER_IDD");
        Log.d("EditAccountActivity", "USERID........" + USERID1);

        dbHelper = new DBHelper(this);
        loadImageFromDB();
//        Log.d("EditAccountActivity", "dbHelper........" + dbHelper.retreiveImageFromDB().length);

        tvTitle.setText("Edit Settings");

        SharedPreferences prefs = getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        firstName = prefs.getString("firstName", null);
        Log.e("EditAccountActivity", "firstName" + firstName);
        etFname.setText(firstName);

        lastName = prefs.getString("lastName", null);
        Log.e("EditAccountActivity", "firstName" + lastName);
        etLname.setText(lastName);

        address = prefs.getString("address", null);
        Log.e("EditAccountActivity", "address" + address);
        etAddress.setText(address);

        email = prefs.getString("email", null);
        Log.e("EditAccountActivity", "email" + email);
        etEmail.setText(email);

        mobile = prefs.getString("mobile", null);
        Log.e("EditAccountActivity", "mobile" + mobile);
        etUsermobile.setText(mobile);





    /*    custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");

        etFname.setTypeface(custom_font);
        etLname.setTypeface(custom_font);
        etAddress.setTypeface(custom_font);
        etEmail.setTypeface(custom_font);
        etUsermobile.setTypeface(custom_font);*/

        tvTitle.setTypeface(custom_font3);

    }

    public void back(View view) {
        this.finish();
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
            Log.e("TAG", "<loadImageFromDB> Error : " + e.getLocalizedMessage());
            dbHelper.close();
            return false;
        }
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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditAccountActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditAccountActivity.this);

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

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
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
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgAvtaar.setImageBitmap(bm);
    }


    private void userLogin() {


        firstName1 = etFname.getText().toString().trim();
        Log.d("EditAccountActivity", "firstName1........" + firstName1);

        lastName1 = etLname.getText().toString().trim();
        Log.d("EditAccountActivity", "lastName1........" + lastName1);

        email1 = etEmail.getText().toString().trim();
        Log.d("EditAccountActivity", "email1........" + email1);

        address1 = etAddress.getText().toString().trim();
        Log.d("EditAccountActivity", "address1........" + address1);



        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Config.URL_UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response.toString());
                        Log.d("EditAccountActivity", "onresponse........" + response);


                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("EditAccountActivity", "jsonObject........" + jsonObject);

                            JSONObject user = jsonObject.getJSONObject("response");
                            Log.d("EditAccountActivity", "user........" + user);

                            String status = user.getString("status");
                            Log.d("EditAccountActivity", "status........" + status);
                            if (status.equals("1")) {


                                JSONObject jsonObject1 = user.getJSONObject("data");
                                Log.d("EditAccountActivity", "jsonObject1........" + jsonObject1);

                                id = jsonObject1.getString("id");
                                Log.d("EditAccountActivity", "id........" + id);

                                String message = jsonObject1.getString("message");
                                Log.d("EditAccountActivity", "message........" + message);


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
                VolleyLog.d("TAG", "Error: " + error.getMessage());
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
                map.put(FIRST_NAME, firstName1);
                map.put(LAST_NAME, lastName1);
                map.put(ADDRESS, address1);
                map.put(EMAIL, email1);


                return map;

            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void MoveToNextActivity() {
        Intent intent = new Intent(EditAccountActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
            Log.e("TAG", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            dbHelper.close();
            return false;
        }

    }


    // Show simple message using SnackBar
    void showMessage(String message) {
     /*   Snackbar snackbar = Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();

*/
    }
}
