package malom.state;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MalomState {

    @Getter
    @Setter
    private boolean blackTurn = true;

    private int[] board = {
            0, 0, 0, 0, 0, 0, 0, 0, //Outside Circle
            0, 0, 0, 0, 0, 0, 0, 0, //Middle Circle
            0, 0, 0, 0, 0, 0, 0, 0, //Inside Circle
            1, 1, 1, 1, 1, 1, 1, 1, 1, //Black
            2, 2, 2, 2, 2, 2, 2, 2, 2 //White
    };

    private boolean isBlack(int index) {
        return board[index] == 1;
    }

    private boolean isWhite(int index) {
        return board[index] == 2;
    }

    public int blackPieceNum() {
        return (int) Arrays.stream(board, 0, 24).filter(value -> value == 1).count();
    }

    public int whitePieceNum() {
        return (int) Arrays.stream(board, 0, 24).filter(value -> value == 2).count();
    }

    public boolean isPieceStoreEmpty() {
        return Arrays.stream(board, 24, 42).sum() == 0;
    }

    public boolean isInPieceStoreOrEmptyStore(int index) {
        return index > 23 || this.isPieceStoreEmpty();
    }

    public boolean isThisColorNext(int index) {
        if (this.isBlack(index) && blackTurn) {
            return true;
        } else return this.isWhite(index) && !blackTurn;
    }

    public void swapPieceValues(int a, int b) {
        int temp = board[a];
        board[a] = board[b];
        board[b] = temp;
    }

    private boolean canRemoveFromColor(int color) {
        int num = 0;

        for (int i = 0; i < 24; i++) {
            if (board[i] == color && !this.isPieceInMill(i, color)) {
                num++;
            }
        }

        return num != 0;
    }

    public boolean canItRemovePiece() {

        if (blackTurn) {
            return canRemoveFromColor(1);
        } else {
            return canRemoveFromColor(2);
        }

    }

    public void removePiece(int index) {
        board[index] = 0;
    }

    public boolean canItJump(int index) {
        return this.isWhite(index) && this.whitePieceNum() == 3 ||
                this.isBlack(index) && this.blackPieceNum() == 3;
    }

    private boolean isColorCantStep(int color) {

        int num = 0;
        List<Integer> where;

        for (int i = 0; i < 24; i++) {
            if (board[i] == color) {
                where = this.whereCanThePieceMove(i);
                for (int integer : where) {
                    if (board[integer] == 0) {
                        num++;
                    }
                }
            }
        }

        return num == 0;
    }

    public boolean isTheNextPlayerCantMove() {
        if (!this.isPieceStoreEmpty()) {
            return false;
        }
        
        if (blackTurn) {
            return isColorCantStep(1);
        } else {
            return isColorCantStep(2);
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

    private boolean isPieceInMillInCorner(int index, int color) {
        if (index == 0 || index == 8 || index == 16) {
            return board[index] == color && board[index + 1] == color && board[index + 2] == color ||
                    board[index] == color && board[index + 7] == color && board[index + 6] == color;
        } else if (index == 6 || index == 14 || index == 22) {
            return board[index] == color && board[index + 1] == color && board[index - 6] == color ||
                    board[index] == color && board[index - 1] == color && board[index - 2] == color;
        } else {
            return board[index] == color && board[index + 1] == color && board[index + 2] == color ||
                    board[index] == color && board[index - 1] == color && board[index - 2] == color;
        }
    }

    private boolean isPieceInMillInOutsideCircle(int index, int color) {
        if (index == 7) {
            return board[index] == color && board[index - 1] == color && board[0] == color ||
                    board[index] == color && board[index + 8] == color && board[index + 16] == color;
        } else {
            return board[index] == color && board[index - 1] == color && board[index + 1] == color ||
                    board[index] == color && board[index + 8] == color && board[index + 16] == color;
        }
    }

    private boolean isPieceInMillInMiddleCircle(int index, int color) {
        if (index == 15) {
            return board[index] == color && board[index - 1] == color && board[8] == color ||
                    board[index] == color && board[index - 8] == color && board[index + 8] == color;
        } else {
            return board[index] == color && board[index - 1] == color && board[index + 1] == color ||
                    board[index] == color && board[index - 8] == color && board[index + 8] == color;
        }
    }

    private boolean isPieceInMillInInsideCircle(int index, int color) {
        if (index == 23) {
            return board[index] == color && board[index - 1] == color && board[16] == color ||
                    board[index] == color && board[index - 8] == color && board[index - 16] == color;
        } else {
            return board[index] == color && board[index - 1] == color && board[index + 1] == color ||
                    board[index] == color && board[index - 8] == color && board[index - 16] == color;
        }
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

    public boolean isSomeoneHasMill(int index) {
        return this.isPieceInMill(index, 1) || this.isPieceInMill(index, 2);
    }

    private boolean isBlackWin(int index) {
        return this.isPieceStoreEmpty() && this.whitePieceNum() == 3 && this.isPieceInMill(index, 1);
    }

    private boolean isWhiteWin(int index) {
        return this.isPieceStoreEmpty() && this.blackPieceNum() == 3 && this.isPieceInMill(index, 2);
    }

    public boolean isGameEnded(int index) {
        return this.isBlackWin(index) || this.isWhiteWin(index);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            sb.append(board[i]).append(' ');
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

    public static void main(String[] args) {
        MalomState state = new MalomState();
        state.swapPieceValues(6, 26);
        state.removePiece(6);
        System.out.println(state.toString());
    }
}
