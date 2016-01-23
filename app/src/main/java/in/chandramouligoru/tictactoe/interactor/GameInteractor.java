package in.chandramouligoru.tictactoe.interactor;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import in.chandramouligoru.tictactoe.data.SharedPreferenceManager;
import in.chandramouligoru.tictactoe.game.IGameInteractor;
import in.chandramouligoru.tictactoe.game.IGameStorageManager;
import in.chandramouligoru.tictactoe.model.Piece;
import in.chandramouligoru.tictactoe.model.Piece.Check;
import in.chandramouligoru.tictactoe.model.Piece.Color;
import in.chandramouligoru.tictactoe.model.Result;
import in.chandramouligoru.tictactoe.model.Winner;

import static in.chandramouligoru.tictactoe.game.GameConfig.NUM_COLS;
import static in.chandramouligoru.tictactoe.game.GameConfig.NUM_ROWS;

/**
 * Created by chandramouligoru on 1/23/16.
 */
public class GameInteractor implements IGameInteractor {

    private Color[][] grid = new Color[NUM_ROWS][NUM_COLS];
    private IGameStorageManager iGameStorageManager;

    //A very naive but an efficient approach. store the moves made so far in maps
    private int[] corners = {0, 2, 6, 8};
    private int[] edges = {1, 3, 5, 7};
    private int center = 4;

    private int previousMove;
    private int[] playerMoves = new int[9];
    private int[] androidMoves = new int[9];
    private int[] availableMoves = new int[9];

    private int playerMoveCount;
    private int androidMoveCount;

    private int wildCard = -1;
    private Winner mWinner;

    public GameInteractor(Context appContext) {
        iGameStorageManager = new SharedPreferenceManager(appContext);
        Arrays.fill(availableMoves, 1);
        mWinner = new Winner();
    }

    private Color getIthColor(Color[][] board, int index, int var, Check check) {
        if (check == Check.ROW) return board[index][var];
        else if (check == Check.COLUMN) return board[var][index];
        else if (check == Check.DIAGONAL) return board[var][var];
        else if (check == Check.REVERSE_DIAGONAL)
            return board[board.length - 1 - var][var];
        return Color.EMPTY;
    }

    private Color getWinner(Color[][] board, int fixed_index, Check check) {
        Color color = getIthColor(board, fixed_index, 0, check);
        if (color == Color.EMPTY) return Color.EMPTY;
        for (int var = 1; var < board.length; var++) {
            if (color != getIthColor(board, fixed_index, var, check))
                return Color.EMPTY;
        }
        return color;
    }

    private Color hasWon(Color[][] board) {
        int N = board.length;
        Color winner;
        // Check rows and columns
        for (int i = 0; i < N; i++) {
            winner = getWinner(board, i, Check.ROW);
            if (winner != Color.EMPTY) {
                mWinner.setPositions(getPositionFromIndex(i, Check.ROW));
                mWinner.setCheck(Check.ROW);
                mWinner.setColor(winner);
                mWinner.setHasAWinner(true);
                return winner;
            }
            winner = getWinner(board, i, Check.COLUMN);
            if (winner != Color.EMPTY) {
                mWinner.setPositions(getPositionFromIndex(i, Check.COLUMN));
                mWinner.setCheck(Check.COLUMN);
                mWinner.setColor(winner);
                mWinner.setHasAWinner(true);
                return winner;
            }
        }
        winner = getWinner(board, -1, Check.DIAGONAL);
        if (winner != Color.EMPTY) {
            mWinner.setPositions(getPositionFromIndex(-1, Check.DIAGONAL));
            mWinner.setCheck(Check.DIAGONAL);
            mWinner.setColor(winner);
            mWinner.setHasAWinner(true);
            return winner;
        }
        // Check diagonal
        winner = getWinner(board, -1, Check.REVERSE_DIAGONAL);
        if (winner != Color.EMPTY) {
            mWinner.setPositions(getPositionFromIndex(-1, Check.REVERSE_DIAGONAL));
            mWinner.setCheck(Check.REVERSE_DIAGONAL);
            mWinner.setColor(winner);
            mWinner.setHasAWinner(true);
            return winner;
        }
        return Color.EMPTY;
    }

    @Override
    public boolean hasWon(List<Piece> board) {
        boolean hasEmptyCell = false;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int index = board.get(i * NUM_ROWS + j).getColor();
                switch (index) {
                    case 0:
                        grid[i][j] = Color.EMPTY;
                        hasEmptyCell = true;
                        break;
                    case 1:
                        grid[i][j] = Color.PLAYER;
                        break;
                    case 2:
                        grid[i][j] = Color.ANDROID;
                        break;

                }
            }
        }
        Color color = hasWon(grid);

        //Tie/Won/Lost
        if (!hasEmptyCell || (color != Color.EMPTY)) {
            iGameStorageManager.storeResult(color.ordinal());
            return true;
        }
        return false;
    }

    private Color[][] converToColorBoard(List<Piece> board) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int index = board.get(i * NUM_ROWS + j).getColor();
                switch (index) {
                    case 0:
                        grid[i][j] = Color.EMPTY;
                        break;
                    case 1:
                        grid[i][j] = Color.PLAYER;
                        break;
                    case 2:
                        grid[i][j] = Color.ANDROID;
                        break;

                }
            }
        }
        return grid;
    }

    @Override
    public Result getResults() {
        return iGameStorageManager.getResults();
    }

    @Override
    public Winner getWinner() {
        return mWinner;
    }

    /**
     * strategy
     * 1. WIN
     * 2. BLOCK
     *
     * @param board
     * @return position in the adapter
     */
    @Override
    public int nextMove(List<Piece> board) {
        int move = dumbMove();
//        if (androidMoveCount == 0)
//            move = firstMove(board);
//        else
//            move = chooseMoveWisely(board);

        //Android's move
        androidMoveCount++;
        androidMoves[move] = 1;
        availableMoves[move] = 0;
        return move;
    }

    private int dumbMove() {
        int i = 0;
        for (; i < 9; ++i) {
            if (availableMoves[i] == 1)
                break;
        }
        return i;
    }

    @Override
    public void cleanUp() {
        previousMove = -1;
        Arrays.fill(playerMoves, 0);
        Arrays.fill(androidMoves, 0);
        Arrays.fill(availableMoves, 1);

        playerMoveCount = 0;
        androidMoveCount = 0;
        mWinner.setPositions(null);
        mWinner.setHasAWinner(false);
        mWinner.setColor(Color.EMPTY);
        mWinner.setCheck(null);
    }

    @Override
    public void onMoveMade(int position) {
        playerMoveCount++;
        playerMoves[position] = 1;
        availableMoves[position] = 0;
    }

    /**
     * 1-turn
     * corner/ edge/ center
     * <p/>
     * respond to opponent's 1-turn
     * corner -> center
     * center -> corner
     * edge -> center/ corner next to x/ opposite edge.
     *
     * @return
     */
    private int firstMove(List<Piece> board) {
        int firstMove;
        int someRand = new Random().nextInt(4);
        if (androidMoveCount == 0) {
            if (someRand % 2 == 0)
                firstMove = center;
            else
                firstMove = corners[someRand];
        } else {
            if (previousMove % 2 == 0) {
                if (previousMove == center)
                    firstMove = corners[someRand];
                else
                    firstMove = center;
            } else {
                firstMove = center;
            }
        }

        if (availableMoves[firstMove] == 0)
            return firstMove(board);
        else
            return firstMove;
    }

    private int chooseMoveWisely(List<Piece> board) {
        Color[][] colorBoard = converToColorBoard(board);

        int chooseMove;

        //Cant win here goto block
        if (androidMoveCount == 1) {
            //Block
            chooseMove = hasWinningIndex(colorBoard, Color.PLAYER);
        } else {
            //Win
            chooseMove = hasWinningIndex(colorBoard, Color.ANDROID);
        }

        if (chooseMove == wildCard) {
            //Free corner
            for (int i = 0; i < 4; ++i) {
                if (availableMoves[corners[i]] == 1) {
                    chooseMove = corners[i];
                    break;
                }
            }
        }

        if (chooseMove == wildCard) {
            //Free edges
            for (int i = 0; i < 4; ++i) {
                if (availableMoves[edges[i]] == 1) {
                    chooseMove = edges[i];
                    break;
                }
            }
        }

        if (chooseMove == wildCard) {
            for (int i = 0; i < 9; ++i) {
                if (availableMoves[i] == 1) {
                    chooseMove = i;
                    break;
                }
            }
        }

        if (availableMoves[chooseMove] == 0)
            return chooseMoveWisely(board);
        else
            return chooseMove;
    }


    private boolean getIForColor(Color[][] board, int index, int var, Color color, Check check) {
        if (check == Check.ROW)
            return board[index][var] == color || board[index][var] == Color.EMPTY;
        else if (check == Check.COLUMN)
            return board[var][index] == color || board[var][index] == Color.EMPTY;
        else if (check == Check.DIAGONAL)
            return board[var][var] == color || board[var][var] == Color.EMPTY;
        else if (check == Check.REVERSE_DIAGONAL)
            return board[board.length - 1 - var][var] == color
                    || board[board.length - 1 - var][var] == Color.EMPTY;
        return false;
    }

    private int getWinningIndex(Color[][] board, int fixed_index, Color color, Check check) {
        int movePosition;
        int count = 0;
        boolean winner = getIForColor(board, fixed_index, 0, color, check);
        movePosition = calPostionFrom2dArray(fixed_index, 0);
        if (!winner)
            return wildCard;
        count++;
        for (int var = 1; var < board.length; var++) {
            if (!getIForColor(board, fixed_index, var, color, check))
                return wildCard;
            else {
                count++;
                movePosition = calPostionFrom2dArray(fixed_index, var);
            }
        }
        if (count == 2)
            return movePosition;
        else
            return wildCard;
    }

    private int hasWinningIndex(Color[][] board, Color color) {
        int winner;
        // Check rows and columns
        for (int i = 0; i < NUM_ROWS; i++) {
//            if (availableMoves[i] == 0)
//                continue;

            winner = getWinningIndex(board, i, color, Check.ROW);
            if (winner != wildCard) {
                return winner;
            }
            winner = getWinningIndex(board, i, color, Check.COLUMN);
            if (winner != wildCard) {
                return winner;
            }
        }
        winner = getWinningIndex(board, -1, color, Check.DIAGONAL);
        if (winner != wildCard) {
            return winner;
        }
        // Check diagonal
        winner = getWinningIndex(board, -1, color, Check.REVERSE_DIAGONAL);
        if (winner != wildCard) {
            return winner;
        }
        return wildCard;
    }

    private int calPostionFrom2dArray(int row, int col) {
        return row * NUM_ROWS + col;
    }

    private String getPositionFromIndex(int i, Check check) {
        String result = null;
        if (check == Check.ROW) {
            switch (i) {
                case 0:
                    result = "012";
                    break;
                case 1:
                    result = "345";
                    break;
                case 2:
                    result = "678";
                    break;
            }
        }

        if (check == Check.COLUMN) {
            switch (i) {
                case 0:
                    result = "036";
                    break;
                case 1:
                    result = "147";
                    break;
                case 2:
                    result = "258";
                    break;
            }
        }

        if (check == Check.DIAGONAL) {
            result = "048";
        }

        if (check == Check.REVERSE_DIAGONAL) {
            result = "246";
        }
        return result;
    }
}
