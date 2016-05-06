package disaster.master.chef2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartScreen extends Activity{

    private String[] descriptions;
    private int[] photos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        if(width == 1440) {
            setContentView(R.layout.start_screen1440);
        }
        else {
            setContentView(R.layout.start_screen);
        }
        descriptions = new String[10];
        descriptions[0] = "Wszelkiego rodzaju kasze należy wypiekać przykryte, w kamiennym naczyniu, na ruszcie w piecu, aby się dobrze wyprażyły, a nie przypaliły.";
        descriptions[1] = "Piec gorący musi być bardzo rozpalony, aby wstawiona potrawa od razu się z wierzchu obrumieniła.";
        descriptions[2] = "Ciasta na naleśniki nie robić z czystego mleka, lecz pół na pół z wodą: są wtedy kruchsze i niełykowate.";
        descriptions[3] = "Soda chemicznie oczyszczona jest niezbędna w każdej kuchni. Dodana na koniec noża nie zmienia smaku, a przyspiesza gotowanie twardych rzeczy.";
        descriptions[4] = "Mięso w zupie będzie miękke, jeżeli wrzucimy je do gotowania rozmrożone.";
        descriptions[5] = "Żeby masło sie na patelni nie przypaliło, należy dodać trochę oleju.";
        descriptions[6] = "Panierka (jajko+bułka tarta) nie odpada z mięsa/ryby, jak mięso jest osuszone ręcznikiem papierowym przed wlożeniem go do jajka.";
        descriptions[7] = "Ziemniaki w kwaśnych zupach (ogórkowej, szczawiowej) gotuje się zanim doda się ów kwaśny element. Dopiero jak ziemniaki są miękkie, dolewa się przecier.";
        descriptions[8] = "Kopytka lepiej się udają z ziemniaków ugotowanych poprzedniego dnia, takie całkiem wystygłe ziemniaki tworzą dużo lepsze, zwarte i elastyczne ciasto.";
        descriptions[9] = "Naleśniki nigdy nie przywrą do patelni jeśli rozgrzejemy ją bardzo, bardzo mocno i potrzemy dość obficie kawałkiem słoniny.";
        photos = new int[10];
        photos[0] = R.drawable.grifts;
        photos[1] = R.drawable.hot_oven;
        photos[2] = R.drawable.pancake_batter;
        photos[3] = R.drawable.backing_soda;
        photos[4] = R.drawable.meat_soup;
        photos[5] = R.drawable.hot_butter;
        photos[6] = R.drawable.coating;
        photos[7] = R.drawable.potatoes;
        photos[8] = R.drawable.last;
        photos[9] = R.drawable.pancake;
        setRandomTip();
    }

    public void gotoMainMenu(View button) {
        Intent swapScreen = new Intent(StartScreen.this, MainActivity.class);
        StartScreen.this.startActivity(swapScreen);
    }

    public void setRandomTip() {
        int randomTipIndex = (int)(Math.random() * 11) - 1;
        if(randomTipIndex != -1) {
            ImageView tipPhoto = (ImageView)findViewById(R.id.tip_photo);
            TextView tipText = (TextView)findViewById(R.id.tip_text);
            tipPhoto.setBackgroundResource(photos[randomTipIndex]);
            tipText.setText(descriptions[randomTipIndex]);
        }
    }

}
