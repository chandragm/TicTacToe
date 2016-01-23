package in.chandramouligoru.tictactoe.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import in.chandramouligoru.tictactoe.game.GameConfig;
import in.chandramouligoru.tictactoe.game.IGameStorageManager;
import in.chandramouligoru.tictactoe.model.Result;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public class SharedPreferenceManager implements IGameStorageManager {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPreferenceManager(Context appContext) {
        mSharedPreferences = appContext.getSharedPreferences(GameConfig.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    public void storeResult(int matchResult) {
        //commit the match result to shared pref.
        int previousState = 0;
        switch (matchResult) {
            case GameConfig.GAME_WON:
                previousState = mSharedPreferences.getInt(GameConfig.SHARED_PREF_ARG_WON, previousState);
                mEditor.putInt(GameConfig.SHARED_PREF_ARG_WON, ++previousState);
                mEditor.commit();
                break;

            case GameConfig.GAME_LOST:
                previousState = mSharedPreferences.getInt(GameConfig.SHARED_PREF_ARG_LOST, previousState);
                mEditor.putInt(GameConfig.SHARED_PREF_ARG_LOST, ++previousState);
                mEditor.commit();
                break;

            case GameConfig.GAME_TIE:
                previousState = mSharedPreferences.getInt(GameConfig.SHARED_PREF_ARG_TIE, previousState);
                mEditor.putInt(GameConfig.SHARED_PREF_ARG_TIE, ++previousState);
                mEditor.commit();
                break;
        }
    }

    @Override
    public Result getResults() {
        int defaultValue = 0;
        Result result = new Result();
        result.setWon(mSharedPreferences.getInt(GameConfig.SHARED_PREF_ARG_WON, defaultValue));
        result.setLost(mSharedPreferences.getInt(GameConfig.SHARED_PREF_ARG_LOST, defaultValue));
        result.setTied(mSharedPreferences.getInt(GameConfig.SHARED_PREF_ARG_TIE, defaultValue));

        return result;
    }
}
