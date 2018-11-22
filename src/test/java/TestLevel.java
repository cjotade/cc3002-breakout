import logic.brick.Brick;
import logic.level.Level;
import logic.level.LinkedLevels;
import logic.level.NullLevel;
import logic.level.realLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TestLevel {
    private realLevel firstLevel;
    private realLevel secondLevel;
    private realLevel thirdLevel;
    private realLevel addfourthLevel;
    private NullLevel endLevel;
    private NullLevel beginLevel;
    private List<Brick> brickList;

    @Before
    public void setUp() throws Exception {
        LinkedLevels linkedLevels = new LinkedLevels();
    }

    @Test
    public void TestAddLevel() {
        List<Level> linkedLevel = new ArrayList<>();
        brickList = new ArrayList<>();
        NullLevel beginLevel = new NullLevel();
        NullLevel endLevel = new NullLevel();
        realLevel addfourthLevel = new realLevel("This is the fourth Level",brickList);
        realLevel thirdLevel = new realLevel("This is the third Level",brickList,endLevel);
        realLevel secondLevel = new realLevel("This is the second Level",brickList);
        realLevel firstLevel = new realLevel("This is the first Level",brickList);

        //linkedLevels.addLast(firstLevel);
        //linkedLevels.addLast(secondLevel);
        //linkedLevels.addLast(endLevel);
        //assertEquals("",endLevel.getName());
        //assertEquals("This is the first Level",linkedLevels.getFirst().getNextLevel());

        assertEquals("",firstLevel.getNextLevel().getName());
        firstLevel.addPlayingLevel(secondLevel);
        assertEquals("This is the second Level",firstLevel.getNextLevel().getName());
        firstLevel.addPlayingLevel(thirdLevel);
        assertEquals("This is the third Level",firstLevel.getNextLevel().getNextLevel().getName());
        assertFalse(thirdLevel.hasNextLevel());
        //thirdLevel.addPlayingLevel(addfourthLevel);
        //assertEquals("This is the fourth Level",secondLevel.getNextLevel().getNextLevel().getName());


    }
}

