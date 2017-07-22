package taxiapp.safetrip.UI.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;


import de.hdodenhof.circleimageview.CircleImageView;
import taxiapp.safetrip.R;

public class MyAccountActivity extends AppCompatActivity{/* implements PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.container) RelativeLayout container;
    @BindView(R.id.img_avtaar) CircleImageView imgAvtaar;
    @BindView(R.id.img_camera) ImageView imgCamera;
    @BindView(R.id.et_myaccount_fname) EditText etMyaccountFname;
    @BindView(R.id.et_myaccount_lname) EditText etMyaccountLname;
    @BindView(R.id.et_myaccount_address) EditText etMyaccountAddress;
    @BindView(R.id.et_my_email) EditText etMyEmail;
    @BindView(R.id.expanded_image) ImageView expandedImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private final int REQUEST_CODE_GALLERY = 1;
    private final int REQUEST_CODE_CAMERA = 2;
    private AlertDialog builder;


    Typeface custom_font, custom_font2, custom_font3, custom_font4;
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;
    private Bitmap photo=null;

*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

/*

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");
*/


//        tvTitle.setText("My Account");

    }
   /* public void back(View view) {
        this.finish();
    }
    @OnClick(R.id.btn_myaccount_save) void onBtnMyaccountSaveClick() {
        //TODO implement
        Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);
        finish();
    }

    @OnLongClick(R.id.btn_myaccount_save) boolean onBtnMyaccountSaveLongClick() {
        //TODO implement
        return true;
    }

    public void clickCallback(View v) {
        switch (v.getId()) {
            case R.id.img_camera:
//                PopupMenu mMenu = new PopupMenu(this, imgCamera);
//                mMenu.getMenuInflater().inflate(R.menu.popup, mMenu.getMenu());
//                mMenu.setOnMenuItemClickListener(this);
//                mMenu.show();

                break;
            case R.id.btn_save:

                break;
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.menu_gallery:
                i = new Intent(Intent.ACTION_PICK);
                i.setType("image*//*");
                startActivityForResult(i, REQUEST_CODE_GALLERY);
                break;
            case R.id.menu_camera:

                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CODE_CAMERA);
                Toast.makeText(getApplicationContext(), "Camera", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            setImageData(data);

        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            if (data != null) {
                photo = (Bitmap) data.getExtras().get("data");
                imgAvtaar.setImageBitmap(photo);
            }
        }
    }

    private void setImageData(Intent data) {

        try {
            InputStream inputStream = MyAccountActivity.this.getContentResolver().openInputStream(data.getData());
            Bitmap mImage = BitmapFactory.decodeStream(inputStream);
            photo=mImage;
            imgAvtaar.setImageBitmap(mImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        //     expandedImageView.setImageResource(imageResId);
        expandedImageView.setImageBitmap(imageResId);
        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    public void zoomImage(View view) {
        //  Toast.makeText(this, "clciked..!!", Toast.LENGTH_SHORT).show();
        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        if (photo!=null)
            zoomImageFromThumb(imgAvtaar,photo);
    }*/

}
