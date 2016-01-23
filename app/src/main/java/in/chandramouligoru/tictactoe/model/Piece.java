package in.chandramouligoru.tictactoe.model;

/**
 * Created by chandramouligoru on 1/20/16.
 */
public class Piece {
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

    public boolean isEmpty() {
        return this.color == Color.Empty.ordinal();
    }
}
