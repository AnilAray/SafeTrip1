package taxiapp.safetrip.database.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import taxiapp.safetrip.R;


public class MySharedPref {

    private static final String LOGIN_STATUS = "LOGIN_STATUS";
    private static final String USER_ID = "USER_ID";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public MySharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.sharedPref), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    boolean isLogined;
    String userId;

    public boolean isLogined() {
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void setLogined(boolean logined) {
        editor.putBoolean(LOGIN_STATUS, logined);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void setUserId(String userId) {

        editor.putString(USER_ID, userId);
        editor.commit();
    }
}


