package disaster.master.chef2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class MultimediaAdapter extends ArrayAdapter<Multimedia> {

    private ArrayList<Multimedia> multimedia;

    public MultimediaAdapter(Context context,
                             ArrayList<Multimedia> multimedia) {
        super(context, 0, multimedia);
        this.multimedia = multimedia;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Multimedia currentMultimedia = multimedia.get(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.multimedia, parent, false);
        ImageView photo = (ImageView) convertView.findViewById(R.id.multimedia_photo);
        if (currentMultimedia.getType().equals("Zdjęcie")) {
            photo.setImageBitmap(currentMultimedia.getPhotoBitmap());
        }
        else {
            photo.setImageResource(R.drawable.video);
        }
        return convertView;
    }
}
