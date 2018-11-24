package logic.level;

import controller.Game;
import controller.LogicElement;
import controller.Visitor;
import logic.brick.Brick;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class RealLevel extends AbstractLevel implements LogicElement {

    public RealLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        super(name, numberOfBricks, probOfGlass, probOfMetal, seed, new NullLevel());
    }

    public RealLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed, Level next) {
        super(name, numberOfBricks, probOfGlass, probOfMetal, seed, next);
    }

    public RealLevel(String name, List<Brick> brickList) {
        super(name, brickList);
    }

    public RealLevel(String name, List<Brick> brickList, Level next){
        this(name,brickList);
        this.next = next;
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

    @Override
    public void update(Observable o, Object arg) {
        currentScoreLevel += ((Brick) arg).getScore();
        if(currentScoreLevel == scoreLevel){
            setChanged();
            notifyObservers(this);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitRealLevel(this);
    }
}
