package disaster.master.chef2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private int layoutResourceId;
    private ArrayList<Ingredient> ingredients;
    private String mode;
    private LayoutInflater inflater;

    public IngredientAdapter(Context context, int layoutResourceId, ArrayList<Ingredient> ingredients, String mode) {
        super(context, layoutResourceId, ingredients);
        this.layoutResourceId = layoutResourceId;
        this.ingredients = ingredients;
        this.mode = mode;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder;
        if (convertView == null) {
            view = inflater.inflate(layoutResourceId, parent, false);
            holder = new Holder();
            holder.txtTitle = (TextView) view.findViewById(R.id.ingredient_text);
            view.setTag(holder);
        }
        else {
            holder = (Holder) convertView.getTag();
        }

        Ingredient object = ingredients.get(position);
        switch(mode) {
            case "Składniki": holder.txtTitle.setText(object.toString());
                break;
            case "Zamienniki": holder.txtTitle.setText(object.showSubstituties());
                break;
            default: holder.txtTitle.setText(object.showPrice());
        }
        return view;
    }

    class Holder {
        TextView txtTitle;
    }

}

