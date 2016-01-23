package in.chandramouligoru.tictactoe.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.chandramouligoru.tictactoe.R;
import in.chandramouligoru.tictactoe.game.IGamePresenter;
import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Result;
import in.chandramouligoru.tictactoe.view.activity.TicTacToeActivity;
import in.chandramouligoru.tictactoe.view.adapter.GridAdapter;

import static in.chandramouligoru.tictactoe.game.GameConfig.NUM_COLS;
import static in.chandramouligoru.tictactoe.game.GameConfig.NUM_ROWS;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public class TicTacToeViewBinder extends ViewBinder<TicTacToeActivity> {

    private GridAdapter mAdapter;
    private GridView mGridView;
    private IGamePresenter iGamePresenter;
    private List<Piece> mBoard;

    private TextView mWon;
    private TextView mLost;
    private TextView mTied;

    private boolean player;

    public TicTacToeViewBinder(TicTacToeActivity viewController) {
        super(viewController);
    }

    @Override
    protected void initContentView() {
        LayoutInflater inflater = mController.getActivityContext().getLayoutInflater();
        mContentView = inflater.inflate(R.layout.activity_tic_tac_toe, null);
        mGridView = (GridView) mContentView.findViewById(R.id.ttt_board);

        mWon = (TextView) mContentView.findViewById(R.id.won);
        mLost = (TextView) mContentView.findViewById(R.id.lost);
        mTied = (TextView) mContentView.findViewById(R.id.tied);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Piece move = mAdapter.getItem(position);
                if (!move.isEmpty()) {
                    mController.showToast("CELL is not empty");
                } else {
                    if (player) {
                        move.setColor(1);
                    } else {
                        move.setColor(2);
                    }
                    player = !player;
                    mAdapter.updateEntry(position, move);
                }

                if (iGamePresenter.hasWon(mBoard)) {
                    mController.showToast("GAME Over!");
                    return;
                }
            }
        });
    }

    public void initializeData() {
        if (mBoard == null)
            mBoard = new ArrayList<>(9);
        else
            mBoard.clear();

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                mBoard.add(new Piece(i, j));
            }
        }
    }

    protected void setupAdapter() {
        mAdapter = new GridAdapter(mController, R.layout.grid_cell_layout, mBoard);
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void makeAMove(int position) {
    }

    @Override
    public void startGame() {
        iGamePresenter = mController.getiGamePresenter();
        initializeData();
        setupAdapter();
    }

    @Override
    public void endGame() {
        //Show game over UI and start over a new game.
        showGameOverUI();
        updateMatchStats();
        startGame();
    }

    private void updateMatchStats() {
        Result result = iGamePresenter.getResults();
        mWon.setText(""+result.getWon());
        mLost.setText(""+result.getLost());
        mTied.setText(""+result.getTied());
    }

    private void showGameOverUI() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public Context getAppContext() {
        return null;
    }
}
