package disaster.master.chef2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RowAdapter extends ArrayAdapter<Row> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Row> rows = null;

    public RowAdapter(Context context, int layoutResourceId, ArrayList<Row> rows) {
        super(context, layoutResourceId, rows);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.rows = rows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RowBeanHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.category_image);
            holder.txtTitle = (TextView)row.findViewById(R.id.category_text);

            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }

        Row object = rows.get(position);
        holder.txtTitle.setText(object.getDescription());
        holder.imgIcon.setImageResource(object.getPictureID());

        return row;
    }

    static class RowBeanHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

}
