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
 * Test class for the Inky ghost behavior.
 */
public class InkyTest {

    static GhostMapParser ghostMapParser;
    static GhostFactory ghostFactory;
    static PlayerFactory playerFactory;

    /**
     * Initializes the required objects before running the tests.
     */
    @BeforeAll
    static void init() {
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        ghostFactory = new GhostFactory(sprites);
        playerFactory = new PlayerFactory(sprites);
        ghostMapParser = new GhostMapParser(levelFactory, new BoardFactory(sprites), ghostFactory);
    }

    /**
     * Test case to verify that Inky does not move if Blinky is not present.
     */
    @Test
    void noBlinky() {
        Board b = ghostMapParser.parseMap(Lists.newArrayList("PI")).getBoard();
        Square to = b.squareAt(0, 0);
        Square from = b.squareAt(1, 0);
        Ghost ghost = ghostFactory.createInky();
        ghost.occupy(from);
        Player player = playerFactory.createPacMan();
        player.occupy(to);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).isEmpty();
    }

    /**
     * Test case to verify that Inky does not move if the player is not present.
     */
    @Test
    void noPlayer() {
        Board b = ghostMapParser.parseMap(Lists.newArrayList(" IB ")).getBoard();
        Square from = b.squareAt(2, 0);
        Ghost ghost = ghostFactory.createInky();
        ghost.occupy(from);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).isEmpty();
    }

    /**
     * Test case to verify that Inky does not move if there is no path to the player.
     */
    @Test
    void noPath() {
        Board b = ghostMapParser.parseMap(Lists.newArrayList("P###IB### ")).getBoard();
        Square to = b.squareAt(0, 0);
        Square from = b.squareAt(4, 0);
        Ghost ghost = ghostFactory.createInky();
        ghost.occupy(from);
        Player player = playerFactory.createPacMan();
        player.occupy(to);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).isEmpty();
    }

    /**
     * Test case to verify that Inky follows Blinky.
     */
    @Test
    void followBlink() {
        Board b = ghostMapParser.parseMap(Lists.newArrayList("P IB ")).getBoard();
        Square to = b.squareAt(0, 0);
        Square from = b.squareAt(3, 0);
        Ghost ghost = ghostFactory.createInky();
        ghost.occupy(from);
        Player player = playerFactory.createPacMan();
        player.occupy(to);
        Optional<Direction> direction = ghost.nextAiMove();
        assertThat(direction).contains(Direction.WEST);
    }
}
