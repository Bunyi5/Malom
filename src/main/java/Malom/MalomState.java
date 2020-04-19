package Malom;

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

    private int blackPieceNum() {
        return (int) Arrays.stream(board, 0, 24).filter(value -> value == 1).count();
    }

    private int whitePieceNum() {
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

    public boolean canItRemovePiece() {
        int num = 0;
        if (blackTurn) {
            for (int i = 0; i < 24; i++) {
                if (this.isBlack(i) && !this.isMill(i, 1)) {
                    num++;
                }
            }
        } else {
            for (int i = 0; i < 24; i++) {
                if (this.isWhite(i) && !this.isMill(i, 2)) {
                    num++;
                }
            }
        }
        return num != 0;
    }

    public void removePiece(int index) {
        board[index] = 0;
    }

    public boolean canItJump(int index) {
        return this.isWhite(index) && this.whitePieceNum() == 3 ||
                this.isBlack(index) && this.blackPieceNum() == 3;
    }

    public boolean nextPlayerCantMove() {
        if (!this.isPieceStoreEmpty()) {
            return false;
        }
        
        int num = 0;
        List<Integer> where;
        
        if (blackTurn) {
            for (int i = 0; i < 24; i++) {
                if (board[i] == 1) {
                    where = this.whereCanThePieceMove(i);
                    for (int integer : where) {
                        if (board[integer] == 0) {
                            num++;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 24; i++) {
                if (board[i] == 2) {
                    where = this.whereCanThePieceMove(i);
                    for (int integer : where) {
                        if (board[integer] == 0) {
                            num++;
                        }
                    }
                }
            }
        }
        return num == 0;
    }

    public List<Integer> whereCanThePieceMove(int index) {
        List<Integer> whereToMove = new ArrayList<>();

        if (index % 2 == 0) {
            whereToMove.add(index + 1);
            if (index == 0 || index == 8 || index == 16) {
                whereToMove.add(index + 7);
            } else {
                whereToMove.add(index - 1);
            }
        } else {
            if (index < 8) {
                whereToMove.add(index - 1);
                whereToMove.add(index + 8);
                if (index == 7) {
                    whereToMove.add(0);
                } else {
                    whereToMove.add(index + 1);
                }
            } else if (index < 16) {
                whereToMove.add(index - 1);
                whereToMove.add(index + 8);
                whereToMove.add(index - 8);
                if (index == 15) {
                    whereToMove.add(8);
                } else {
                    whereToMove.add(index + 1);
                }
            } else {
                whereToMove.add(index - 1);
                whereToMove.add(index - 8);
                if (index == 23) {
                    whereToMove.add(16);
                } else {
                    whereToMove.add(index + 1);
                }
            }
        }

        return whereToMove;
    }

    private boolean isMill(int index, int color) {

        if (index % 2 == 0) {
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
        } else {
            if (index < 8) {
                if (index == 7) {
                    return board[index] == color && board[index - 1] == color && board[0] == color ||
                            board[index] == color && board[index + 8] == color && board[index + 16] == color;
                } else {
                    return board[index] == color && board[index - 1] == color && board[index + 1] == color ||
                            board[index] == color && board[index + 8] == color && board[index + 16] == color;
                }
            } else if (index < 16) {
                if (index == 15) {
                    return board[index] == color && board[index - 1] == color && board[8] == color ||
                            board[index] == color && board[index - 8] == color && board[index + 8] == color;
                } else {
                    return board[index] == color && board[index - 1] == color && board[index + 1] == color ||
                            board[index] == color && board[index - 8] == color && board[index + 8] == color;
                }
            } else {
                if (index == 23) {
                    return board[index] == color && board[index - 1] == color && board[16] == color ||
                            board[index] == color && board[index - 8] == color && board[index - 16] == color;
                } else {
                    return board[index] == color && board[index - 1] == color && board[index + 1] == color ||
                            board[index] == color && board[index - 8] == color && board[index - 16] == color;
                }
            }
        }

    }

    public boolean isSomeoneHasMill(int index) {
        return this.isMill(index, 1) || this.isMill(index, 2);
    }

    private boolean isBlackWin(int index) {
        return this.isPieceStoreEmpty() && this.whitePieceNum() == 3 && this.isMill(index, 1);
    }

    private boolean isWhiteWin(int index) {
        return this.isPieceStoreEmpty() && this.blackPieceNum() == 3 && this.isMill(index, 2);
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
