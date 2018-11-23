package logic.level;

import controller.Game;
import controller.LogicElement;
import controller.Visitor;
import logic.brick.Brick;

import java.util.List;
import java.util.Observable;

public class RealLevel extends AbstractLevel implements LogicElement {

    public RealLevel(String name, List<Brick> brickList) {
        super(name,brickList,new NullLevel());
    }

    public RealLevel(String name, List<Brick> brickList, Level next) {
        super(name,brickList,next);
    }

    //recorrer lista ladrillos y agregar nivel como observador


    public boolean isPlayableLevel() {
        return true;
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
    public boolean hasNextLevel() {
        return getNextLevel().isPlayableLevel();
    }

    @Override
    public Level getNextLevel() {
        return next;
    }

    public void setNextLevel(Level level) {
        next = level;
    }

    public void addGameObserver(Game game) {
        addObserver(game);
    }


    @Override
    public void update(Observable o, Object arg) {
        scoreLevel += ((Brick) arg).getScore();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitRealLevel(this);
    }
}
