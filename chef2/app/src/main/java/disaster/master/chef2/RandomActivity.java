package disaster.master.chef2;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class RandomActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String timeDay;
    private Button goToRecepture;
    private TextView nameRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if(size.x == 1440) {
            setContentView(R.layout.activity_random1440);
        }
        else {
            setContentView(R.layout.activity_random);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_random);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_random);
        navigationView.setNavigationItemSelectedListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.time_day);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_of_day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        goToRecepture = (Button)findViewById(R.id.go_to_receptue);
        goToRecepture.setVisibility(View.INVISIBLE);
        timeDay = "Śniadanie";
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0: {
                        timeDay = "Śniadanie";
                        return;
                    }
                    case 1: {
                        timeDay = "Obiad";
                        return;
                    }
                    case 2: {
                        timeDay = "Kolacja";
                        return;
                    }
                    case 3: {
                        timeDay = "Deser";
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_random);
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
            Intent mainScreen = new Intent(RandomActivity.this, MainActivity.class);
            RandomActivity.this.startActivity(mainScreen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about_app) {
            Intent swapScreen = new Intent(RandomActivity.this, AboutAppActivity.class);
            RandomActivity.this.startActivity(swapScreen);
        } else if (id == R.id.predictors) {
            Intent swapScreen = new Intent(RandomActivity.this, PredictorActivity.class);
            RandomActivity.this.startActivity(swapScreen);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_random);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setRandomRecepture(View button) {
        nameRandom = (TextView)findViewById(R.id.dish_name_random);
        ReceptureInBase randomRecepture = Base.getRandomRecepture(timeDay);
        ImageView receptureImage = (ImageView)findViewById(R.id.random_recepture_photo);
        goToRecepture = (Button)findViewById(R.id.go_to_receptue);
        if(randomRecepture != null) {
            nameRandom.setText(randomRecepture.getName());
            receptureImage.setVisibility(View.VISIBLE);
            receptureImage.setImageResource(randomRecepture.getMainPhoto());
            if(goToRecepture.getVisibility() == View.INVISIBLE) {
                goToRecepture.setVisibility(View.VISIBLE);
            }
        }
        else {
            goToRecepture.setVisibility(View.INVISIBLE);
            nameRandom.setText(R.string.empty_base);
            receptureImage.setVisibility(View.INVISIBLE);
        }
    }

    public void goToRecepture(View button) {
        Intent swapScreen = new Intent(RandomActivity.this, DishActivity.class);
        swapScreen.putExtra("dish name", nameRandom.getText().toString());
        RandomActivity.this.startActivity(swapScreen);
    }

}

