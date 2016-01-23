package in.chandramouligoru.tictactoe.interactor;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import in.chandramouligoru.tictactoe.data.SharedPreferenceManager;
import in.chandramouligoru.tictactoe.game.IGameInteractor;
import in.chandramouligoru.tictactoe.game.IGameStorageManager;
import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Piece.Check;
import in.chandramouligoru.tictactoe.model.Piece.Color;

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
        if (check == Check.Row) return board[index][var];
        else if (check == Check.Column) return board[var][index];
        else if (check == Check.Diagonal) return board[var][var];
        else if (check == Check.ReverseDiagonal)
            return board[board.length - 1 - var][var];
        return Color.Empty;
    }

    private Color getWinner(Color[][] board, int fixed_index, Check check) {
        Color color = getIthColor(board, fixed_index, 0, check);
        if (color == Color.Empty) return Color.Empty;
        for (int var = 1; var < board.length; var++) {
            if (color != getIthColor(board, fixed_index, var, check))
                return Color.Empty;
        }
        return color;
    }

    private Color hasWon(Color[][] board) {
        int N = board.length;
        Color winner = Color.Empty;
        // Check rows and columns
        for (int i = 0; i < N; i++) {
            winner = getWinner(board, i, Check.Row);
            if (winner != Color.Empty) {
                return winner;
            }
            winner = getWinner(board, i, Check.Column);
            if (winner != Color.Empty) {
                return winner;
            }
        }
        winner = getWinner(board, -1, Check.Diagonal);
        if (winner != Color.Empty) {
            return winner;
        }
        // Check diagonal
        winner = getWinner(board, -1, Check.ReverseDiagonal);
        if (winner != Color.Empty) {
            return winner;
        }
        return Color.Empty;
    }

    @Override
    public boolean hasWon(List<Piece> board) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int index = board.get(i * NUM_ROWS + j).getColor();
                switch (index) {
                    case 0:
                        grid[i][j] = Color.Empty;
                        break;
                    case 1:
                        grid[i][j] = Color.Red;
                        break;
                    case 2:
                        grid[i][j] = Color.Blue;
                        break;

                }
            }
        }
        return hasWon(grid) != Color.Empty;
    }
}
