package in.chandramouligoru.tictactoe.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import in.chandramouligoru.tictactoe.R;
import in.chandramouligoru.tictactoe.model.Piece;

/**
 * Created by chandramouligoru on 1/20/16.
 */
public class GridAdapter extends ArrayAdapter<Piece> {

    private LayoutInflater mInflater;
    private List<Piece> board;

    public GridAdapter(Context context, int resource, List<Piece> objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
        board = objects;
    }

    public int getCount() {
        return board.size();
    }

    public Piece getItem(int position) {
        return board.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public List<Piece> getData() {
        return board;
    }

    public void setData(List<Piece> moves) {
        this.clear();
        board.clear();
        board.addAll(moves);
        this.notifyDataSetChanged();
    }

    public void updateEntry(int position, Piece piece) {
        board.set(position, piece);
        this.notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Bind the data efficiently with the holder.
        Piece move = board.get(position);
        int color = move.getColor();

        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.grid_cell_layout, parent, false);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        switch (color) {
            case 0://EMPTY
                holder.image.setBackgroundColor(Color.WHITE);
                break;

            case 1://RED
                holder.image.setBackgroundColor(Color.RED);
                break;

            case 2://BLUE
                holder.image.setBackgroundColor(Color.BLUE);
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView image;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
