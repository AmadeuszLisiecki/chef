package disaster.master.chef2;

import android.graphics.Bitmap;

public class Photo extends Multimedia {

    private Bitmap photoBitmap;
    private Bitmap photoBigBitmap;

    public Photo(Bitmap photoBitmap, Bitmap photoBigBitmap) {
        super("Zdjęcie");
        this.photoBitmap = photoBitmap;
        this.photoBigBitmap = photoBigBitmap;
    }

    @Override
    public String getReference() {
        return null;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public Bitmap getPhotoBigBitmap() {
        return photoBigBitmap;
    }

}

