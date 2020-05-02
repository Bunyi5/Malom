package malom.state;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the state of one game.
 */
public class MalomState {

    /**
     * Boolean which {@code true} if the black comes next.
     */
    @Getter
    @Setter
    private boolean blackTurn = true;

    /**
     * Array that stores the board.
     */
    private int[] board = {
            0, 0, 0, 0, 0, 0, 0, 0, //Outside Circle
            0, 0, 0, 0, 0, 0, 0, 0, //Middle Circle
            0, 0, 0, 0, 0, 0, 0, 0, //Inside Circle
            1, 1, 1, 1, 1, 1, 1, 1, 1, //Black
            2, 2, 2, 2, 2, 2, 2, 2, 2 //White
    };

    /**
     * Returns an integer which represents the color of the piece.
     *
     * @param index the index of the piece
     * @return {@code 1} if the piece is black, {@code 2} if the piece is white,
     * {@code 0} if there is no piece there
     */
    public int getColor(int index) {
        return board[index];
    }

    /**
     * Swaps to values on the board.
     *
     * @param a index of a piece
     * @param b index of a piece
     */
    public void swapPieceValues(int a, int b) {
        int temp = board[a];
        board[a] = board[b];
        board[b] = temp;
    }

    /**
     * Removes an element from the board.
     *
     * @param index the index of piece which will be removed
     */
    public void removePiece(int index) {
        board[index] = 0;
    }

    /**
     * Returns how many black pieces are on the board.
     *
     * @return an int value which contains how many black pieces are on the board
     */
    public int blackPieceNum() {
        return (int) Arrays.stream(board, 0, 24).filter(value -> value == 1).count();
    }

    /**
     * Returns how many white pieces are on the board.
     *
     * @return an int value which contains how many white pieces are on the board
     */
    public int whitePieceNum() {
        return (int) Arrays.stream(board, 0, 24).filter(value -> value == 2).count();
    }

    /**
     * Returns whether that the color of the piece is the next who can step.
     *
     * @param index the index of the examined piece
     * @return {@code true} if the piece is black and the black color comes next
     * or the piece is white and the white color comes, {@code false} otherwise
     */
    public boolean isThisColorNext(int index) {
        if (getColor(index) == 1 && blackTurn) {
            return true;
        } else return getColor(index) == 2 && !blackTurn;
    }

    /**
     * Returns whether the piece can jump on the board.
     *
     * @param index the index of the examined piece
     * @return {@code true} if the piece is white and only 3 white left on the board
     * or the piece is black and only 3 black left on the board, {@code false} otherwise
     */
    public boolean canItJump(int index) {
        return getColor(index) == 2 && this.whitePieceNum() == 3 ||
                getColor(index) == 1 && this.blackPieceNum() == 3;
    }

    /**
     * Returns whether all pieces are on the board.
     *
     * @return {@code true} if all pieces are on the board,
     * {@code false} otherwise
     */
    public boolean isAllPiecesWereOnBoard() {
        return Arrays.stream(board, 24, 42).sum() == 0;
    }

    /**
     * Returns whether the piece on the board or all pieces was on the board.
     *
     * @param index the index of the examined piece
     * @return {@code true} if the piece is not on the board
     * or all pieces are on the board, {@code false} otherwise
     */
    public boolean isNotOnBoardOrAllPiecesWereOnBoard(int index) {
        return index > 23 || this.isAllPiecesWereOnBoard();
    }

    /**
     * Returns whether the next player can remove a piece from the opponent.
     *
     * @param index the index of the last moved piece
     * @return {@code true} if the opponent has a piece which is not in mill,
     * {@code false} otherwise
     */
    public boolean canRemovePieceFromTheOtherPlayer(int index) {

        if (getColor(index) == 1) {
            return canRemoveFromColor(2);
        } else return canRemoveFromColor(1);

    }

    private boolean canRemoveFromColor(int color) {
        int num = 0;

        for (int i = 0; i < 24; i++) {
            if (getColor(i) == color && !this.isPieceInMill(i, color)) {
                num++;
            }
        }

        return num != 0;
    }

    /**
     * Returns whether the next player can't move on the board.
     * Only after all pieces have been placed on the board.
     *
     * @return {@code true} if the player in his turn can't move a piece,
     * {@code false} if not all pieces was on the board or the next player can move
     */
    public boolean canThePlayerMoveInHisTurn() {
        if (!this.isAllPiecesWereOnBoard()) {
            return true;
        }

        if (blackTurn) {
            return canColorStep(1);
        } else {
            return canColorStep(2);
        }
    }

    private boolean canColorStep(int color) {

        int num = 0;
        List<Integer> where;

        for (int i = 0; i < 24; i++) {
            if (getColor(i) == color) {
                where = this.whereCanThePieceMove(i);
                for (int index : where) {
                    if (getColor(index) == 0) {
                        num++;
                    }
                }
            }
        }

        return num != 0;
    }

    /**
     * Returns the list of integer which contains where the current piece can move.
     *
     * @param index the index of the examined piece
     * @return the list of integer which contains where the current piece can move
     */
    public List<Integer> whereCanThePieceMove(int index) {

        if (index % 2 == 0) {
            return whereCanMoveFromCorner(index);
        } else {
            if (index < 8) {
                return whereCanMoveFromOutsideCircle(index);
            } else if (index < 16) {
                return whereCanMoveFromMiddleCircle(index);
            } else {
                return whereCanMoveFromInsideCircle(index);
            }
        }

    }

    private List<Integer> whereCanMoveFromCorner(int index) {
        List<Integer> where = new ArrayList<>();

        where.add(index + 1);
        if (index == 0 || index == 8 || index == 16) {
            where.add(index + 7);
        } else {
            where.add(index - 1);
        }

        return where;
    }

    private List<Integer> whereCanMoveFromOutsideCircle(int index) {
        List<Integer> where = new ArrayList<>();

        where.add(index - 1);
        where.add(index + 8);
        if (index == 7) {
            where.add(0);
        } else {
            where.add(index + 1);
        }

        return where;
    }

    private List<Integer> whereCanMoveFromMiddleCircle(int index) {
        List<Integer> where = new ArrayList<>();

        where.add(index - 1);
        where.add(index + 8);
        where.add(index - 8);
        if (index == 15) {
            where.add(8);
        } else {
            where.add(index + 1);
        }

        return where;
    }

    private List<Integer> whereCanMoveFromInsideCircle(int index) {
        List<Integer> where = new ArrayList<>();

        where.add(index - 1);
        where.add(index - 8);
        if (index == 23) {
            where.add(16);
        } else {
            where.add(index + 1);
        }

        return where;
    }

    /**
     * Returns whether the current piece gives someone a mill.
     *
     * @param index the index of the examined piece
     * @return {@code true} if the piece is black and in mill or the piece is white and in mill,
     * {@code false} otherwise
     */
    public boolean isSomeoneHasMill(int index) {
        return this.isPieceInMill(index, 1) || this.isPieceInMill(index, 2);
    }

    private boolean isPieceInMill(int index, int color) {

        if (index % 2 == 0) {
            return isPieceInMillInCorner(index, color);
        } else {
            if (index < 8) {
                return isPieceInMillInOutsideCircle(index, color);
            } else if (index < 16) {
                return isPieceInMillInMiddleCircle(index, color);
            } else {
                return isPieceInMillInInsideCircle(index, color);
            }
        }

    }

    private boolean isPieceInMillInCorner(int index, int color) {
        if (index == 0 || index == 8 || index == 16) {
            return getColor(index) == color && getColor(index + 1) == color && getColor(index + 2) == color ||
                    getColor(index) == color && getColor(index + 7) == color && getColor(index + 6) == color;
        } else if (index == 6 || index == 14 || index == 22) {
            return getColor(index) == color && getColor(index + 1) == color && getColor(index - 6) == color ||
                    getColor(index) == color && getColor(index - 1) == color && getColor(index - 2) == color;
        } else {
            return getColor(index) == color && getColor(index + 1) == color && getColor(index + 2) == color ||
                    getColor(index) == color && getColor(index - 1) == color && getColor(index - 2) == color;
        }
    }

    private boolean isPieceInMillInOutsideCircle(int index, int color) {
        if (index == 7) {
            return getColor(index) == color && getColor(index - 1) == color && getColor(0) == color ||
                    getColor(index) == color && getColor(index + 8) == color && getColor(index + 16) == color;
        } else {
            return getColor(index) == color && getColor(index - 1) == color && getColor(index + 1) == color ||
                    getColor(index) == color && getColor(index + 8) == color && getColor(index + 16) == color;
        }
    }

    private boolean isPieceInMillInMiddleCircle(int index, int color) {
        if (index == 15) {
            return getColor(index) == color && getColor(index - 1) == color && getColor(8) == color ||
                    getColor(index) == color && getColor(index - 8) == color && getColor(index + 8) == color;
        } else {
            return getColor(index) == color && getColor(index - 1) == color && getColor(index + 1) == color ||
                    getColor(index) == color && getColor(index - 8) == color && getColor(index + 8) == color;
        }
    }

    private boolean isPieceInMillInInsideCircle(int index, int color) {
        if (index == 23) {
            return getColor(index) == color && getColor(index - 1) == color && getColor(16) == color ||
                    getColor(index) == color && getColor(index - 8) == color && getColor(index - 16) == color;
        } else {
            return getColor(index) == color && getColor(index - 1) == color && getColor(index + 1) == color ||
                    getColor(index) == color && getColor(index - 8) == color && getColor(index - 16) == color;
        }
    }

    /**
     * Returns whether someone wins the game.
     *
     * @param index the index of the last moved piece
     * @return {@code true} if the black or white wins the game
     * {@code false} otherwise
     */
    public boolean isGameEnded(int index) {
        return this.isBlackWin(index) || this.isWhiteWin(index);
    }

    private boolean isBlackWin(int index) {
        return this.isAllPiecesWereOnBoard() && this.whitePieceNum() == 3 && this.isPieceInMill(index, 1);
    }

    private boolean isWhiteWin(int index) {
        return this.isAllPiecesWereOnBoard() && this.blackPieceNum() == 3 && this.isPieceInMill(index, 2);
    }

    /**
     * Returns the board in string format.
     *
     * @return a string which contains the board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            sb.append(getColor(i)).append(' ');
            if (i == 7) {
                sb.append('\n');
            } else if (i == 15) {
                sb.append('\n');
            } else if (i == 23) {
                sb.append('\n');
            } else if (i == 32) {
                sb.append('\n');
            } else if (i == 41) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

}
