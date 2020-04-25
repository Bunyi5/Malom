package malom.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GameResult {

    @Id
    @GeneratedValue()
    private Long id;

    private String player1;

    private int leftPiece1;

    private String player2;

    private int leftPiece2;

    private String winner;

    private ZonedDateTime date;

    @PrePersist
    protected void onPersist() {
        date = ZonedDateTime.now();
    }

}
