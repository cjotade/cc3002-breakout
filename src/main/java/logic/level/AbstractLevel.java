package logic.level;

import logic.brick.Brick;

import java.util.List;

public abstract class AbstractLevel implements Level{
    private String name;
    private List<Brick> brickList;
    private Level next;

    public AbstractLevel(String name, List<Brick> brickList) {
        this.name = name;
        this.brickList = brickList;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfBricks() {
        return brickList.size();
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
    public int getPoints() {
        return 0;
    }

    @Override
    public Level addPlayingLevel(Level level) {
        return null;
    }

    @Override
    public void setNextLevel(Level level) {
        next = level;
    }

    @Override
    public boolean hasNextLevel() {
        return getNextLevel().isPlayableLevel();
    }
}
