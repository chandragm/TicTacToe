package in.chandramouligoru.tictactoe.game;

import android.content.Context;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public interface IGameView {

    void makeAMove(int position);

    void startGame();

    void endGame();

    void destroy();

    Context getAppContext();
}
