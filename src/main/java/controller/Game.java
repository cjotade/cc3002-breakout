package controller;

import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.Level;
import logic.level.NullLevel;
import logic.level.RealLevel;

import java.util.*;

/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 * @author Camilo Jara Do Nascimento
 */
public class Game implements Observer,Visitor {
    private int balls;
    private Level currentLevel;
    private int currentPoints;
    private int acumLevelPoints;

    /**
     * Default constructor for a Game
     */
    public Game(){
        this(3);
    }

    /**
     * Constructor for a Game given the number of balls
     *
     * @param balls the number of balls
     */
    public Game(int balls) {
        this.balls = balls;
        currentLevel = new NullLevel();
    }

    /**
     * Creates a new level with the given parameters.
     *
     * @param name           the name of the level
     * @param numberOfBricks the number of bricks in the level
     * @param probOfGlass    the probability of a {@link logic.brick.GlassBrick}
     * @param probOfMetal    the probability of a {@link logic.brick.MetalBrick}
     * @param seed           the seed for the random number generator
     * @return a new level determined by the parameters
     * @see Level
     */
    public Level newLevelWithBricksFull(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        Level level = new RealLevel(name,numberOfBricks,probOfGlass,probOfMetal,seed);
        return level;
    }

    /**
     * Creates a new level with the given parameters with no metal bricks.
     *
     * @param name           the name of the level
     * @param numberOfBricks the number of bricks in the level
     * @param probOfGlass    the probability of a {@link logic.brick.GlassBrick}
     * @param seed           the seed for the random number generator
     * @return a new level determined by the parameters
     * @see Level
     */
    public Level newLevelWithBricksNoMetal(String name, int numberOfBricks, double probOfGlass, int seed) {
        return newLevelWithBricksFull(name, numberOfBricks, probOfGlass, 0, seed);
    }

    /**
     * Gets the number of {@link Brick} in the current level, that are still not destroyed.
     *
     * @return the number of intact bricks in the current level
     */
    public int numberOfBricks() {
        return getCurrentLevel().getNumberOfBricks();
    }

    /**
     * Gets the list of {@link Brick} in the current level.
     *
     * @return the list of bricks
     */
    public List<Brick> getBricks() {
        return getCurrentLevel().getBricks();
    }

    /**
     * Whether the current {@link Level}'s next level is playable or not.
     *
     * @return true if the current level's next level is playable, false otherwise
     */
    public boolean hasNextLevel() {
        return getCurrentLevel().hasNextLevel();
    }

    /**
     * Pass to the next level of the current {@link Level}. Ignores all conditions and skip to the next level.
     */
    public void goNextLevel() {
        setCurrentLevel(getCurrentLevel().getNextLevel());
    }

    /**
     * Gets whether the current {@link Level} is playable or not.
     *
     * @return true if the current level is playable, false otherwise
     */
    public boolean hasCurrentLevel() {
        return getCurrentLevel().isPlayableLevel();
    }

    /**
     * Gets the name of the current {@link Level}.
     *
     * @return the name of the current level
     */
    public String getLevelName() {
        return getCurrentLevel().getName();
    }

    /**
     * Gets the current {@link Level}.
     *
     * @return the current level
     * @see Level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets a {@link Level} as the current playing level.
     *
     * @param level the level to be used as the current level
     * @see Level
     */
    public void setCurrentLevel(Level level) {
        currentLevel = level;
        currentLevel.subscribeGameObserver(this);
        List<Brick> levelBricks = currentLevel.getBricks();
        for(Brick brick: levelBricks){
            brick.subscribeGameObserver(this);
        }

    }

    /**
     * Adds a level to the list of {@link Level} to play. This adds the level in the last position of the list.
     *
     * @param level the level to be added
     */
    public void addPlayingLevel(Level level) {
        currentLevel.addPlayingLevel(level);
    }

    /**
     * Gets the number of points required to pass to the next level. Gets the points obtainable in the current {@link Level}.
     *
     * @return the number of points in the current level
     */
    public int getLevelPoints() {
        return getCurrentLevel().getPoints();
    }

    /**
     * Gets the accumulated points through all levels and current {@link Level}.
     *
     * @return the cumulative points
     */
    public int getCurrentPoints() {
        return currentPoints;
    }

    /**
     * Gets the current number of available balls to play.
     *
     * @return the number of available balls
     */
    public int getBallsLeft() {
        return balls;
    }

    /**
     * Reduces the count of available balls and returns the new number.
     *
     * @return the new number of available balls
     */
    public int dropBall() {
        if(!isGameOver()){
            balls -= 1;
        }
        return balls;
    }

    /**
     * Checks whether the game is over or not. A game is over when the number of available balls are 0 or the player won the game.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return balls == 0;
    }

    /**
     * Gets the state of the player.
     *
     * @return true if the player won the game, false otherwise
     */
    public boolean winner() {
        return !getCurrentLevel().isPlayableLevel() && currentPoints >0;
    }

    /**
     * Gets the cumulative Points of the current Level.
     *
     * @return the cumulative Level Points
     */
    public int getAcumLevelPoints() {
        return acumLevelPoints;
    }

    @Override
    public void visitRealLevel(RealLevel realLevel) {
        acumLevelPoints += currentLevel.getPoints();
        if(currentPoints == acumLevelPoints) {
            goNextLevel();
            realLevel.subscribeGameObserver(this);
        }
    }

    @Override
    public void visitGlassBrick(GlassBrick glassBrick) {
        if(glassBrick.isDestroyed()){
            currentPoints += glassBrick.getScore();
            getCurrentLevel().brickDestroyed();
        }
        glassBrick.subscribeGameObserver(this);
    }


    @Override
    public void visitWoodenBrick(WoodenBrick woodenBrick) {
        if(woodenBrick.isDestroyed()){
            currentPoints += woodenBrick.getScore();
            getCurrentLevel().brickDestroyed();
        }
        woodenBrick.subscribeGameObserver(this);
    }

    @Override
    public void visitMetalBrick(MetalBrick metalBrick) {
        if(metalBrick.isDestroyed()) {
            balls += 1;
            getCurrentLevel().brickDestroyed();
        }
        metalBrick.subscribeGameObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
          LogicElement obs = (LogicElement) arg;
        obs.accept(this);
    }
}
