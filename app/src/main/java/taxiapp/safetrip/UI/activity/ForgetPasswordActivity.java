package taxiapp.safetrip.UI.activity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import taxiapp.safetrip.R;


public class ForgetPasswordActivity extends AppCompatActivity/* implements View.OnClickListener*/ {

    EditText etForgetMobilenumber;
    TextView tv_title;
    Button Submit;
    ImageView img_backarrow;
    Typeface custom_font, custom_font2, custom_font3, custom_font4;

    String mobileNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        tv_title.setText("Forget Password");

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");

        etForgetMobilenumber.setTypeface(custom_font);
        Submit.setTypeface(custom_font4);


    }
/*
    public void back(View view) {
        this.finish();
    }


    @OnClick(R.id.forgetbtn_submit)
    void onForgetbtnSubmitClick(final View view) {
        //TODO implement


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        mobileNumber = etForgetMobilenumber.getText().toString();

        if (mobileNumber.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (mobileNumber.length() == 10) {
            progressDialog.show();
            view.setEnabled(false);

            apiInterface.forgetPassword(mobileNumber).enqueue(new Callback<ForgetPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                    view.setEnabled(true);
                    progressDialog.dismiss();

                    ForgetPasswordResponse forgetPasswordResponse = response.body();
                    if (forgetPasswordResponse.getResult().getStatus().equals(-1)) {
                        Toast.makeText(getApplicationContext(), forgetPasswordResponse.getResult().getData().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        if (forgetPasswordResponse.getResult().getStatus().equalsIgnoreCase("1")) {
                            Toast.makeText(ForgetPasswordActivity.this, forgetPasswordResponse.getResult().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetPasswordActivity.this, OtpForgetPassActivity.class);
                            intent.putExtra("mobile", mobileNumber);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, forgetPasswordResponse.getResult().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.d("TAG", "onResponse: " + response.body().getResult().getData().getMessage());
                }

                @Override
                public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                    Toast.makeText(ForgetPasswordActivity.this, "Error retrieving password", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    view.setEnabled(true);
                    progressDialog.dismiss();
                }
            });
        }
    }

    @OnLongClick(R.id.forgetbtn_submit)
    boolean onForgetbtnSubmitLongClick() {
        //TODO implement
        return true;
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
    }*/
}
