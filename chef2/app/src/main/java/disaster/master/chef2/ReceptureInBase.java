package disaster.master.chef2;

import java.util.ArrayList;

public class ReceptureInBase {

    private String name;
    private int mainPhoto;
    private ArrayList<Ingredient> ingredients;
    private ReceptureDetails details;
    private String[] steps;

    public ReceptureInBase(String name, int mainPhoto, ArrayList<Ingredient> ingredients,
                           ReceptureDetails details, String[] steps) {
        this.name = name;
        this.mainPhoto = mainPhoto;
        this.ingredients = (ingredients == null ? new ArrayList<Ingredient>() : ingredients);
        this.details = details;
        this.steps = (steps == null ? new String[4] : steps);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setSteps(String[] added){
        steps = added;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setDetails(String description, int timePrepare, int protein, int fat, int sugar, String difficulty, int weight1portion, int caloric, int portions, String timeDay) {
        details = new ReceptureDetails(description, timePrepare, protein, fat, sugar, difficulty,weight1portion, caloric, portions, timeDay);
    }

    public ReceptureDetails getDetails() {
        return details;
    }

    public int getMainPhoto() {
        return mainPhoto;
    }

}
