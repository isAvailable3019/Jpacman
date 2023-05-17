import nl.tudelft.jpacman.level.CollisionMap;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerCollisions;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * A test suite for the Collider class, which tests the collision behavior between
 * different game objects (Player, Ghost, Pellet) using a CollisionMap.
 */
public class ColliderTest {

  private CollisionMap collisions;

  /**
   * Set up the environment before each test by initializing the CollisionMap object.
   */
  @BeforeEach
  void init() {
    collisions = new PlayerCollisions(new PointCalculatorLoader().load());
  }

  /**
   * Test the collision behavior between a Player and a Ghost.
   * The expected result is that the Player is no longer alive.
   */
  @Test
  void playerToGhost() {
    Ghost ghost = Mockito.mock(Ghost.class);
    Player player = Mockito.mock(Player.class);
    collisions.collide(player, ghost);
    Assertions.assertThat(player.isAlive()).isFalse();
  }

  /**
   * Test the collision behavior between a Player and a Pellet.
   * The expected result is that the Player's score is increased by the Pellet's value.
   */
  @Test
  void playerToPellet() {
    Pellet pellet = new Pellet(10, Mockito.mock(Sprite.class));
    Player player = new PlayerFactory(new PacManSprites()).createPacMan();
    collisions.collide(player, pellet);
    Assertions.assertThat(player.getScore()).isEqualTo(10);
  }

  /**
   * Test the collision behavior between a Player and null (nothing).
   * The expected result is that the Player's score is unchanged.
   */
  @Test
  void playerToNothing() {
    Player player = Mockito.mock(Player.class);
    collisions.collide(player, null);
    Assertions.assertThat(player.getScore()).isEqualTo(0);
  }

  /**
   * Test the collision behavior between a Ghost and a Player.
   * The expected result is that the Player is no longer alive.
   */
  @Test
  void ghostToPlayer() {
    Ghost ghost = Mockito.mock(Ghost.class);
    Player player = Mockito.mock(Player.class);
    collisions.collide(ghost, player);
    Assertions.assertThat(player.isAlive()).isFalse();
  }

  /**
   * Test the collision behavior between a Pellet and a Player.
   * The expected result is that the Player's score is increased by the Pellet's value.
   */
  @Test
  void pelletToPlayer() {
    Pellet pellet = new Pellet(10, Mockito.mock(Sprite.class));
    Player player = new PlayerFactory(new PacManSprites()).createPacMan();
    collisions.collide(pellet, player);
    Assertions.assertThat(player.getScore()).isEqualTo(10);
  }

}
