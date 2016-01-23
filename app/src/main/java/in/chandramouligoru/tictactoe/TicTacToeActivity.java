package in.chandramouligoru.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import in.chandramouligoru.tictactoe.adapter.GridAdapter;
import in.chandramouligoru.tictactoe.model.Piece;

public class TicTacToeActivity extends AppCompatActivity {

    private List<Piece> board;
    private GridAdapter mAdapter;

    private boolean player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        GridView ticTacToe = (GridView) findViewById(R.id.ttt_board);

        int gvHeight = dpToPx(this, 80 * 3);

        board = Piece.getInitailData();
        mAdapter = new GridAdapter(this, R.layout.grid_cell_layout, board);
        ticTacToe.setAdapter(mAdapter);
//        ticTacToe.setLayoutParams(new GridView.LayoutParams(gvHeight, gvHeight));

        ticTacToe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Piece.Color color = Piece.hasWon(board);
                if (color != Piece.Color.Empty) {
                    showToast("Player " + color.ordinal() + " has won!");
                    return;
                }
                Piece move = mAdapter.getItem(position);
                if (!move.isEmpty()) {
                    showToast("CELL is not empty");
                } else {
                    if (player) {
                        move.setColor(1);
                    } else {
                        move.setColor(2);
                    }
                    player = !player;
                    mAdapter.updateEntry(position, move);
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // read a view's height
    private int viewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    // read a view's width
    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
