package taxiapp.safetrip.UI.activity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import taxiapp.safetrip.R;


public class PrivacyPolicyActivity extends AppCompatActivity  {

TextView tvPrivacy;
    InputStream is;
    AssetManager am;
    Typeface custom_font, custom_font3;
    private StringBuilder text = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
        tvPrivacy = (TextView)findViewById(R.id.tv_privacy);


        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("Privacy.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
                text.append('\n');
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
            tvPrivacy.setText((CharSequence) text);

/*
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Light.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");

        tvTitle.setText("Privacy Policy");
        tvTitle.setTypeface(custom_font3);
        tvPrivacy.setTypeface(custom_font);*/

    }
    public void back(View view){
        this.finish();
    }

}
