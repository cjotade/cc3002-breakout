package logic.level;

import logic.brick.AbstractBrick;
import logic.brick.Brick;

import java.util.ArrayList;
import java.util.List;

public class NullLevel implements Level {
    private String name;
    List<Brick> brickList;
    private Level next;
    public NullLevel() {
        name = "";
        brickList = new ArrayList<Brick>();
        next = this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfBricks() {
        return 0;
    }

    @Override
    public List<Brick> getBricks() {
        return brickList;
    }

    @Override
    public Level getNextLevel() {
        return next;
    }

    @Override
    public boolean isPlayableLevel() {
        return false;
    }

    @Override
    public boolean hasNextLevel() {
        return getNextLevel().isPlayableLevel();
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public Level addPlayingLevel(Level level) {
        next = this;
        level.addPlayingLevel(this);
        return this;
    }

    @Override
    public void setNextLevel(Level level) {
        next = this;
    }
}
