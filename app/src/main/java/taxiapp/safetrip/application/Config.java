package taxiapp.safetrip.application;

/**
 * Created by NIKS on 31-05-2017.
 */

public class Config {

    public static final String URL_API = "http://experttechnics.com/mobile/safetrip/ws/";
    public static final String URL_REGISTER = "register.php";
    public static final String URL_LOGIN = "login.php";
    public static final String URL_UPDATE_VERIFICATION = "updateVerification.php";//otp
    public static final String URL_CHANGE_PASSWORD = "changePassword.php";
    public static final String URL_FORGOT_PASSWORD = "forgotPassword.php";

    public static final String URL_VERIFY_OTP_FOR_FORGOT_PASSWORD = "verifyOtpForForgot.php";
   // public static final String URL_UPDATE_PROFILE = "updateProfile.php";//create profile//setting update profile
    public static final String URL_UPDATE_PROFILE = "http://experttechnics.com/mobile/safetrip/ws/updateProfile.php";
    public static final String URL_RETURN_NEAR_BY_DRIVER = "returnNearByDrivers.php";
    public static final String URL_BOOK_RIDE = "bookRide.php";
    public static final String URL_RETURN_DRIVER_PROFILE = "returnDriverProfile.php";



    public static final String BUNDLE_KEY_PICKUP_LAT_LNG = "BUNDLE_KEY_PICKUP_LAT_LNG";
    public static  String  USER_ID= null;
    public static final String BUNDLE_KEY_PICKUP = "BUNDLE_KEY_PICKUP";
    public static final int MAP_ZOOM_LEVEL_PICKUP_LOCATION=18;
    public static final int MAP_ZOOM_LEVEL_DESTINATION=15;
    public static final int MAP_ZOOM_LEVEL_PATH=13;
    public static final int MAP_ZOOM_LEVEL_CONFIRMATION=12;

}
