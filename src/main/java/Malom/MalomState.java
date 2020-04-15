package Malom;

import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MalomState {

    @Setter
    private boolean blackTurn = true;
    private int blackPieceNum = 0;
    private int whitePieceNum = 0;

    private int[] board = {
            0, 0, 0, 0, 0, 0, 0, 0, //Outside Circle
            0, 0, 0, 0, 0, 0, 0, 0, //Middle Circle
            0, 0, 0, 0, 0, 0, 0, 0, //Inside Circle
            1, 1, 1, 1, 1, 1, 1, 1, 1, //Black
            2, 2, 2, 2, 2, 2, 2, 2, 2 //White
    };

    public void swap(int a, int b) {
        int temp = board[a];
        board[a] = board[b];
        board[b] = temp;
    }

    public boolean isPieceStoreEmpty() {
        return Arrays.stream(board, 24, 41).sum() == 0;
    }

    public boolean isInPieceStore(int index) {
        return index > 23 || this.isPieceStoreEmpty();
    }

    public boolean whoIsNext(int index) {
        if (board[index] == 1 && blackTurn) {
            return true;
        } else return board[index] == 2 && !blackTurn;
    }

    public boolean isBlack(int index) {
        if (board[index] == 1) {
            blackPieceNum++;
            return true;
        } else {
            whitePieceNum++;
            return false;
        }
    }

    public void removePiece(int index) {
        if (board[index] == 1) {
            blackPieceNum--;
        } else {
            whitePieceNum--;
        }
        board[index] = 0;
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

    public boolean doesBlackHaveMill(int index) {

        if (index % 2 == 0) {
            if (index == 0 || index == 8 || index == 16) {
                return board[index] == 1 && board[index + 1] == 1 && board[index + 2] == 1 ||
                        board[index] == 1 && board[index + 7] == 1 && board[index + 6] == 1;
            } else {
                return board[index] == 1 && board[index + 1] == 1 && board[index + 2] == 1 ||
                        board[index] == 1 && board[index - 1] == 1 && board[index - 2] == 1;
            }
        } else {
            if (index < 8) {
                if (index == 7) {
                    return board[index] == 1 && board[index - 1] == 1 && board[0] == 1 ||
                            board[index] == 1 && board[index + 8] == 1 && board[index + 16] == 1;
                } else {
                    return board[index] == 1 && board[index - 1] == 1 && board[index + 1] == 1 ||
                            board[index] == 1 && board[index + 8] == 1 && board[index + 16] == 1;
                }
            } else if (index < 16) {
                if (index == 15) {
                    return board[index] == 1 && board[index - 1] == 1 && board[8] == 1 ||
                            board[index] == 1 && board[index - 8] == 1 && board[index + 8] == 1;
                } else {
                    return board[index] == 1 && board[index - 1] == 1 && board[index + 1] == 1 ||
                            board[index] == 1 && board[index - 8] == 1 && board[index + 8] == 1;
                }
            } else {
                if (index == 23) {
                    return board[index] == 1 && board[index - 1] == 1 && board[15] == 1 ||
                            board[index] == 1 && board[index - 8] == 1 && board[index - 16] == 1;
                } else {
                    return board[index] == 1 && board[index - 1] == 1 && board[index + 1] == 1 ||
                            board[index] == 1 && board[index - 8] == 1 && board[index - 16] == 1;
                }
            }
        }

    }

    public boolean doesWhiteHaveMill(int index) {

        if (index % 2 == 0) {
            if (index == 0 || index == 8 || index == 16) {
                return board[index] == 2 && board[index + 1] == 2 && board[index + 2] == 2 ||
                        board[index] == 2 && board[index + 7] == 2 && board[index + 6] == 2;
            } else {
                return board[index] == 2 && board[index + 1] == 2 && board[index + 2] == 2 ||
                        board[index] == 2 && board[index - 1] == 2 && board[index - 2] == 2;
            }
        } else {
            if (index < 8) {
                if (index == 7) {
                    return board[index] == 2 && board[index - 1] == 2 && board[0] == 2 ||
                            board[index] == 2 && board[index + 8] == 2 && board[index + 16] == 2;
                } else {
                    return board[index] == 2 && board[index - 1] == 2 && board[index + 1] == 2 ||
                            board[index] == 2 && board[index + 8] == 2 && board[index + 16] == 2;
                }
            } else if (index < 16) {
                if (index == 15) {
                    return board[index] == 2 && board[index - 1] == 2 && board[8] == 2 ||
                            board[index] == 2 && board[index - 8] == 2 && board[index + 8] == 2;
                } else {
                    return board[index] == 2 && board[index - 1] == 2 && board[index + 1] == 2 ||
                            board[index] == 2 && board[index - 8] == 2 && board[index + 8] == 2;
                }
            } else {
                if (index == 23) {
                    return board[index] == 2 && board[index - 1] == 2 && board[15] == 2 ||
                            board[index] == 2 && board[index - 8] == 2 && board[index - 16] == 2;
                } else {
                    return board[index] == 2 && board[index - 1] == 2 && board[index + 1] == 2 ||
                            board[index] == 2 && board[index - 8] == 2 && board[index - 16] == 2;
                }
            }
        }

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
        state.swap(6, 26);
        System.out.println(state.toString());
    }
}
