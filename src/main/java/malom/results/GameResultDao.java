package malom.results;

import util.jpa.GenericJpaDao;

import javax.persistence.Persistence;
import java.util.List;

/**
 * DAO class for the {@link GameResult} entity.
 */
public class GameResultDao extends GenericJpaDao<GameResult> {

    private static GameResultDao instance;

    private GameResultDao() {
        super(GameResult.class);
    }

    /**
     * Returns and creates if it's null an instance of the {@link GameResultDao} class.
     *
     * @return an instance of the {@link GameResultDao} class
     */
    public static GameResultDao getInstance() {
        if (instance == null) {
            instance = new GameResultDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("jpa-persistence-unit-1").createEntityManager());
        }
        return instance;
    }

    /**
     * Returns a list of {@code n} results in descending order by the date.
     *
     * @param n the maximum number of results to be returned
     * @return returns a list of {@code n} results in descending order by the date
     */
    public List<GameResult> findEarliest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r ORDER BY r.date DESC", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }

}
