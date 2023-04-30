import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MapParserTest {
    MapParser parser;
    @BeforeEach
    void init() {
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        parser = new MapParser(levelFactory, new BoardFactory(sprites));
    }

    @Test
    void createMap1(){
        char [][]c = {{'G', ' '}, {' ', ' '}};
        Level level =parser.parseMap(c);
        assertThat(level.getBoard().squareAt(0,0).getOccupants().size()).isEqualTo(1);
    }

    @Test
    void createMap2(){
        Level level =parser.parseMap(List.of("G", " "));
        assertThat(level.getBoard().squareAt(0,0).getOccupants().size()).isEqualTo(1);
    }

    @Test
    void createMap3(){
        Level level = null;
        try {
            level = parser.parseMap("/board.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(level.getBoard().squareAt(0,0).getOccupants().size()).isEqualTo(0);
    }

    @Test
    void createMap4(){
        try {
            InputStream is =  getClass().getResourceAsStream("/board.txt");
            Level level =parser.parseMap(is);
            assertThat(level.getBoard().squareAt(0,0).getOccupants().size()).isEqualTo(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void invalidChar(){
        char [][]c = {{'a', ' '}, {' ', ' '}};
        try {
            Level level =parser.parseMap(c);
        }catch (PacmanConfigurationException e){
            assertThat(e.toString()).isEqualTo("nl.tudelft.jpacman.PacmanConfigurationException: Invalid character at 0,0: a");
        }
    }

    @Test
    void zeroRowInputText(){
        try {
            Level level = parser.parseMap(List.of());
            assertThat(level.getBoard().squareAt(0, 0).getOccupants().size()).isEqualTo(1);
        }catch (PacmanConfigurationException e){
            assertThat(e.toString()).isEqualTo("nl.tudelft.jpacman.PacmanConfigurationException: Input text must consist of at least 1 row.");
        }
    }

    @Test
    void nullInputText(){
        try {
            Level level = parser.parseMap((List<String>) null);
            assertThat(level.getBoard().squareAt(0, 0).getOccupants().size()).isEqualTo(1);
        }catch (PacmanConfigurationException e){
            assertThat(e.toString()).isEqualTo("nl.tudelft.jpacman.PacmanConfigurationException: Input text cannot be null.");
        }
    }

    @Test
    void notOfEqualWidth(){
        try {
            Level level = parser.parseMap(List.of(" ", "  "));
            assertThat(level.getBoard().squareAt(0, 0).getOccupants().size()).isEqualTo(1);
        }catch (PacmanConfigurationException e){
            assertThat(e.toString()).isEqualTo("nl.tudelft.jpacman.PacmanConfigurationException: Input text lines are not of equal width.");
        }
    }

    @Test
    void emptyLine(){
        try {
            Level level = parser.parseMap(List.of("", ""));
            assertThat(level.getBoard().squareAt(0, 0).getOccupants().size()).isEqualTo(1);
        }catch (PacmanConfigurationException e){
            assertThat(e.toString()).isEqualTo("nl.tudelft.jpacman.PacmanConfigurationException: Input text lines cannot be empty.");
        }
    }

}
