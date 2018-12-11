package logic.level;

import controller.Game;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

import java.util.*;

/**
 * Abstract class that represents general behavior of Levels using the Null Pattern.
 *
 * @author Camilo Jara Do Nascimento
 */
public abstract class AbstractLevel extends Observable implements Level{
    protected String name;
    protected List<Brick> brickList;
    protected Level next;
    protected int scoreLevel;
    protected int currentScoreLevel = 0;
    protected int currentNumberOfBricks;

    /**
     * Constructor for an AbstractLevel setting its name, number of bricks,
     * probability of Glass, probability of Metal and a seed for the random number generator.
     *
     * @param name the name of the level
     * @param numberOfBricks the number of bricks in the level
     * @param probOfGlass the probability of a {@link logic.brick.GlassBrick}
     * @param probOfMetal the probability of a {@link logic.brick.MetalBrick}
     * @param seed the seed for the random number generator
     */
    public AbstractLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        this.name = name;
        brickList = createListBricks(numberOfBricks,probOfGlass,probOfMetal,seed);
        scoreLevel = getPoints();
        currentNumberOfBricks = brickList.size();
    }

    /**
     * Constructor for an AbstractLevel setting its name, number of bricks, probability of Glass,
     * probability of Metal, a seed for the random number generator and the next Level.
     *
     * @param name the name of the level
     * @param numberOfBricks the number of bricks in the level
     * @param probOfGlass the probability of a {@link logic.brick.GlassBrick}
     * @param probOfMetal the probability of a {@link logic.brick.MetalBrick}
     * @param seed the seed for the random number generator
     * @param next the next Level.
     */
    public AbstractLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed, Level next) {
        this(name, numberOfBricks, probOfGlass, probOfMetal, seed);
        this.next = next;
    }

    /**
     * Constructor for an AbstractLevel setting its name and the brick list of the level.
     *
     * @param name the name of the level.
     * @param brickList the brick list of the level.
     */
    public AbstractLevel(String name, List<Brick> brickList) {
        this.name = name;
        this.brickList = brickList;
        scoreLevel = getPoints();
        currentNumberOfBricks = brickList.size();
    }

    /**
     * Creates a Brick's List with the given parameters and notify to the level that a Brick is being added.
     *
     * @param numberOfBricks the number of bricks in the level
     * @param probOfGlass the probability of a {@link logic.brick.GlassBrick}
     * @param probOfMetal the probability of a {@link logic.brick.MetalBrick}
     * @param seed the seed for the random number generator
     * @return a Brick's List determined by the parameters
     */
    public List<Brick> createListBricks(int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        List<Brick> brickList = new ArrayList<>();
        Random rand = new Random(seed);
        for(int i = 0; i<numberOfBricks; i++) {
            double prob = rand.nextDouble();
            if (prob < probOfGlass) {
                GlassBrick glassBrick = new GlassBrick();
                glassBrick.subscribeLevelObserver(this);
                brickList.add(glassBrick);
                currentNumberOfBricks++;
            }
            else {
                WoodenBrick woodenBrick = new WoodenBrick();
                woodenBrick.subscribeLevelObserver(this);
                brickList.add(woodenBrick);
                currentNumberOfBricks++;
            }
        }
        for(int j = 0; j<numberOfBricks; j++){
            double probM = rand.nextDouble();
            if (probM < probOfMetal){
                MetalBrick metalBrick = new MetalBrick();
                metalBrick.subscribeLevelObserver(this);
                brickList.add(new MetalBrick());
                currentNumberOfBricks++;
            }
        }
        return brickList;
    }

    @Override
    public void brickDestroyed(){
        currentNumberOfBricks--;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfBricks() {
        return currentNumberOfBricks;
    }

    @Override
    public List<Brick> getBricks() {
        return brickList;
    }

    @Override
    public int getPoints() {
        int points = 0;
        for(Brick brick : brickList){
            points += brick.getScore();
        }
        return points;
    }

    @Override
    public void subscribeGameObserver(Game game) {
        addObserver(game);
    }
}
