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
 */
public class Game implements Observer {
    private int balls;
    private Level currentLevel;

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
        Random rand = new Random(seed);
        List<Brick> brickList = new ArrayList<>();
        for(int i = 0; i<numberOfBricks; i++) {
            double prob = rand.nextDouble();
            if (prob < probOfGlass) {
                brickList.add(new GlassBrick());
            } else {
                brickList.add(new WoodenBrick());
            }
        }
        for(int j = 0; j<numberOfBricks; j++){
            double probM = rand.nextDouble();
            if (probM < probOfMetal){
                brickList.add(new MetalBrick());
            }
        }
        Level level = new RealLevel(name,brickList);
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
     * Gets the number of {@link Brick} in the current level, that are still not destroyed
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
        return 0;
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
        balls -= 1;
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
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        LogicElement obs = (LogicElement) arg;
        obs.accept(new Visitor());
    }

    /*
    @Override
    public void update(Observable o, Object arg) {
        visitBrick((Brick) arg);
    }

    private void visitBrick(Brick arg) {

    }
    */
}
