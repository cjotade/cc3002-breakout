import controller.Game;
import logic.level.Level;
import logic.level.RealLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game defaultGame;
    private Game game;
    private Level realLevel;
    private final int seed = 0;

    @Before
    public  void setUp(){
        defaultGame = new Game(3);
        game = new Game(5);
        realLevel = new RealLevel("table",5,0.5,5,5);
    }

    @Test
    public void generalTest(){
        assertEquals(5, game.getBallsLeft());
        assertEquals(3,defaultGame.getBallsLeft());
        assertEquals(0,defaultGame.getCurrentPoints());
        assertEquals(0,defaultGame.getCurrentPoints());
    }

    @Test
    public void GameOverTest(){
        String name = "Level with bricks full";
        int numberOfBricks = 20;
        Level levelFullBricks = game.newLevelWithBricksFull(name, numberOfBricks, 0.5, 0.5, seed);

        game.setCurrentLevel(levelFullBricks);
        int inBalls = game.getBallsLeft();
        game.dropBall();
        repeat(inBalls,game::dropBall);
        assertTrue(game.isGameOver());

    }
    private void repeat(int n, Runnable action) {
        IntStream.range(0, n).forEach(i -> action.run());
    }
}
