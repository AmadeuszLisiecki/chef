package disaster.master.chef2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewFlipper swapSteps;
    private float lastX;
    private RadioButton[] dots;
    private TextView stepText;
    private TextView dishName;
    private ImageView step1image;
    private ImageView step2image;
    private ImageView step3image;
    private ImageView step4image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recipe);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        dishName = (TextView) findViewById(R.id.text_recipe);
        Intent intent = getIntent();
        final String dishText = intent.getStringExtra("dish name");
        dishName.setText(dishText + " - krok 1");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_recipe);
        navigationView.setNavigationItemSelectedListener(this);
        swapSteps = (ViewFlipper)findViewById(R.id.steps);
        final String[] steps = Base.getStepsForRecepture(dishText);
        step1image = (ImageView)findViewById(R.id.step1image);
        step2image = (ImageView)findViewById(R.id.step2image);
        step3image = (ImageView)findViewById(R.id.step3image);
        step4image = (ImageView)findViewById(R.id.step4image);
        stepText = (TextView)findViewById(R.id.step_text);
        stepText.setMovementMethod(new ScrollingMovementMethod());
        if(steps != null) {
            step1image.setImageResource(R.drawable.no_internet);
            step2image.setImageResource(R.drawable.no_internet);
            step3image.setImageResource(R.drawable.no_internet);
            step4image.setImageResource(R.drawable.no_internet);
            stepText.setText(steps[0]);
        }
        dots = new RadioButton[4];
        dots[0] = (RadioButton)findViewById(R.id.dot1);
        dots[1] = (RadioButton)findViewById(R.id.dot2);
        dots[2] = (RadioButton)findViewById(R.id.dot3);
        dots[3] = (RadioButton)findViewById(R.id.dot4);
        swapSteps.setDisplayedChild(0);
        swapSteps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent touchevent) {
                switch (touchevent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = touchevent.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float currentX = touchevent.getX();
                        if (lastX < currentX) {
                            if (swapSteps.getDisplayedChild() == 0)
                                break;
                            swapSteps.setInAnimation(getApplication(), R.anim.slide_in_from_left);
                            swapSteps.setOutAnimation(getApplication(), R.anim.slide_out_to_right);
                            swapSteps.showNext();
                        }
                        if (lastX > currentX) {
                            if (swapSteps.getDisplayedChild() == 1)
                                break;
                            swapSteps.setInAnimation(getApplication(), R.anim.slide_in_from_right);
                            swapSteps.setOutAnimation(getApplication(), R.anim.slide_out_to_left);
                            swapSteps.showPrevious();
                        }
                        swapSteps.getOutAnimation().setAnimationListener(new Animation.AnimationListener() {

                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                switch(swapSteps.getDisplayedChild()) {
                                    case 0:
                                        dots[1].setChecked(false);
                                        dots[0].setChecked(true);
                                        dishName.setText(dishText + " - krok 1");
                                        if(steps != null) {
                                            stepText.setText(steps[0]);
                                        }
                                        return;
                                    case 3:
                                        (dots[0].isChecked() ? dots[0]: dots[2]).setChecked(false);
                                        dots[1].setChecked(true);
                                        dishName.setText(dishText + " - krok 2");
                                        if(steps != null) {
                                            stepText.setText(steps[1]);
                                        }
                                        return;
                                    case 2:
                                        (dots[0].isChecked() ? dots[0]: dots[2]).setChecked(false);
                                        dots[2].setChecked(true);
                                        dishName.setText(dishText + " - krok 3");
                                        if(steps != null) {
                                            stepText.setText(steps[2]);
                                        }
                                        return;
                                    default:
                                        dots[2].setChecked(false);
                                        dots[3].setChecked(true);
                                        dishName.setText(dishText + " - krok 4");
                                        if(steps != null) {
                                            stepText.setText(steps[3]);
                                        }
                                }
                            }
                        });
                }
                return true;
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chef.cba.pl")
                .build();
        Get service = retrofit.create(Get.class);
        Call<ResponseBody> result = null;
        switch(dishText) {
            case "Muszle z łososiem": {
                result = service.getStepsPhotosForSalmoNudle();
                break;
            }
            case "Kokosowa potrawka z ananasem": {
                result = service.getStepsPhotosCoconutStewWithPineapple();
                break;
            }
            case "Słodkie pampuchy": {
                result = service.getStepsPhotosSweetPampuchy();
                break;
            }
            case "Zupa pomidorowa": {
                result = service.getStepsPhotosForTomatoSoup();
                break;
            }
            case "Morszczuk zapiekany": {
                result = service.getStepsPhotosForBakedHake();
                break;
            }
            case "Placki gryczane": {
                result = service.getStepsPhotosForCocoaBuckwheatPancakes();
                break;
            }
            case "Rurki z kremem": {
                result = service.getStepsPhotosForTubesCream();
                break;
            }
            case "Sałatka z kiełkami": {
                result = service.getStepsPhotosForSaladWithSprouts();
                break;
            }
        }
        if (result != null) {
            result.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseString = response.body().string();
                        dispalyImages(responseString);
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recipe);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            Intent mainScreen = new Intent(RecipeActivity.this, MainActivity.class);
            RecipeActivity.this.startActivity(mainScreen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent swapScreen = null;
        if (id == R.id.random) {
            swapScreen = new Intent(RecipeActivity.this, RandomActivity.class);
        } else if (id == R.id.predictors) {
            swapScreen = new Intent(RecipeActivity.this, PredictorActivity.class);
        } else if (id == R.id.about_app) {
            swapScreen = new Intent(RecipeActivity.this, AboutAppActivity.class);
        }
        RecipeActivity.this.startActivity(swapScreen);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recipe);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void dispalyImages(String responseString) {
        try {
            JSONObject  jsonRootObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonRootObject.optJSONArray("ZdjeciaEtapow");
            int stepNr;
            String photoString;
            JSONObject jsonObject;
            byte[] decodedString;
            Bitmap decodedByte;
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                stepNr = Integer.parseInt(jsonObject.optString("nrEtapu"));
                photoString = jsonObject.optString("zdjecie");
                if(stepNr != -1) {
                    decodedString = Base64.decode(photoString, Base64.DEFAULT);
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    switch(stepNr) {
                        case 1:
                            step1image.setImageBitmap(decodedByte);
                            break;
                        case 2:
                            step4image.setImageBitmap(decodedByte);
                            break;
                        case 3:
                            step3image.setImageBitmap(decodedByte);
                            break;
                        case 4:
                            step2image.setImageBitmap(decodedByte);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
