package in.chandramouligoru.tictactoe.game;

import in.chandramouligoru.tictactoe.model.Result;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public interface IGameStorageManager {

    void storeResult(int matchResult);

    Result getResults();
}
