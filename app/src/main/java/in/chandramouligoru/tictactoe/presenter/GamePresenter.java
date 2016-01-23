package in.chandramouligoru.tictactoe.presenter;

import java.util.List;

import in.chandramouligoru.tictactoe.game.IGameInteractor;
import in.chandramouligoru.tictactoe.game.IGamePresenter;
import in.chandramouligoru.tictactoe.game.IGameView;
import in.chandramouligoru.tictactoe.interactor.GameInteractor;
import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Result;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public class GamePresenter implements IGamePresenter {

    private final IGameView iGameView;
    private final IGameInteractor iGameInteractor;

    public GamePresenter(IGameView iGameView) {
        this.iGameView = iGameView;
        iGameInteractor = new GameInteractor(iGameView.getAppContext());
    }

    @Override
    public void moveTaken(int position) {
    }

    @Override
    public boolean hasWon(List<Piece> board) {
        boolean result = iGameInteractor.hasWon(board);
        if (result)
            iGameView.endGame();
        return result;
    }

    @Override
    public Result getResults() {
        return iGameInteractor.getResults();
    }
}
