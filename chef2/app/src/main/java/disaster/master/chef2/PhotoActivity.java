package disaster.master.chef2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class PhotoActivity extends Activity {

    private double lastX;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        final ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.big_photo_flipper);
        Intent intent = getIntent();
        int recivedPosition = intent.getIntExtra("position", 0);
        int videosNumber = Base.getVideoCunter();
        while(videosNumber != 0) {
            recivedPosition--;
            videosNumber--;
        }
        position = recivedPosition;
        ArrayList<View> images = new ArrayList<>();
        final ArrayList<Bitmap> photos = Base.getBitmaps();
        int left = 1;
        int right = photos.size() - 1;
        while(left < right) {
            if(left == position) {
                position = right;
            }
            else if(right == position) {
                position = left;
            }
            Bitmap tmp = photos.get(left);
            photos.set(left, photos.get(right));
            photos.set(right, tmp);
            left++;
            right--;
        }
        for(int i = 0; i < photos.size(); i++) {
            images.add(new ImageView(getApplicationContext()));
            ((ImageView) images.get(i)).setImageBitmap(photos.get(i));
            viewFlipper.addView(images.get(i));
        }
        viewFlipper.setDisplayedChild(position);
        Toast.makeText(getApplicationContext(), (recivedPosition + 1) + " / " + (photos.size()), Toast.LENGTH_SHORT).show();
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent touchevent) {
                switch (touchevent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = touchevent.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float currentX = touchevent.getX();
                        // Handling left to right screen swap.
                        if (lastX < currentX) {
                            // If there aren't any other children, just break.
                            if (viewFlipper.getDisplayedChild() == 0)
                                break;
                            // Next screen comes in from left.
                            viewFlipper.setInAnimation(getApplication(), R.anim.slide_in_from_left);

                            // Current screen goes out from right.
                            viewFlipper.setOutAnimation(getApplication(), R.anim.slide_out_to_right);

                            // Display next screen.
                            viewFlipper.showNext();
                        }
                        // Handling right to left screen swap.
                        if (lastX > currentX) {
                            // If there is a child (to the left), kust break.
                            if (viewFlipper.getDisplayedChild() == 1)
                                break;
                            // Next screen comes in from right.
                            viewFlipper.setInAnimation(getApplication(), R.anim.slide_in_from_right);
                            // Current screen goes out from left.
                            viewFlipper.setOutAnimation(getApplication(), R.anim.slide_out_to_left);
                            // Display previous screen.
                            viewFlipper.showPrevious();
                        }
                        viewFlipper.getOutAnimation().setAnimationListener(new Animation.AnimationListener() {
                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                if(viewFlipper.getDisplayedChild() == 0) {
                                    position = 0;
                                }
                                else {
                                    for(int i = 1; i < photos.size(); i++) {
                                        if(i == viewFlipper.getDisplayedChild()) {
                                            position = photos.size() - i;
                                        }
                                    }
                                }
                                Toast.makeText(getApplicationContext(), (position + 1) + " / " + photos.size(), Toast.LENGTH_LONG).show();
                            }
                        });
                }
                return true;
            }
        });

    }

}

