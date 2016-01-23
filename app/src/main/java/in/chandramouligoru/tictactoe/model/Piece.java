package in.chandramouligoru.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandramouligoru on 1/20/16.
 */
public class Piece {

    public static final int NUM_ROWS = 3;
    public static final int NUM_COLS = 3;

    private static Color[][] grid = new Color[3][3];

    private int row;
    private int column;
    private int color;

    public Piece(int row, int col) {
        this.row = row;
        this.column = col;
        color = Color.Empty.ordinal();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public enum Color {Empty, Red, Blue}

    ;

    public enum Check {Row, Column, Diagonal, ReverseDiagonal}

    ;

    public static Color getIthColor(Color[][] board, int index, int var, Check check) {
        if (check == Check.Row) return board[index][var];
        else if (check == Check.Column) return board[var][index];
        else if (check == Check.Diagonal) return board[var][var];
        else if (check == Check.ReverseDiagonal)
            return board[board.length - 1 - var][var];
        return Color.Empty;
    }

    public static Color getWinner(Color[][] board, int fixed_index, Check check) {
        Color color = getIthColor(board, fixed_index, 0, check);
        if (color == Color.Empty) return Color.Empty;
        for (int var = 1; var < board.length; var++) {
            if (color != getIthColor(board, fixed_index, var, check))
                return Color.Empty;
        }
        return color;
    }

    public static Color hasWon(List<Piece> board) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int index = board.get(i * NUM_ROWS + j).color;
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
        return hasWon(grid);
    }

    public static Color hasWon(Color[][] board) {
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

    public static List<Piece> getInitailData() {
        List<Piece> list = new ArrayList<>(9);
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                list.add(new Piece(i, j));
            }
        }
        return list;
    }


    public boolean isEmpty() {
        return this.color == Color.Empty.ordinal();
    }
}
