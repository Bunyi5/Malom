package malom.state;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MalomStateTest {

    private static final int FIRST_INDEX = 0;
    private static final int MIDDLE_INDEX = 12;
    private static final int LAST_INDEX = 23;
    private static final int BLACK_FIRST = 24;
    private static final int BLACK_MIDDLE = 28;
    private static final int BLACK_LAST = 32;
    private static final int WHITE_FIRST = 33;
    private static final int WHITE_MIDDLE = 37;
    private static final int WHITE_LAST = 41;
    private MalomState underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MalomState();
    }

    @Test
    void testIsBlackTurn() {
        Assertions.assertTrue(underTest.isBlackTurn());
        underTest.setBlackTurn(false);
        Assertions.assertFalse(underTest.isBlackTurn());
    }

    @Test
    void testGetColor() {
        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));
        underTest.swapPieceValues(BLACK_FIRST, FIRST_INDEX);
        Assertions.assertEquals(1, underTest.getColor(FIRST_INDEX));
        underTest.swapPieceValues(WHITE_FIRST, FIRST_INDEX);
        Assertions.assertEquals(2, underTest.getColor(FIRST_INDEX));

        Assertions.assertEquals(0, underTest.getColor(MIDDLE_INDEX));
        underTest.swapPieceValues(BLACK_MIDDLE, MIDDLE_INDEX);
        Assertions.assertEquals(1, underTest.getColor(MIDDLE_INDEX));
        underTest.swapPieceValues(WHITE_MIDDLE, MIDDLE_INDEX);
        Assertions.assertEquals(2, underTest.getColor(MIDDLE_INDEX));

        Assertions.assertEquals(0, underTest.getColor(LAST_INDEX));
        underTest.swapPieceValues(BLACK_LAST, LAST_INDEX);
        Assertions.assertEquals(1, underTest.getColor(LAST_INDEX));
        underTest.swapPieceValues(WHITE_LAST, LAST_INDEX);
        Assertions.assertEquals(2, underTest.getColor(LAST_INDEX));
    }

    @Test
    void testSwapPieceValuesSwapsPlacesOnBoard() {
        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));
        Assertions.assertEquals(0, underTest.getColor(MIDDLE_INDEX));
        underTest.swapPieceValues(FIRST_INDEX, MIDDLE_INDEX);
        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));
        Assertions.assertEquals(0, underTest.getColor(MIDDLE_INDEX));

        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));
        Assertions.assertEquals(1, underTest.getColor(BLACK_FIRST));
        underTest.swapPieceValues(BLACK_FIRST, FIRST_INDEX);
        Assertions.assertEquals(1, underTest.getColor(FIRST_INDEX));
        Assertions.assertEquals(0, underTest.getColor(BLACK_FIRST));

        Assertions.assertEquals(0, underTest.getColor(MIDDLE_INDEX));
        Assertions.assertEquals(2, underTest.getColor(WHITE_FIRST));
        underTest.swapPieceValues(WHITE_FIRST, MIDDLE_INDEX);
        Assertions.assertEquals(2, underTest.getColor(MIDDLE_INDEX));
        Assertions.assertEquals(0, underTest.getColor(WHITE_FIRST));

        Assertions.assertEquals(2, underTest.getColor(WHITE_MIDDLE));
        Assertions.assertEquals(1, underTest.getColor(BLACK_MIDDLE));
        underTest.swapPieceValues(BLACK_MIDDLE, WHITE_MIDDLE);
        Assertions.assertEquals(1, underTest.getColor(WHITE_MIDDLE));
        Assertions.assertEquals(2, underTest.getColor(BLACK_MIDDLE));
    }

    @Test
    void testRemovePiece() {
        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));
        underTest.removePiece(FIRST_INDEX);
        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));

        Assertions.assertEquals(1, underTest.getColor(BLACK_FIRST));
        underTest.removePiece(BLACK_FIRST);
        Assertions.assertEquals(0, underTest.getColor(BLACK_FIRST));

        Assertions.assertEquals(2, underTest.getColor(WHITE_FIRST));
        underTest.removePiece(WHITE_FIRST);
        Assertions.assertEquals(0, underTest.getColor(WHITE_FIRST));

        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));
        underTest.swapPieceValues(BLACK_MIDDLE, FIRST_INDEX);
        Assertions.assertEquals(1, underTest.getColor(FIRST_INDEX));
        underTest.removePiece(FIRST_INDEX);
        Assertions.assertEquals(0, underTest.getColor(FIRST_INDEX));
    }

    @Test
    void testBlackPieceNumCountsBlackPiecesOnBoard() {
        Assertions.assertEquals(0, underTest.blackPieceNum());
        underTest.swapPieceValues(BLACK_FIRST, FIRST_INDEX);
        Assertions.assertEquals(1, underTest.blackPieceNum());
        underTest.swapPieceValues(BLACK_MIDDLE, MIDDLE_INDEX);
        Assertions.assertEquals(2, underTest.blackPieceNum());
        underTest.swapPieceValues(BLACK_LAST, LAST_INDEX);
        Assertions.assertEquals(3, underTest.blackPieceNum());
    }

    @Test
    void testWhitePieceNumCountsWhitePiecesOnBoard() {
        Assertions.assertEquals(0, underTest.whitePieceNum());
        underTest.swapPieceValues(WHITE_FIRST, FIRST_INDEX);
        Assertions.assertEquals(1, underTest.whitePieceNum());
        underTest.swapPieceValues(WHITE_MIDDLE, MIDDLE_INDEX);
        Assertions.assertEquals(2, underTest.whitePieceNum());
        underTest.swapPieceValues(WHITE_LAST, LAST_INDEX);
        Assertions.assertEquals(3, underTest.whitePieceNum());
    }

    @Test
    void testIsThisColorNext() {
        Assertions.assertFalse(underTest.isThisColorNext(FIRST_INDEX));
        Assertions.assertFalse(underTest.isThisColorNext(WHITE_FIRST));
        Assertions.assertTrue(underTest.isThisColorNext(BLACK_FIRST));

        Assertions.assertFalse(underTest.isThisColorNext(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.isThisColorNext(WHITE_MIDDLE));
        Assertions.assertTrue(underTest.isThisColorNext(BLACK_MIDDLE));

        Assertions.assertFalse(underTest.isThisColorNext(LAST_INDEX));
        Assertions.assertFalse(underTest.isThisColorNext(WHITE_LAST));
        Assertions.assertTrue(underTest.isThisColorNext(BLACK_LAST));

        underTest.setBlackTurn(false);
        Assertions.assertFalse(underTest.isThisColorNext(FIRST_INDEX));
        Assertions.assertFalse(underTest.isThisColorNext(BLACK_FIRST));
        Assertions.assertTrue(underTest.isThisColorNext(WHITE_FIRST));

        Assertions.assertFalse(underTest.isThisColorNext(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.isThisColorNext(BLACK_MIDDLE));
        Assertions.assertTrue(underTest.isThisColorNext(WHITE_MIDDLE));

        Assertions.assertFalse(underTest.isThisColorNext(LAST_INDEX));
        Assertions.assertFalse(underTest.isThisColorNext(BLACK_LAST));
        Assertions.assertTrue(underTest.isThisColorNext(WHITE_LAST));
    }

    @Test
    void testCanItJumpAtGameStart() {
        Assertions.assertFalse(underTest.canItJump(FIRST_INDEX));
        Assertions.assertFalse(underTest.canItJump(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.canItJump(LAST_INDEX));

        Assertions.assertFalse(underTest.canItJump(BLACK_FIRST));
        Assertions.assertFalse(underTest.canItJump(BLACK_MIDDLE));
        Assertions.assertFalse(underTest.canItJump(BLACK_LAST));

        Assertions.assertFalse(underTest.canItJump(WHITE_FIRST));
        Assertions.assertFalse(underTest.canItJump(WHITE_MIDDLE));
        Assertions.assertFalse(underTest.canItJump(WHITE_LAST));
    }

    @ParameterizedTest
    @ValueSource(ints = {BLACK_FIRST, WHITE_FIRST})
    void testCanItJumpWhenBlackOrWhiteOnTheBoard(int colorIndex) {
        Assertions.assertFalse(underTest.canItJump(FIRST_INDEX));
        Assertions.assertFalse(underTest.canItJump(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.canItJump(LAST_INDEX));
        underTest.swapPieceValues(FIRST_INDEX, colorIndex);
        underTest.swapPieceValues(MIDDLE_INDEX, colorIndex + 1);
        underTest.swapPieceValues(LAST_INDEX, colorIndex + 2);
        Assertions.assertTrue(underTest.canItJump(FIRST_INDEX));
        Assertions.assertTrue(underTest.canItJump(MIDDLE_INDEX));
        Assertions.assertTrue(underTest.canItJump(LAST_INDEX));

        underTest.swapPieceValues(FIRST_INDEX + 1, colorIndex + 3);
        Assertions.assertFalse(underTest.canItJump(FIRST_INDEX));
        Assertions.assertFalse(underTest.canItJump(FIRST_INDEX + 1));
        Assertions.assertFalse(underTest.canItJump(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.canItJump(LAST_INDEX));
    }

    @Test
    void testIsAllPiecesWereOnBoard() {
        Assertions.assertFalse(underTest.isAllPiecesWereOnBoard());
        underTest.swapPieceValues(FIRST_INDEX, BLACK_FIRST);
        for (int i = BLACK_FIRST; i <= WHITE_LAST; i++) {
            underTest.removePiece(i);
        }
        Assertions.assertTrue(underTest.isAllPiecesWereOnBoard());
        underTest.swapPieceValues(FIRST_INDEX, BLACK_FIRST);
        Assertions.assertFalse(underTest.isAllPiecesWereOnBoard());
    }

    @Test
    void testIsNotOnBoardOrAllPiecesWereOnBoard() {
        Assertions.assertFalse(underTest.isNotOnBoardOrAllPiecesWereOnBoard(FIRST_INDEX));
        Assertions.assertFalse(underTest.isNotOnBoardOrAllPiecesWereOnBoard(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.isNotOnBoardOrAllPiecesWereOnBoard(LAST_INDEX));

        Assertions.assertTrue(underTest.isNotOnBoardOrAllPiecesWereOnBoard(BLACK_FIRST));
        Assertions.assertTrue(underTest.isNotOnBoardOrAllPiecesWereOnBoard(WHITE_FIRST));

        underTest.swapPieceValues(FIRST_INDEX, BLACK_FIRST);
        for (int i = BLACK_FIRST; i <= WHITE_LAST; i++) {
            underTest.removePiece(i);
        }
        Assertions.assertTrue(underTest.isNotOnBoardOrAllPiecesWereOnBoard(FIRST_INDEX));
        underTest.swapPieceValues(FIRST_INDEX, BLACK_FIRST);
        Assertions.assertFalse(underTest.isNotOnBoardOrAllPiecesWereOnBoard(FIRST_INDEX));
    }

    @Test
    void testCanRemovePieceFromTheOtherPlayer() {
        Assertions.assertFalse(underTest.canRemovePieceFromTheOtherPlayer(FIRST_INDEX));
        Assertions.assertFalse(underTest.canRemovePieceFromTheOtherPlayer(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.canRemovePieceFromTheOtherPlayer(LAST_INDEX));

        underTest.swapPieceValues(FIRST_INDEX, BLACK_FIRST);
        Assertions.assertFalse(underTest.canRemovePieceFromTheOtherPlayer(FIRST_INDEX));
        underTest.swapPieceValues(FIRST_INDEX + 1, BLACK_MIDDLE);
        underTest.swapPieceValues(FIRST_INDEX + 2, BLACK_LAST);
        Assertions.assertFalse(underTest.canRemovePieceFromTheOtherPlayer(FIRST_INDEX));

        underTest.swapPieceValues(MIDDLE_INDEX, WHITE_FIRST);
        Assertions.assertTrue(underTest.canRemovePieceFromTheOtherPlayer(FIRST_INDEX));
        Assertions.assertFalse(underTest.canRemovePieceFromTheOtherPlayer(MIDDLE_INDEX));

        underTest.removePiece(FIRST_INDEX);
        Assertions.assertTrue(underTest.canRemovePieceFromTheOtherPlayer(MIDDLE_INDEX));
        underTest.removePiece(FIRST_INDEX + 1);
        underTest.removePiece(FIRST_INDEX + 2);
        Assertions.assertFalse(underTest.canRemovePieceFromTheOtherPlayer(MIDDLE_INDEX));
    }

    @ParameterizedTest
    @MethodSource("provideColorIndexesForTestCanThePlayerMove")
    void testCanThePlayerMoveInHisTurnWhenThePlayerIsBlackOrWhite(int colorIndex1, int colorIndex2, boolean blackTurn) {
        underTest.setBlackTurn(blackTurn);
        Assertions.assertTrue(underTest.canThePlayerMoveInHisTurn());

        underTest.swapPieceValues(2, colorIndex1);
        underTest.swapPieceValues(1, colorIndex2);
        underTest.swapPieceValues(4, colorIndex2 + 1);
        for (int i = BLACK_FIRST; i <= WHITE_LAST; i++) {
            underTest.removePiece(i);
        }

        Assertions.assertTrue(underTest.canThePlayerMoveInHisTurn());
        underTest.swapPieceValues(3, 4);
        Assertions.assertFalse(underTest.canThePlayerMoveInHisTurn());
    }

    private static Stream<Arguments> provideColorIndexesForTestCanThePlayerMove() {
        return Stream.of(
                Arguments.of(BLACK_FIRST, WHITE_FIRST, true),
                Arguments.of(WHITE_FIRST, BLACK_FIRST, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideListToTestWhereCanThePieceMove")
    void testWhereCanThePieceMoveReturnsTheCorrectList(int who, List<Integer> expected) {
        Assertions.assertEquals(expected, underTest.whereCanThePieceMove(who));
    }

    private static Stream<Arguments> provideListToTestWhereCanThePieceMove() {
        return Stream.of(
                Arguments.of(1, Arrays.asList(0, 9, 2)),
                Arguments.of(2, Arrays.asList(3, 1)),
                Arguments.of(9, Arrays.asList(8, 17, 1, 10)),
                Arguments.of(10, Arrays.asList(11, 9)),
                Arguments.of(17, Arrays.asList(16, 9, 18)),
                Arguments.of(18, Arrays.asList(19, 17)),
                Arguments.of(0, Arrays.asList(1, 7)),
                Arguments.of(7, Arrays.asList(6, 15, 0)),
                Arguments.of(8, Arrays.asList(9, 15)),
                Arguments.of(15, Arrays.asList(14, 23, 7, 8)),
                Arguments.of(16, Arrays.asList(17, 23)),
                Arguments.of(23, Arrays.asList(22, 15, 16))
        );
    }

    @ParameterizedTest
    @MethodSource("provideIndexesForTestingIsSomeoneMill")
    void testIsSomeoneHasMill(int colorIndex, int who, int next1, int next2, int next3, int next4) {
        underTest.swapPieceValues(who, colorIndex);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.swapPieceValues(next1, colorIndex + 1);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.swapPieceValues(next2, colorIndex + 2);
        Assertions.assertTrue(underTest.isSomeoneHasMill(who));

        underTest.removePiece(who);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.removePiece(next1);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.removePiece(next2);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));

        underTest.swapPieceValues(who, colorIndex + 3);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.swapPieceValues(next3, colorIndex + 4);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.swapPieceValues(next4, colorIndex + 5);
        Assertions.assertTrue(underTest.isSomeoneHasMill(who));

        underTest.removePiece(who);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.removePiece(next3);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
        underTest.removePiece(next4);
        Assertions.assertFalse(underTest.isSomeoneHasMill(who));
    }

    private static Stream<Arguments> provideIndexesForTestingIsSomeoneMill() {
        return Stream.of(
                Arguments.of(BLACK_FIRST, 2, 1, 0, 3, 4), //Normal cases
                Arguments.of(WHITE_FIRST, 2, 1, 0, 3, 4),
                Arguments.of(BLACK_FIRST, 1, 0, 2, 9, 17),
                Arguments.of(WHITE_FIRST, 1, 0, 2, 9, 17),
                Arguments.of(BLACK_FIRST, 9, 8, 10, 1, 17),
                Arguments.of(WHITE_FIRST, 9, 8, 10, 1, 17),
                Arguments.of(BLACK_FIRST, 17, 16, 18, 9, 1),
                Arguments.of(WHITE_FIRST, 17, 16, 18, 9, 1),
                Arguments.of(BLACK_FIRST, 0, 1, 2, 7, 6), //Special cases
                Arguments.of(WHITE_FIRST, 0, 1, 2, 7, 6),
                Arguments.of(BLACK_FIRST, 8, 9, 10, 15, 14),
                Arguments.of(WHITE_FIRST, 8, 9, 10, 15, 14),
                Arguments.of(BLACK_FIRST, 16, 17, 18, 23, 22),
                Arguments.of(WHITE_FIRST, 16, 17, 18, 23, 22),
                Arguments.of(BLACK_FIRST, 6, 7, 0, 5, 4),
                Arguments.of(WHITE_FIRST, 6, 7, 0, 5, 4),
                Arguments.of(BLACK_FIRST, 14, 15, 8, 13, 12),
                Arguments.of(WHITE_FIRST, 14, 15, 8, 13, 12),
                Arguments.of(BLACK_FIRST, 22, 23, 16, 21, 20),
                Arguments.of(WHITE_FIRST, 22, 23, 16, 21, 20),
                Arguments.of(BLACK_FIRST, 7, 6, 0, 15, 23),
                Arguments.of(WHITE_FIRST, 7, 6, 0, 15, 23),
                Arguments.of(BLACK_FIRST, 15, 14, 8, 7, 23),
                Arguments.of(WHITE_FIRST, 15, 14, 8, 7, 23),
                Arguments.of(BLACK_FIRST, 23, 22, 16, 15, 7),
                Arguments.of(WHITE_FIRST, 23, 22, 16, 15, 7)
        );
    }

    @Test
    void testIsGameEndedAtGameStart() {
        Assertions.assertFalse(underTest.isGameEnded(FIRST_INDEX));
        Assertions.assertFalse(underTest.isGameEnded(MIDDLE_INDEX));
        Assertions.assertFalse(underTest.isGameEnded(LAST_INDEX));
    }

    @ParameterizedTest
    @MethodSource("provideColorIndexesToTestIsGameEnded")
    void testIsGameEndedWhenBlackWinsOrWhiteWins(int colorIndex1, int colorIndex2) {
        underTest.swapPieceValues(FIRST_INDEX, colorIndex1);
        underTest.swapPieceValues(MIDDLE_INDEX, colorIndex1 + 1);
        underTest.swapPieceValues(LAST_INDEX, colorIndex1 + 2);
        underTest.swapPieceValues(FIRST_INDEX + 1, colorIndex1 + 3);
        underTest.swapPieceValues(8, colorIndex2);
        underTest.swapPieceValues(9, colorIndex2 + 1);
        underTest.swapPieceValues(10, colorIndex2 + 2);

        Assertions.assertFalse(underTest.isGameEnded(8));
        Assertions.assertFalse(underTest.isGameEnded(9));
        Assertions.assertFalse(underTest.isGameEnded(10));

        for (int i = BLACK_FIRST; i <= WHITE_LAST; i++) {
            underTest.removePiece(i);
        }

        Assertions.assertFalse(underTest.isGameEnded(8));
        Assertions.assertFalse(underTest.isGameEnded(9));
        Assertions.assertFalse(underTest.isGameEnded(10));

        underTest.removePiece(FIRST_INDEX + 1);

        Assertions.assertTrue(underTest.isGameEnded(8));
        Assertions.assertTrue(underTest.isGameEnded(9));
        Assertions.assertTrue(underTest.isGameEnded(10));
    }

    private static Stream<Arguments> provideColorIndexesToTestIsGameEnded() {
        return Stream.of(
                Arguments.of(BLACK_FIRST, WHITE_FIRST),
                Arguments.of(WHITE_FIRST, BLACK_FIRST)
        );
    }

    @Test
    void testToString() {
        String expected = "0 0 0 0 0 0 0 0 \n" +
                "0 0 0 0 0 0 0 0 \n" +
                "0 0 0 0 0 0 0 0 \n" +
                "1 1 1 1 1 1 1 1 1 \n" +
                "2 2 2 2 2 2 2 2 2 \n";

        Assertions.assertEquals(expected, underTest.toString());

        underTest.swapPieceValues(BLACK_FIRST, FIRST_INDEX);
        underTest.swapPieceValues(WHITE_MIDDLE, MIDDLE_INDEX);
        underTest.swapPieceValues(BLACK_LAST, LAST_INDEX);

        expected = "1 0 0 0 0 0 0 0 \n" +
                "0 0 0 0 2 0 0 0 \n" +
                "0 0 0 0 0 0 0 1 \n" +
                "0 1 1 1 1 1 1 1 0 \n" +
                "2 2 2 2 0 2 2 2 2 \n";

        Assertions.assertEquals(expected, underTest.toString());
    }

    @AfterEach
    public void tearDown() {
        underTest = null;
    }
}
