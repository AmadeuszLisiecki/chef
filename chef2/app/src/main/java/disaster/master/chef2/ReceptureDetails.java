package disaster.master.chef2;

public class ReceptureDetails {

    private String description;
    private int timePrepare;
    private int protein;
    private int fat;
    private int sugar;
    private String difficulty;
    private int weight1portion;
    private int caloric;
    private int portions;
    private String timeDay;

    public ReceptureDetails(String description, int timePrepare, int protein, int fat, int sugar, String difficulty, int weight1portion,
                            int caloric, int portions,  String timeDay) {
        this.description = description;
        this.timePrepare = timePrepare;
        this.protein = protein;
        this.fat = fat;
        this.sugar = sugar;
        this.difficulty = difficulty;
        this.weight1portion = weight1portion;
        this.caloric = caloric;
        this.portions = portions;
        this.timeDay = timeDay;
    }

    public String getDescription() {
        return description;
    }

    public int getTimePrepare() {
        return timePrepare;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getSugar() {
        return sugar;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getWeight1portion() {
        return weight1portion;
    }

    public int getCaloric() {
        return caloric;
    }

    public int getPortions() {
        return portions;
    }

    public String getTimeDay() {
        return timeDay;
    }

}
