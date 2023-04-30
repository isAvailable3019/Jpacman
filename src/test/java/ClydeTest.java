import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.GhostMapParser;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * This test class tests the behavior of the Clyde class.
 */
public class ClydeTest {

    static GhostMapParser ghostMapParser;
    static GhostFactory ghostFactory;
    static PlayerFactory playerFactory;

    /**
     * Initializes the required classes and objects before running the tests.
     */
    @BeforeAll
    static void init(){
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        ghostFactory =  new GhostFactory(sprites);
        playerFactory = new PlayerFactory(sprites);
        ghostMapParser = new GhostMapParser(levelFactory, new BoardFactory(sprites), ghostFactory);
    }

    /**
     * Tests if Clyde does not move if there is no available path.
     */
    @Test
    void noPath(){
        Board b = ghostMapParser
            .parseMap(Lists.newArrayList("P#C #"))
            .getBoard();
        Square to = b.squareAt(0, 0);
        Square from = b.squareAt(2, 0);
        Ghost ghost = ghostFactory.createClyde();
        ghost.occupy(from);
        Player player = playerFactory.createPacMan();
        player.occupy(to);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).isEmpty();
    }

    /**
     * Tests if Clyde does not move if there is no player available.
     */
    @Test
    void noPlayer(){
        Board b = ghostMapParser
            .parseMap(Lists.newArrayList("          C           "))
            .getBoard();
        Square from = b.squareAt(10, 0);
        Ghost ghost = ghostFactory.createClyde();
        ghost.occupy(from);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).isEmpty();
    }

    /**
     * Tests if Clyde moves away from the player if the distance between Clyde and the player is less than or equal to 8.
     */
    @Test
    void nearThanShyness(){
        Board b = ghostMapParser
            .parseMap(Lists.newArrayList("PC "))
            .getBoard();
        Square to = b.squareAt(0, 0);
        Square from = b.squareAt(1, 0);
        Ghost ghost = ghostFactory.createClyde();
        ghost.occupy(from);
        Player player = playerFactory.createPacMan();
        player.occupy(to);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).contains(Direction.EAST);
    }

    /**
     * Tests if Clyde moves towards the player if the distance between Clyde and the player is greater than 8.
     */
    @Test
    void farThanShyness(){
        Board b = ghostMapParser
            .parseMap(Lists.newArrayList("P          C           "))
            .getBoard();
        Square to = b.squareAt(0, 0);
        Square from = b.squareAt(11, 0);
        Ghost ghost = ghostFactory.createClyde();
        ghost.occupy(from);
        Player player = playerFactory.createPacMan();
        player.occupy(to);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).contains(Direction.WEST);
    }
}



