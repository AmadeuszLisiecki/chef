package disaster.master.chef2;

import android.graphics.Bitmap;

public class Video extends Multimedia {

    private String reference;

    public Video(String reference) {
        super("Wideo");
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public Bitmap getPhotoBitmap() {
        return null;
    }

    @Override
    public Bitmap getPhotoBigBitmap() {
        return null;
    }

}
