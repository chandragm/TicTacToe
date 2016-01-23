package in.chandramouligoru.tictactoe.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.chandramouligoru.tictactoe.R;
import in.chandramouligoru.tictactoe.game.GameConfig;
import in.chandramouligoru.tictactoe.game.IGamePresenter;
import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Result;
import in.chandramouligoru.tictactoe.model.Winner;
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

    private boolean human = true;
    private Handler mHandler;

    public TicTacToeViewBinder(TicTacToeActivity viewController) {
        super(viewController);
        mHandler = new Handler(Looper.myLooper());
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
                    if (human) {
                        move.setColor(GameConfig.HUMAN);
                        mAdapter.updateEntry(position, move);
                        iGamePresenter.onMoveMade(position);
                        human = !human;
                    } else {
                        mController.showToast("Wait for your turn.");
                    }
                }

                if (iGamePresenter.hasWon(mBoard)) {
                    mController.showToast("GAME Over. " + getWinningMessage(iGamePresenter.getWinner()));
                    return;
                } else {
                    if (!human) {
                        //Ask the computer to make a move.
                        iGamePresenter.nextMove(mBoard);
                    }
                }
            }
        });
    }

    private String getWinningMessage(Winner winner) {
        if (winner.getColor() == Piece.Color.PLAYER)
            return "You WON!";

        if (winner.getColor() == Piece.Color.EMPTY)
            return "Its a TIE";

        return "You LOST";
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
        Piece cyborg = mAdapter.getItem(position);
        cyborg.setColor(GameConfig.ANDROID);
        mAdapter.updateEntry(position, cyborg);
        human = !human;

        // TODO: 1/23/16 do it in a better way.
        if (iGamePresenter.hasWon(mBoard)) {
            mController.showToast("GAME Over. " + getWinningMessage(iGamePresenter.getWinner()));
            return;
        }
    }

    @Override
    public void startGame() {
        iGamePresenter = mController.getiGamePresenter();
        initializeData();
        setupAdapter();

        if (!human) {
            //Ask the computer to make a move.
            iGamePresenter.nextMove(mBoard);
        }
    }

    @Override
    public void endGame() {
        //Show game over UI and start over a new game.
        showGameOverUI();
        updateMatchStats();
//        iGamePresenter.cleanUp();
//        startGame();
    }

    private void updateMatchStats() {
        Result result = iGamePresenter.getResults();
        mWon.setText("" + result.getWon());
        mLost.setText("" + result.getLost());
        mTied.setText("" + result.getTied());
    }

    private void showGameOverUI() {
        Winner winner = iGamePresenter.getWinner();
        if (winner.hasAWinner()) {
            initializeData();
            String positions = winner.getPositions();
            for (int i = 0; i < 3; i++) {
                int position = Integer.parseInt(positions.substring(i, i + 1));
                Piece piece = mBoard.get(position);
                piece.setColor(winner.getColor().ordinal());
                mAdapter.updateEntry(position, piece);
            }
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iGamePresenter.cleanUp();
                startGame();
            }
        }, 1000);
    }

    @Override
    public void destroy() {
    }

    @Override
    public Context getAppContext() {
        return null;
    }
}
