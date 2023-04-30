import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectionTest {
    Launcher launcher;
    Game game;
    Player player;
    {
        launcher = new Launcher();
        launcher.launch();
        game = launcher.getGame();
        player = game.getPlayers().get(0);
        game.start();
    }

    @Test
    void eastTest(){
        game.move(player, Direction.EAST);
        game.stop();
        assertThat(player.getScore()).isEqualTo(10);
    }

    @Test
    void westTest(){
        game.move(player, Direction.WEST);
        game.stop();
        assertThat(player.getScore()).isEqualTo(10);
    }

    @Test
    void northTest(){
        game.move(player, Direction.NORTH);
        game.stop();
        assertThat(player.getScore()).isEqualTo(0);
    }

    @Test
    void southTest(){
        game.move(player, Direction.SOUTH);
        game.stop();
        assertThat(player.getScore()).isEqualTo(0);
    }
}
