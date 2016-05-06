package disaster.master.chef2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String dishText;
    private TextView dishName;
    private RadioButton dotIngredients;
    private RadioButton dotSubstituties;
    private RadioButton dotCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if(size.x == 1440) {
            setContentView(R.layout.activity_ingredients1440);
        }
        else {
            setContentView(R.layout.activity_ingredients);
        }
        setContentView(R.layout.activity_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ingredients);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        dishName = (TextView) findViewById(R.id.text_ingredients);
        Intent intent = getIntent();
        dishText = intent.getStringExtra("dish name");
        dishName.setText(dishText + " - składniki");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_ingredients);
        navigationView.setNavigationItemSelectedListener(this);
        setAdapter(Base.getIngredientsForRecepture(dishText), "Składniki");
        dotIngredients = (RadioButton)findViewById(R.id.dot_ingredients);
        dotSubstituties = (RadioButton)findViewById(R.id.dot_substitutes);
        dotCost = (RadioButton)findViewById(R.id.dot_cost);
        dotIngredients.setChecked(true);
    }

    private void setAdapter(ArrayList<Ingredient> toView, String mode) {
        IngredientAdapter adapter = new IngredientAdapter(this, R.layout.ingredient, toView, mode);
        ListView listIngredients = (ListView)findViewById(R.id.list_ingredients);
        listIngredients.setScrollingCacheEnabled(false);
        listIngredients.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ingredients);
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
            Intent mainScreen = new Intent(IngredientsActivity.this, MainActivity.class);
            IngredientsActivity.this.startActivity(mainScreen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent swapScreen = null;
        if (id == R.id.random) {
            swapScreen = new Intent(IngredientsActivity.this, RandomActivity.class);
        } else if (id == R.id.predictors) {
            swapScreen = new Intent(IngredientsActivity.this, PredictorActivity.class);
        } else if (id == R.id.about_app) {
            swapScreen = new Intent(IngredientsActivity.this, AboutAppActivity.class);
        }
        IngredientsActivity.this.startActivity(swapScreen);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ingredients);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeContent(View clicked) {
        if(clicked.equals(dotIngredients)) {
            dotIngredients.setChecked(true);
            if(dotCost.isChecked()) {
                dotCost.setChecked(false);
            }
            if(dotSubstituties.isChecked()) {
                dotSubstituties.setChecked(false);
            }
            dishName.setText(dishText + " - składniki");
            setAdapter(Base.getIngredientsForRecepture("Muszle z łososiem"), "Składniki");
        }
        else if(clicked.equals(dotSubstituties)) {
            dotSubstituties.setChecked(true);
            if(dotCost.isChecked()) {
                dotCost.setChecked(false);
            }
            if(dotIngredients.isChecked()) {
                dotIngredients.setChecked(false);
            }
            dishName.setText(dishText + " - zamienniki");
            setAdapter(Base.getIngredientsForRecepture(dishText), "Zamienniki");
        }
        else {
            dishName.setText(dishText + " - koszt");
            (dotIngredients.isChecked() ? dotIngredients : dotSubstituties).setChecked(false);
            setAdapter(Base.getIngredientsForRecepture(dishText), "Koszt");
            new AlertDialog.Builder(this)
                    .setMessage("Szacowana cena potrawy to: " + Base.getPriceForRecepture(dishText) + " zł.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

}