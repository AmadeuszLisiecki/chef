package disaster.master.chef2;

public class Predictor {

    private String name;
    private double glass;
    private double spool;
    private double spoolSmall;
    private double photoId;
    private String measure;

    public Predictor(String name, double glass, double spool, double spoolSmall, double photoId, String measure) {
        this.name = name;
        this.glass = glass;
        this.spool = spool;
        this.spoolSmall = spoolSmall;
        this.photoId = photoId;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public double getGlass() {
        return glass;
    }

    public double getSpool() {
        return spool;
    }

    public double getSpoolSmall() {
        return spoolSmall;
    }

    public double getPhotoId() {
        return photoId;
    }

    public String getMeasure() {
        return measure;
    }

}

