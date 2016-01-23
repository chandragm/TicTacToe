package in.chandramouligoru.tictactoe.model;

/**
 * Created by chandramouligoru on 1/24/16.
 */
public class Winner {
    private boolean hasAWinner;
    private String positions;
    private Piece.Check check;
    private Piece.Color color;

    public boolean hasAWinner() {
        return hasAWinner;
    }

    public void setHasAWinner(boolean hasAWinner) {
        this.hasAWinner = hasAWinner;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public Piece.Check getCheck() {
        return check;
    }

    public void setCheck(Piece.Check check) {
        this.check = check;
    }

    public Piece.Color getColor() {
        return color;
    }

    public void setColor(Piece.Color color) {
        this.color = color;
    }
}
