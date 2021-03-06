package in.chandramouligoru.tictactoe.view.viewbinder;

import android.view.View;

import in.chandramouligoru.tictactoe.game.IGameView;
import in.chandramouligoru.tictactoe.view.ViewController;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public abstract class ViewBinder<T extends ViewController> implements IGameView {

    protected T mController;
    protected View mContentView;

    public ViewBinder(final T viewController) {
        mController = viewController;
        initContentView();
    }

    protected abstract void initContentView();

    public View getContentView() {
        return mContentView;
    }
}
