package taxiapp.safetrip.UI.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import taxiapp.safetrip.R;


public class AboutUsActivity extends AppCompatActivity  {

   TextView tvAboutus;
     TextView tvTitle;



    Typeface custom_font, custom_font3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

/*        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Light.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");

        tvTitle.setText("About Us");

        tvTitle.setTypeface(custom_font3);
        tvAboutus.setTypeface(custom_font);*/
    }

    public void back(View view){
        this.finish();
    }

}
