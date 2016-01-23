package in.chandramouligoru.tictactoe.game;

import java.util.List;

import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Result;
import in.chandramouligoru.tictactoe.model.Winner;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public interface IGamePresenter {
    void nextMove(List<Piece> board);

    boolean hasWon(List<Piece> board);

    Result getResults();

    Winner getWinner();

    void onMoveMade(int position);

    void cleanUp();
}
