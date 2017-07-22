package taxiapp.safetrip.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;


import taxiapp.safetrip.R;
import taxiapp.safetrip.database.sharedpreference.MySharedPref;


public class SplashScreenActivity extends AppCompatActivity {
    private int mInterval = 20;
    private Handler mHandler;


    ProgressBar progressBar;
    private MySharedPref msharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        progressBar = (ProgressBar)findViewById(R.id.determinateBar);

        msharedPref = new MySharedPref(getApplicationContext());

        mHandler = new Handler();
        startRepeatingTask();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    private int progress = 0;
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                if (progress < 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress = updateStatusProgress();
                            progressBar.setProgress(progress);
                        }
                    });
                    mHandler.postDelayed(mStatusChecker, mInterval);
                } else {
                    if (msharedPref.isLogined()) {
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    }
                    SplashScreenActivity.this.finish();
                }

            } catch (Exception e) {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                e.printStackTrace();
            }
        }
    };

    private int updateStatusProgress() {
        progress = progress + 1;
//        while(progress<100){
//            return progress;
//        }

        return progress;
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}


