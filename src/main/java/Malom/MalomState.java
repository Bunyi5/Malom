package Malom;

public class MalomState {

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
