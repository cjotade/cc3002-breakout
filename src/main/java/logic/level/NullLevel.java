package logic.level;

import controller.Game;
import controller.LogicElement;
import controller.Visitor;
import logic.brick.Brick;

import java.util.ArrayList;
import java.util.Observable;

public class NullLevel extends AbstractLevel {

    public NullLevel(){
        super("", new ArrayList<Brick>());
        next = this;
    }

    @Override
    public Level getNextLevel() {
        return this;
    }

    public boolean isPlayableLevel() {
        return false;
    }

    @Override
    public boolean hasNextLevel() {
        return false;
    }


    @Override
    public Level addPlayingLevel(Level level) {
        //next = this;
        //level.addPlayingLevel(this);
        return this;
    }


    public void setNextLevel(Level level) {
        next = this;
    }

    @Override
    public void update(Observable o, Object arg) {
        //setChanged();
        //notifyObservers(this);
    }
    /*
    @Override
    public void accept(Visitor visitor) {
        //visitor.visitNullLevel(this);
    }
    */

}
