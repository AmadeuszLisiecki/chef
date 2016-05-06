package disaster.master.chef2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_details);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        TextView dishName = (TextView) findViewById(R.id.text_details);
        Intent intent = getIntent();
        String dishText = intent.getStringExtra("dish name");
        dishName.setText(dishText + " - informacje");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_details);
        navigationView.setNavigationItemSelectedListener(this);
        TextView description = (TextView) findViewById(R.id.description);
        TextView protein = (TextView) findViewById(R.id.protein);
        TextView fat = (TextView) findViewById(R.id.fat);
        TextView sugar = (TextView) findViewById(R.id.sugar);
        TextView portions = (TextView) findViewById(R.id.portions);
        TextView weight = (TextView) findViewById(R.id.weight1portion);
        TextView difficulty = (TextView) findViewById(R.id.difficulty);
        TextView caloric = (TextView) findViewById(R.id.caloric);
        TextView time = (TextView) findViewById(R.id.time_prepare);
        description.setText(Base.getRecepture(dishText).getDetails().getDescription());
        protein.setText("Zawartość białka: " + Base.getRecepture(dishText).getDetails().getProtein() + " g");
        fat.setText("Zawartość tłuszczu: " + Base.getRecepture(dishText).getDetails().getFat() + " g");
        sugar.setText("Zawartość węglowodanów " + Base.getRecepture(dishText).getDetails().getSugar() + " g");
        difficulty.setText("Stopień trudności: " + Base.getRecepture(dishText).getDetails().getDifficulty());
        time.setText("Czas przygotowania: " + Base.getRecepture(dishText).getDetails().getTimePrepare() + " min");
        portions.setText("Liczba porcji: " + Base.getRecepture(dishText).getDetails().getPortions());
        weight.setText("Waga jednej porcji: " + Base.getRecepture(dishText).getDetails().getWeight1portion() + " g");
        caloric.setText("Ilość kalorii: " + Base.getRecepture(dishText).getDetails().getCaloric() + " kcal");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_details);
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
            Intent mainScreen = new Intent(DetailsActivity.this, MainActivity.class);
            DetailsActivity.this.startActivity(mainScreen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent swapScreen = null;
        if (id == R.id.random) {
            swapScreen = new Intent(DetailsActivity.this, RandomActivity.class);
        } else if (id == R.id.predictors) {
            swapScreen = new Intent(DetailsActivity.this, PredictorActivity.class);
        } else if (id == R.id.about_app) {
            swapScreen = new Intent(DetailsActivity.this, AboutAppActivity.class);
        }
        DetailsActivity.this.startActivity(swapScreen);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_details);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
