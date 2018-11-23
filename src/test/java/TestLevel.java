import logic.brick.Brick;
import logic.level.Level;
import logic.level.NullLevel;
import logic.level.RealLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestLevel {
    private RealLevel firstLevel;
    private RealLevel secondLevel;
    private RealLevel thirdLevel;
    private RealLevel addfourthLevel;
    private NullLevel endLevel;
    private NullLevel beginLevel;
    private List<Brick> brickList;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void TestAddLevel() {
        List<Level> linkedLevel = new ArrayList<>();
        brickList = new ArrayList<>();
        NullLevel beginLevel = new NullLevel();
        NullLevel endLevel = new NullLevel();
        RealLevel addfourthLevel = new RealLevel("This is the fourth Level",brickList);
        RealLevel thirdLevel = new RealLevel("This is the third Level",brickList,endLevel);
        RealLevel secondLevel = new RealLevel("This is the second Level",brickList);
        RealLevel firstLevel = new RealLevel("This is the first Level",brickList);


        assertEquals("",firstLevel.getNextLevel().getName());
        firstLevel.addPlayingLevel(secondLevel);
        assertEquals("This is the second Level",firstLevel.getNextLevel().getName());
        firstLevel.addPlayingLevel(thirdLevel);
        assertEquals("This is the third Level",firstLevel.getNextLevel().getNextLevel().getName());
        assertFalse(thirdLevel.hasNextLevel());
        thirdLevel.addPlayingLevel(addfourthLevel);
        assertEquals("This is the fourth Level",secondLevel.getNextLevel().getNextLevel().getName());


    }
}

