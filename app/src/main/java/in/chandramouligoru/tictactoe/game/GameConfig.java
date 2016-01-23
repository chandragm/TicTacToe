package in.chandramouligoru.tictactoe.game;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public class GameConfig {

    /**
     * TODO
     * 3x3 Grid for TIC-TAC-TOE. Not handling a generic NxN case yet.
     */
    public static final int NUM_ROWS = 3;
    public static final int NUM_COLS = 3;

    public static final String SHARED_PREF_NAME = "tictactoe";

    public static final int GAME_WON = 1;
    public static final int GAME_LOST = 2;
    public static final int GAME_TIE = 0;

    public static final String SHARED_PREF_ARG_WON = "won";
    public static final String SHARED_PREF_ARG_LOST = "lost";
    public static final String SHARED_PREF_ARG_TIE = "tie";
}
