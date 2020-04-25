package malom.results;

public class GameResultTest {

    public static void main(String[] args) {
        GameResultDao gameResultDao = GameResultDao.getInstance();
        GameResult gameResult = GameResult.builder()
                .player1("Péter")
                .leftPiece1(5)
                .player2("János")
                .leftPiece2(3)
                .winner("Péter")
                .build();
        gameResultDao.persist(gameResult);
        System.out.println(gameResult);
        System.out.println(gameResultDao.findEarliest(5));
    }

}

