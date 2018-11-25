package logic.level;

import controller.LogicElement;
import controller.Visitor;
import logic.brick.Brick;

import java.util.List;
import java.util.Observable;

/**
 * Class for a Playable Level.
 *
 * @author Camilo Jara Do Nascimento.
 */
public class RealLevel extends AbstractLevel implements LogicElement {

    /**
     * Constructor for a RealLevel setting its name, number of bricks,
     * probability of Glass, probability of Metal and a seed for the random number generator.
     *
     * @param name the name of the level
     * @param numberOfBricks the number of bricks in the level
     * @param probOfGlass the probability of a {@link logic.brick.GlassBrick}
     * @param probOfMetal the probability of a {@link logic.brick.MetalBrick}
     * @param seed the seed for the random number generator
     */
    public RealLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        super(name, numberOfBricks, probOfGlass, probOfMetal, seed, new NullLevel());
    }

    /**
     * Constructor for a RealLevel setting its name and the brick list of the level.
     *
     * @param name the name of the level.
     * @param brickList the brick list of the level.
     */
    public RealLevel(String name, List<Brick> brickList) {
        super(name, brickList);
        this.next = new NullLevel();
    }

    /**
     * Constructor for a RealLevel setting its name, the brick list of the level and the next level.
     *
     * @param name the name of the level.
     * @param brickList the brick list of the level.
     * @param next the next level.
     */
    public RealLevel(String name, List<Brick> brickList, Level next){
        this(name,brickList);
        this.next = next;
    }

    @Override
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
