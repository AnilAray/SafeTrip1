package taxiapp.safetrip.UI.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import taxiapp.safetrip.R;
import taxiapp.safetrip.database.session.SessionManager;
import taxiapp.safetrip.database.sqlite.DBHelper;
import taxiapp.safetrip.utils.Utils;

public class AccountSettingsActivity extends AppCompatActivity {
    private static final String TAG = "AccountSetting";
    private CircleImageView imgAvtaar;
    private TextView tvFavarities;
    private TextView tvHome;
    private TextView tvWork;
    private TextView tvSignout;
    private EditText etEmergency;
    private EditText dmobile;
    private EditText etDrivername;
    private SessionManager session;
    RelativeLayout relativeLayout;
    TextView tv_title;
    ImageView imageView;
    DBHelper dbHelper;
    String firstName, lastName,mobile;


    Typeface custom_font, custom_font2, custom_font3, custom_font4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_activity);


        imgAvtaar = (CircleImageView) findViewById(R.id.img_avtaar_account_setting);
        tvFavarities = (TextView) findViewById(R.id.tv_favarities);
        tvHome = (TextView) findViewById(R.id.tv_home);
        tvWork = (TextView) findViewById(R.id.tv_work);
        tvSignout = (TextView) findViewById(R.id.tv_signout1);
        tvSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        etEmergency = (EditText) findViewById(R.id.et_emergency);
        dmobile = (EditText) findViewById(R.id.dmobile);
        etDrivername = (EditText) findViewById(R.id.et_drivername);
        tv_title = (TextView) findViewById(R.id.tv_title);
        relativeLayout = (RelativeLayout) findViewById(R.id.tv_signout_view);
        dbHelper = new DBHelper(this);
        tv_title.setText("Account Settings");
        loadImageFromDB();

        SharedPreferences prefs = getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        firstName = prefs.getString("firstName", null);
        Log.e("Emaill_response", "firstName" + firstName);

        lastName = prefs.getString("lastName", null);
        Log.e("Emaill_response", "firstName" + lastName);

        mobile = prefs.getString("mobile", null);
        Log.e("Emaill_response", "mobile" + mobile);

        dmobile.setText(mobile);
        etDrivername.setText(firstName + " "+ lastName);


/*        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");


        tvFavarities.setTypeface(custom_font);
        tvHome.setTypeface(custom_font);
        tvWork.setTypeface(custom_font);
        tvSignout.setTypeface(custom_font);
        etEmergency.setTypeface(custom_font);
        tv_title.setTypeface(custom_font3);
        dmobile.setTypeface(custom_font);
        etDrivername.setTypeface(custom_font2);*/

        session = new SessionManager(getApplicationContext());



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


    public void back(View view) {
        this.finish();
    }


    public void accountedit(View view) {
        Intent intent = new Intent(this, EditAccountActivity.class);
        startActivity(intent);

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
       /* try {
            if (dbHelper.retreiveImageFromDB().length > 0) {
                dbHelper.open();
                dbHelper.DeleteImage();
            } else {
                dbHelper.close();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
*/
    }


}
