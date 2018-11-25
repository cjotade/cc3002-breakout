package logic.level;

import logic.brick.Brick;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Class for a Null Level that represent the start and the end of a Game.
 *
 * @author Camilo Jara Do Nascimento.
 */
public class NullLevel extends AbstractLevel {

    /**
     * Constructor for default NullLevel.
     */
    public NullLevel(){
        super("", new ArrayList<Brick>());
        next = this;
    }

    @Override
    public Level getNextLevel() {
        return this;
    }

    @Override
    public boolean isPlayableLevel() {
        return false;
    }

    @Override
    public boolean hasNextLevel() {
        return false;
    }


    @Override
    public Level addPlayingLevel(Level level) {
        return this;
    }

    /**
     * Set the reference to the next Level
     * Note that the NullLevel's next level is itself
     *
     * @param level the next level of a level object
     */
    @Override
    public void setNextLevel(Level level) {
        next = this;
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
