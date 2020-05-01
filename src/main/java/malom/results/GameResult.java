package malom.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Class representing the result of a game played by 2 players.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GameResult {

    @Id
    @GeneratedValue()
    private Long id;

    /**
     * The name of player 1.
     */
    private String player1;

    /**
     * The number of pieces that left by player 1.
     */
    private int leftPiece1;

    /**
     * The name of player 2.
     */
    private String player2;

    /**
     * The number of pieces that left by player 2.
     */
    private int leftPiece2;

    /**
     * The name of the game winner.
     */
    private String winner;

    /**
     * The timestamp when the result was saved.
     */
    private ZonedDateTime date;

    @PrePersist
    protected void onPersist() {
        date = ZonedDateTime.now();
    }

}
