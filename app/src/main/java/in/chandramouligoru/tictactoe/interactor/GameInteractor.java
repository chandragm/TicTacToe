package in.chandramouligoru.tictactoe.interactor;

import android.content.Context;

import java.util.List;

import in.chandramouligoru.tictactoe.data.SharedPreferenceManager;
import in.chandramouligoru.tictactoe.game.IGameInteractor;
import in.chandramouligoru.tictactoe.game.IGameStorageManager;
import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Piece.Check;
import in.chandramouligoru.tictactoe.model.Piece.Color;
import in.chandramouligoru.tictactoe.model.Result;

import static in.chandramouligoru.tictactoe.game.GameConfig.NUM_ROWS;
import static in.chandramouligoru.tictactoe.game.GameConfig.NUM_COLS;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public class GameInteractor implements IGameInteractor {

    private Color[][] grid = new Color[NUM_ROWS][NUM_COLS];
    private IGameStorageManager iGameStorageManager;

    public GameInteractor(Context appContext) {
        iGameStorageManager = new SharedPreferenceManager(appContext);
    }

    private Color getIthColor(Color[][] board, int index, int var, Check check) {
        if (check == Check.ROW) return board[index][var];
        else if (check == Check.COLUMN) return board[var][index];
        else if (check == Check.DIAGONAL) return board[var][var];
        else if (check == Check.REVERSE_DIAGONAL)
            return board[board.length - 1 - var][var];
        return Color.EMPTY;
    }

    private Color getWinner(Color[][] board, int fixed_index, Check check) {
        Color color = getIthColor(board, fixed_index, 0, check);
        if (color == Color.EMPTY) return Color.EMPTY;
        for (int var = 1; var < board.length; var++) {
            if (color != getIthColor(board, fixed_index, var, check))
                return Color.EMPTY;
        }
        return color;
    }

    private Color hasWon(Color[][] board) {
        int N = board.length;
        Color winner;
        // Check rows and columns
        for (int i = 0; i < N; i++) {
            winner = getWinner(board, i, Check.ROW);
            if (winner != Color.EMPTY) {
                return winner;
            }
            winner = getWinner(board, i, Check.COLUMN);
            if (winner != Color.EMPTY) {
                return winner;
            }
        }
        winner = getWinner(board, -1, Check.DIAGONAL);
        if (winner != Color.EMPTY) {
            return winner;
        }
        // Check diagonal
        winner = getWinner(board, -1, Check.REVERSE_DIAGONAL);
        if (winner != Color.EMPTY) {
            return winner;
        }
        return Color.EMPTY;
    }

    @Override
    public boolean hasWon(List<Piece> board) {
        boolean hasEmptyCell = false;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int index = board.get(i * NUM_ROWS + j).getColor();
                switch (index) {
                    case 0:
                        grid[i][j] = Color.EMPTY;
                        hasEmptyCell = true;
                        break;
                    case 1:
                        grid[i][j] = Color.PLAYER;
                        break;
                    case 2:
                        grid[i][j] = Color.COMPUTER;
                        break;

                }
            }
        }
        Color color = hasWon(grid);

        //Tie/Won/Lost
        if(!hasEmptyCell || (color != Color.EMPTY)) {
            iGameStorageManager.storeResult(color.ordinal());
            return true;
        }
        return false;
    }

    @Override
    public Result getResults() {
        return iGameStorageManager.getResults();
    }
}
