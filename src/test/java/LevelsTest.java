import controller.Game;
import facade.HomeworkTwoFacade;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.Level;
import logic.level.NullLevel;
import logic.level.RealLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class LevelsTest {
    private HomeworkTwoFacade hw2;

    private final int seed = 0;

    @Before
    public void setUp() {
        hw2 = new HomeworkTwoFacade();
    }

    @Test
    public void AddLevelTest() {
        List<Brick> brickList = new ArrayList<>();
        NullLevel endLevel = new NullLevel();
        RealLevel addfourthLevel = new RealLevel("This is the fourth Level",brickList);
        RealLevel thirdLevel = new RealLevel("This is the third Level",brickList,endLevel);
        RealLevel secondLevel = new RealLevel("This is the second Level",brickList);
        RealLevel firstLevel = new RealLevel("This is the first Level",brickList);
        NullLevel nullLevel = new NullLevel();

        assertEquals("",firstLevel.getNextLevel().getName());
        assertTrue(firstLevel.isPlayableLevel());
        assertFalse(firstLevel.getNextLevel().isPlayableLevel());
        assertFalse(endLevel.getNextLevel().isPlayableLevel());
        assertFalse(endLevel.hasNextLevel());

        hw2.setCurrentLevel(firstLevel);

        hw2.addPlayingLevel(secondLevel);
        assertEquals("This is the second Level",hw2.getCurrentLevel().getNextLevel().getName());

        firstLevel.addPlayingLevel(thirdLevel);
        assertEquals("This is the third Level",firstLevel.getNextLevel().getNextLevel().getName());
        assertFalse(thirdLevel.hasNextLevel());

        secondLevel.addPlayingLevel(addfourthLevel);
        assertEquals("This is the fourth Level",secondLevel.getNextLevel().getNextLevel().getName());

        nullLevel.addPlayingLevel(firstLevel);
        nullLevel.setNextLevel(firstLevel);
        assertFalse(nullLevel.isPlayableLevel());
        assertFalse(nullLevel.getNextLevel().isPlayableLevel());
    }

    @Test
    public void GlassBrickHitTest(){
        String name = "Level Only Glass";
        int numberOfBricks = 20;

        Level levelOnlyGlass = hw2.newLevelWithBricksNoMetal(name,numberOfBricks,1, seed);
        int levelPointsOnlyGrass = levelOnlyGlass.getPoints();
        int numBricks = levelOnlyGlass.getNumberOfBricks();
        assertEquals(numberOfBricks,numBricks);

        hw2.setCurrentLevel(levelOnlyGlass);
        assertEquals(hw2.getLevelName(), name);
        assertTrue(hw2.hasCurrentLevel());
        assertFalse(hw2.hasNextLevel());

        for(Brick brick : levelOnlyGlass.getBricks()){
            brick.hit();
            assertEquals(brick.getScore(),50);
        }
        assertEquals(hw2.getCurrentPoints(),levelPointsOnlyGrass);
        assertFalse(hw2.getCurrentLevel().isPlayableLevel());
        assertTrue(hw2.winner());
    }

    @Test
    public void WoodenBrickHitTest(){
        String name = "Level Only Wooden";
        int numberOfBricks = 20;
        Level levelOnlyWooden = hw2.newLevelWithBricksNoMetal(name,numberOfBricks,0, seed);
        hw2.setCurrentLevel(levelOnlyWooden);
        assertEquals(hw2.getLevelPoints(),numberOfBricks*200);
        assertTrue(hw2.hasCurrentLevel());
        assertEquals(hw2.getLevelName(), name);
        assertEquals(hw2.numberOfBricks(),numberOfBricks);

        for(Brick brick : levelOnlyWooden.getBricks()){
            brick.hit();
            repeat(3,brick::hit);
            assertEquals(brick.getScore(),200);
        }


        assertEquals(hw2.getCurrentPoints(),numberOfBricks*200);
        assertTrue(hw2.winner());
    }

    @Test
    public void FullBricksHitTest(){
        String name = "Level with bricks full";
        int numberOfBricks = 20;
        Level levelFullBricks = hw2.newLevelWithBricksFull(name, numberOfBricks, 0.5, 0.5, seed);

        hw2.setCurrentLevel(levelFullBricks);
        assertTrue(hw2.hasCurrentLevel());
        assertEquals(hw2.getLevelName(), name);

        int inBalls = hw2.getBallsLeft();

        long numberOfGlass = hw2.getCurrentLevel().getBricks()
                .stream()
                .filter(brick -> brick instanceof GlassBrick)
                .count();
        long numberOfWood = hw2.getCurrentLevel().getBricks()
                .stream()
                .filter(brick -> brick instanceof WoodenBrick)
                .count();
        long numberOfMetal = hw2.getCurrentLevel().getBricks()
                .stream()
                .filter(brick -> brick instanceof MetalBrick)
                .count();

        for(Brick brick : levelFullBricks.getBricks()){
            brick.hit();
            repeat(10,brick::hit);
        }

        assertEquals(hw2.getCurrentPoints(),50*numberOfGlass+200*numberOfWood);
        assertEquals(hw2.getBallsLeft(),inBalls + numberOfMetal);
        assertTrue(hw2.winner());
    }

    /*
    @Test
    public void spotTargetHitTest(){
        game.setTable(fullTable);

        for(Target target :game.getGameTable().getSpotTargetList()){
            assertEquals(true,target.isActive());
        }

        for(Target target :game.getGameTable().getSpotTargetList()){
            target.hit();
        }
        assertEquals(game.getScore(),5*100000);
        for(Target target :game.getGameTable().getSpotTargetList()){
            assertEquals(false,target.isActive());
        }
    }

    @Test
    public void upgradeBumpersTest(){
        game.setTable(fullTable);

        for(Bumper bumper:game.getGameTable().getBumpers()){
            assertEquals(false,bumper.isUpgraded());
        }
        game.getGameTable().upgradeAllBumpers();

        for(Bumper bumper:game.getGameTable().getBumpers()){
            assertEquals(true,bumper.isUpgraded());
        }
    }

    @Test
    public void playableTableTest(){
        assertFalse(emptyTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        game.setTable(fullTable);
        List<Target> asdasd = game.getGameTable().getTargets();
    }
    */

    private void repeat(int n, Runnable action) {
        IntStream.range(0, n).forEach(i -> action.run());
    }
}

