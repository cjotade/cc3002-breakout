package logic.level;

import logic.brick.Brick;

import java.util.ArrayList;
import java.util.List;

public class realLevel implements Level{
    private String name;
    private List<Brick> brickList;
    private Level next;

    public realLevel(String name, List<Brick> brickList) {
        this.name = name;
        this.brickList = brickList;
        next = new NullLevel();
    }

    public realLevel(String name, List<Brick> brickList, Level next) {
        this.name = name;
        this.brickList = brickList;
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
    public Level getNextLevel() {
        return next;
    }

    @Override
    public boolean isPlayableLevel() {
        return true;
    }

    @Override
    public boolean hasNextLevel() {
        return next.isPlayableLevel();
    }

    @Override
    public int getPoints() {
        return 0;
    }


    @Override
    public Level addPlayingLevel(Level level) {
       if (hasNextLevel()){
           getNextLevel().addPlayingLevel(level);
       }
       else{
           setNextLevel(level);
       }
       return this;
    }

    @Override
    public void setNextLevel(Level level) {
        next = level;
    }


}
