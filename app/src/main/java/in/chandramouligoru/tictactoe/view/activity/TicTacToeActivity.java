package in.chandramouligoru.tictactoe.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import in.chandramouligoru.tictactoe.game.IGamePresenter;
import in.chandramouligoru.tictactoe.game.IGameView;
import in.chandramouligoru.tictactoe.presenter.GamePresenter;
import in.chandramouligoru.tictactoe.view.TicTacToeViewBinder;
import in.chandramouligoru.tictactoe.view.ViewBinder;
import in.chandramouligoru.tictactoe.view.ViewController;

public class TicTacToeActivity extends AppCompatActivity implements ViewController, IGameView {

    private ViewBinder mViewBinder;
    private IGamePresenter iGamePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinder = new TicTacToeViewBinder(this);
        setContentView(mViewBinder.getContentView());
        iGamePresenter = new GamePresenter(this);
        startGame();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getActivityContext() {
        return TicTacToeActivity.this;
    }

    @Override
    public void makeAMove(int position) {

    }

    @Override
    public void startGame() {
        //Initialize the grid and update the UI
        mViewBinder.startGame();
    }

    @Override
    public void endGame() {
        // TODO: 1/23/16 update the UI and start a new game.
        mViewBinder.endGame();
    }

    @Override
    public void destroy() {

    }

    @Override
    public Context getAppContext() {
        return TicTacToeActivity.this.getApplicationContext();
    }

    public IGamePresenter getiGamePresenter() {
        return iGamePresenter;
    }
}
