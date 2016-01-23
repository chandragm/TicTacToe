package in.chandramouligoru.tictactoe.game;

import java.util.List;

import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Result;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public interface IGameInteractor {
    boolean hasWon(List<Piece> board);

    Result getResults();
}
