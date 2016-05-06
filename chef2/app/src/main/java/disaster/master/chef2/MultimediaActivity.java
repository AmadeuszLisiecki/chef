package disaster.master.chef2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MultimediaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Multimedia> multimedia;
    private Call<ResponseBody> result;
    private Get service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_multimedia);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        TextView dishName = (TextView) findViewById(R.id.text_multimedia);
        Intent intent = getIntent();
        final String dishText = intent.getStringExtra("dish name");
        dishName.setText(dishText + " - multimedia");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_multimedia);
        navigationView.setNavigationItemSelectedListener(this);
        multimedia = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chef.cba.pl")
                .build();
        service = retrofit.create(Get.class);
        Toast.makeText(getApplicationContext(), "Poczekaj na pobranie zawartości!", Toast.LENGTH_LONG).show();
        switch (dishText) {
            case "Muszle z łososiem": {
                result = service.getWideoForSalmoNudle();
                getVideoAndPhoto(dishText);
                return;
            }
            case "Kokosowa potrawka z ananasem": {
                result = service.getPhotosCoconutStewWithPineapple();
                getPhotos();
                return;
            }
            case "Słodkie pampuchy": {
                result = service.getWideoForSweetPampuchy();
                getVideoAndPhoto(dishText);
                return;
            }
            case "Zupa pomidorowa": {
                result = service.getWideoForTomatoSoup();
                getVideoAndPhoto(dishText);
                return;
            }
            case "Morszczuk zapiekany": {
                result = service.getWideoForBakedHake();
                getVideoAndPhoto(dishText);
                return;
            }
            case "Placki gryczane": {
                result = service.getPhotosBakedHake();
                getPhotos();
                return;
            }
            case "Rurki z kremem": {
                result = service.getWideoForTubesCream();
                getVideoAndPhoto(dishText);
                return;
            }
            case "Sałatka z kiełkami": {
                result = service.getPhotosSaladWithSprouts();
                getPhotos();
            }
        }
    }

    private void getPhotos() {
        if (result != null) {
            result.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseString = response.body().string();
                        extractMultimedia(responseString, "Zdjęcia");
                        setAdapter();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Błąd wejścia wyjścia", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nie udało się pobrać", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_multimedia);
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
            Intent mainScreen = new Intent(MultimediaActivity.this, MainActivity.class);
            MultimediaActivity.this.startActivity(mainScreen);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent swapScreen = null;
        if (id == R.id.random) {
            swapScreen = new Intent(MultimediaActivity.this, RandomActivity.class);
        } else if (id == R.id.predictors) {
            swapScreen = new Intent(MultimediaActivity.this, PredictorActivity.class);
        } else if (id == R.id.about_app) {
            swapScreen = new Intent(MultimediaActivity.this, AboutAppActivity.class);
        }
        MultimediaActivity.this.startActivity(swapScreen);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_multimedia);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void extractMultimedia(String responseString, String mode) {
        try {
            JSONObject jsonRootObject = new JSONObject(responseString);
            JSONArray jsonArray;
            if(mode.equals("Wideo")) {
                jsonArray = jsonRootObject.optJSONArray("Wideo");
            }
            else {
                jsonArray = jsonRootObject.optJSONArray("ZdjeciaPotraw");
            }
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                if(mode.equals("Wideo")) {
                    String url = jsonObject.optString("url");
                    multimedia.add(new Video(url));
                }
                else {
                    String photoString = jsonObject.optString("zdjecie");
                    String photoBigString = jsonObject.optString("zdjecieDuze");
                    byte[] decodedString = Base64.decode(photoString, Base64.NO_WRAP);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    byte[] decodedBigString = Base64.decode(photoBigString, Base64.NO_WRAP);
                    Bitmap decodedByteBig = BitmapFactory.decodeByteArray(decodedBigString, 0, decodedBigString.length);
                    multimedia.add(new Photo(decodedByte, decodedByteBig));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        MultimediaAdapter adapter = new MultimediaAdapter(this, multimedia);
        ListView listMultimedia = (ListView)findViewById(R.id.list_multimedia);
        listMultimedia.setAdapter(adapter);
        listMultimedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Multimedia clicked = multimedia.get(position);
                if(clicked.getType().equals("Wideo")) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(clicked.getReference()));
                    startActivity(i);
                }
                else {
                    Intent swapScreen = new Intent(MultimediaActivity.this, PhotoActivity.class);
                    swapScreen.putExtra("position", position);
                    Base.addBitmaps(multimedia);
                    MultimediaActivity.this.startActivity(swapScreen);
                }
            }

        });
    }

    private void getVideoAndPhoto(final String receptureName) {
        if (result != null) {
            result.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseString = response.body().string();
                        extractMultimedia(responseString, "Wideo");
                        result = getResult(receptureName, "Zdjęcia");
                        result.enqueue(new Callback<ResponseBody>() {

                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String responseString = response.body().string();
                                    extractMultimedia(responseString, "Zdjęcia");
                                    setAdapter();
                                } catch (IOException e) {
                                    Toast.makeText(getApplicationContext(), "Błąd wejścia wyjścia", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Nie udało się pobrać", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Błąd wejścia wyjścia", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nie udało się pobrać", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private Call<ResponseBody> getResult(String receptureName, String mode) {
        switch (receptureName) {
            case "Muszle z łososiem": {
                return mode.equals("Wideo") ? service.getWideoForSalmoNudle() : service.getPhotosForSalmoNudle();
            }
            case "Kokosowa potrawka z ananasem": {
                return service.getPhotosCoconutStewWithPineapple();
            }
            case "Słodkie pampuchy": {
                return mode.equals("Wideo") ? service.getWideoForSweetPampuchy() : service.getPhotosSweetPampuchy();
            }
            case "Zupa pomidorowa": {
                return mode.equals("Wideo") ? service.getWideoForTomatoSoup() : service.getPhotosTomatoSoup();
            }
            case "Morszczuk zapiekany": {
                return mode.equals("Wideo") ? service.getWideoForBakedHake() : service.getPhotosBakedHake();
            }
            case "Placki gryczane": {
                return service.getPhotosCocoaBuckwheatPancakes();
            }
            case "Rurki z kremem": {
                return mode.equals("Wideo") ? service.getWideoForTubesCream() : service.getPhotosTubesCream();
            }
            case "Sałatka z kiełkami": {
                return service.getPhotosSaladWithSprouts();
            }
            default: {
                return null;
            }
        }
    }
}
