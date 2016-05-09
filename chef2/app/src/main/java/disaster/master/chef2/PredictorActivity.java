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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class PredictorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if(size.x == 1440) {
            setContentView(R.layout.activity_predictor1440);
        }
        else {
            setContentView(R.layout.activity_predictor);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_predictor);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_predictor);
        navigationView.setNavigationItemSelectedListener(this);
        final Spinner predictors = (Spinner) findViewById(R.id.spinner_predictor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.predictors, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        predictors.setAdapter(adapter);
        final TextView glass = (TextView)findViewById(R.id.glass_measure);
        final TextView spoon = (TextView)findViewById(R.id.spoon_measure);
        final TextView smallSpoon = (TextView)findViewById(R.id.small_spoon_measure);
        final ImageView photo = (ImageView)findViewById(R.id.predictor_photo);
        predictors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Predictor ingredient = Base.getPredictors().get(pos);
                double[] measures = Base.getPredictorsForIngedient(ingredient.getName());
                glass.setText((int) measures[0] + " " + ingredient.getMeasure());
                if(measures[1] == Math.floor(measures[1])) {
                    spoon.setText((int)measures[1] + " " + ingredient.getMeasure());
                }
                else {
                    spoon.setText(measures[1] + " " + ingredient.getMeasure());
                }
                if(measures[2] == Math.floor(measures[2])) {
                    smallSpoon.setText((int)measures[2] + " " + ingredient.getMeasure());
                }
                else {
                    smallSpoon.setText(measures[2] + " " + ingredient.getMeasure());
                }
                photo.setBackgroundResource((int) measures[3]);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_predictor);
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
            Intent mainScreen = new Intent(PredictorActivity.this, MainActivity.class);
            PredictorActivity.this.startActivity(mainScreen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.random) {
            Intent swapScreen = new Intent(PredictorActivity.this, RandomActivity.class);
            PredictorActivity.this.startActivity(swapScreen);
        } else if (id == R.id.about_app) {
            Intent swapScreen = new Intent(PredictorActivity.this, AboutAppActivity.class);
            PredictorActivity.this.startActivity(swapScreen);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_predictor);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
