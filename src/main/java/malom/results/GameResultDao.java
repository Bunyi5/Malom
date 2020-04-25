package malom.results;

import util.jpa.GenericJpaDao;

import javax.persistence.Persistence;
import java.util.List;

public class GameResultDao extends GenericJpaDao<GameResult> {

    private static GameResultDao instance;

    private GameResultDao() {
        super(GameResult.class);
    }

    public static GameResultDao getInstance() {
        if (instance == null) {
            instance = new GameResultDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("jpa-persistence-unit-1").createEntityManager());
        }
        return instance;
    }

    public List<GameResult> findEarliest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r ORDER BY r.date DESC", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }
}
