package in.chandramouligoru.tictactoe.model;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public class Result {
    private int won;
    private int lost;

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    private int tied;


}
