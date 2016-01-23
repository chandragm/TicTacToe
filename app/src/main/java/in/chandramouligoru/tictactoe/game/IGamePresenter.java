package in.chandramouligoru.tictactoe.game;

import java.util.List;

import in.chandramouligoru.tictactoe.model.Piece;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public interface IGamePresenter {
    void moveTaken(int position);

    boolean hasWon(List<Piece> board);
}
