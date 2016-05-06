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
import android.widget.TextView;
import android.widget.Toast;

public class DishActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView dishName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if(size.x == 1440) {
            setContentView(R.layout.activity_dish1440);
        }
        else {
            setContentView(R.layout.activity_dish);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dish);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_dish);
        navigationView.setNavigationItemSelectedListener(this);
        dishName = (TextView)findViewById(R.id.text_dish);
        Intent intent = getIntent();
        dishName.setText(intent.getStringExtra("dish name"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dish);
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
            Intent mainScreen = new Intent(DishActivity.this, MainActivity.class);
            DishActivity.this.startActivity(mainScreen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent swapScreen = null;
        if (id == R.id.random) {
            swapScreen = new Intent(DishActivity.this, RandomActivity.class);
        } else if (id == R.id.predictors) {
            swapScreen = new Intent(DishActivity.this, PredictorActivity.class);
        } else if (id == R.id.about_app) {
            swapScreen = new Intent(DishActivity.this, AboutAppActivity.class);
        }
        DishActivity.this.startActivity(swapScreen);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dish);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openIngedients(View clicked) {
        Intent swapScreen = new Intent(DishActivity.this, IngredientsActivity.class);
        swapScreen.putExtra("dish name", dishName.getText());
        DishActivity.this.startActivity(swapScreen);
    }

    public void openSteps(View clicked) {
        Intent swapScreen = new Intent(DishActivity.this, RecipeActivity.class);
        swapScreen.putExtra("dish name", dishName.getText());
        DishActivity.this.startActivity(swapScreen);
    }

    public void openDetails(View clicked) {
        Intent swapScreen = new Intent(DishActivity.this, DetailsActivity.class);
        swapScreen.putExtra("dish name", dishName.getText());
        DishActivity.this.startActivity(swapScreen);
    }

    public void openMultimedia(View clicked) {
        Intent swapScreen = new Intent(DishActivity.this, MultimediaActivity.class);
        swapScreen.putExtra("dish name", dishName.getText());
        DishActivity.this.startActivity(swapScreen);
    }

}

