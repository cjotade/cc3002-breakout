import controller.Game;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class BrickTest {
    GlassBrick glassBrick;
    WoodenBrick woodenBrick;
    MetalBrick metalBrick;
    Game game ;

    @Before
    public void setUp(){
        int initialBalls = 3;
        game = new Game(initialBalls);
        glassBrick = new GlassBrick();
        woodenBrick = new WoodenBrick();
        metalBrick = new MetalBrick();
        glassBrick.subscribeGameObserver(game);
        woodenBrick.subscribeGameObserver(game);
        metalBrick.subscribeGameObserver(game);
    }

    @Test
    public void hitTest(){
        glassBrick.hit();
        woodenBrick.hit();
        metalBrick.hit();
        repeat(5,metalBrick::hit);
        assertEquals(glassBrick.remainingHits(),0);
        assertEquals(woodenBrick.remainingHits(),2);
        assertEquals(metalBrick.remainingHits(),4);
        assertEquals(game.getCurrentPoints(),50);

        woodenBrick.hit();
        repeat(8, woodenBrick::hit);
        assertEquals(woodenBrick.remainingHits(),0);
        assertEquals(game.getCurrentPoints(),250);

    }

    @Test
    public void extraBallTest(){
        int inBalls = game.getBallsLeft();
        metalBrick.hit();
        repeat(10,metalBrick::hit);
        assertEquals(game.getCurrentPoints(),0);
        assertEquals(game.getBallsLeft(),inBalls+1);
    }

    private void repeat(int n, Runnable action) {
        IntStream.range(0, n).forEach(i -> action.run());
    }
}
