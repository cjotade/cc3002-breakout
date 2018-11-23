package logic.level;

import logic.brick.Brick;

import java.util.List;
import java.util.Observable;

public abstract class AbstractLevel extends Observable implements Level{
    protected String name;
    protected List<Brick> brickList;
    protected Level next;
    protected int scoreLevel;

    public AbstractLevel(String name, List<Brick> brickList) {
        this.name = name;
        this.brickList = brickList;
        scoreLevel = 0;
    }

    public AbstractLevel(String name, List<Brick> brickList, Level next) {
        this(name,brickList);
        this.next = next;
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
    public int getPoints() {
        return 0;
    }

    public void setScoreLevel(int scoreLevel) {
        this.scoreLevel = scoreLevel;
    }

    public int getScoreLevel() {
        return scoreLevel;
    }
}
