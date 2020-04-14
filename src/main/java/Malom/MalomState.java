package Malom;


import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

public class MalomState {

    @Getter
    private boolean stage2 = false;

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

    public int blackPieceNum() {
        int num = 0;
        for (int i = 0; i < 24; i++) {
            if (board[i] == 1) {
                num++;
            }
        }
        return num;
    }

    public int whitePieceNum() {
        int num = 0;
        for (int i = 0; i < 24; i++) {
            if (board[i] == 2) {
                num++;
            }
        }
        return num;
    }

    public void isStage2Started() {
        int num = 18;
        for (int i = 24; i < board.length; i++) {
            if (board[i] == 0) {
                num--;
            }
        }
        stage2 = num == 0;
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
