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
        color = Color.EMPTY.ordinal();
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

    public enum Color {EMPTY, PLAYER, ANDROID}

    ;

    public enum Check {ROW, COLUMN, DIAGONAL, REVERSE_DIAGONAL}

    ;

    public boolean isEmpty() {
        return this.color == Color.EMPTY.ordinal();
    }
}
