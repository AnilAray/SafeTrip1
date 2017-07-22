package taxiapp.safetrip.UI.activity;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import taxiapp.safetrip.R;

public class HelpActivity extends AppCompatActivity{ /*implements View.OnClickListener {

    @BindView(R.id.tv_cancellationfee)
    TextView tvCancellationfee;
    @BindView(R.id.tv_involvedaccident)
    TextView tvInvolvedaccident;
    @BindView(R.id.tv_lostitem)
    TextView tvLostitem;
    @BindView(R.id.tv_unprofessional)
    TextView tvUnprofessional;
    @BindView(R.id.tv_badroute)
    TextView tvBadroute;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_cannotrequestride)
    TextView tvCannotrequestride;
    @BindView(R.id.tv_viechleexpected)
    TextView tvViechleexpected;
    @BindView(R.id.img_backarrow)
    ImageView img_backarrow;

    Typeface custom_font, custom_font2, custom_font3, custom_font4;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
      /*  ButterKnife.bind(this);
        tv_title.setText("HELP");

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");


        tv_title.setTypeface(custom_font3);

        tvCancellationfee.setTypeface(custom_font);
        tvInvolvedaccident.setTypeface(custom_font);
        tvLostitem.setTypeface(custom_font);
        tvUnprofessional.setTypeface(custom_font);
        tvBadroute.setTypeface(custom_font);
        tvCannotrequestride.setTypeface(custom_font);
        tvViechleexpected.setTypeface(custom_font);


        tvCancellationfee.setOnClickListener(this);
        tvInvolvedaccident.setOnClickListener(this);
        tvLostitem.setOnClickListener(this);
        tvUnprofessional.setOnClickListener(this);
        tvBadroute.setOnClickListener(this);
        tvCannotrequestride.setOnClickListener(this);
        tvViechleexpected.setOnClickListener(this);*/

    }
/*

    public void back(View view) {
        this.finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_cancellationfee:
                setDialogMenu();
                break;
            case R.id.tv_involvedaccident:
                setDialogMenu();
                break;
            case R.id.tv_lostitem:
                setDialogMenu();
                break;
            case R.id.tv_unprofessional:
                setDialogMenu();
                break;
            case R.id.tv_badroute:
                setDialogMenu();
                break;
            case R.id.tv_cannotrequestride:
                setDialogMenu();
                break;
            case R.id.tv_viechleexpected:
                setDialogMenu();
                break;

        }
    }


    private void setDialogMenu() {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        mDialog.setTitle("Feedback");
        mDialog.setMessage("Confirm to send Feedback");
        mDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
*/

}

