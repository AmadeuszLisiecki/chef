package disaster.master.chef2;

public class Row {

    private int pictureID;
    private String description;
    private Row parent;
    private String type;

    public Row(int pictureID, String description, Row parent, String type) {
        this.pictureID = pictureID;
        this.description = description;
        this.parent = parent;
        this.type = type;
    }

    public int getPictureID() {
        return pictureID;
    }

    public String getDescription() {
        return description;
    }

    public Row getParent() {
        return parent;
    }

    public String getType() {
        return type;
    }

}
