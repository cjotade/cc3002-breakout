package controller;

import facade.HomeworkTwoFacade;
import logic.brick.Brick;
import logic.level.Level;
import logic.level.realLevel;
import sun.reflect.generics.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 */
public class Game {

    public Game(int balls) {

    }

    public Level newLevelWithBricksFull(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        Level level = new realLevel(name,);
        return ;
    }


    public Level newLevelWithBricksNoMetal(String name, int numberOfBricks, double probOfGlass, int seed) {
        Random rand = new Random(seed);
        List<Brick> brickList = new ArrayList<>();
        for(int i = 0; i<numberOfBricks; i++){

        }
        return super.newLevelWithBricksNoMetal(name, numberOfBricks, probOfGlass, seed);
    }


    public int numberOfBricks() {
        return super.numberOfBricks();
    }


    public List<Brick> getBricks() {
        return super.getBricks();
    }


    public boolean hasNextLevel() {
        return super.hasNextLevel();
    }


    public void goNextLevel() {
        super.goNextLevel();
    }


    public boolean hasCurrentLevel() {
        return super.hasCurrentLevel();
    }


    public String getLevelName() {
        return super.getLevelName();
    }


    public Level getCurrentLevel() {
        return super.getCurrentLevel();
    }


    public void setCurrentLevel(Level level) {
        super.setCurrentLevel(level);
    }


    public void addPlayingLevel(Level level) {
        super.addPlayingLevel(level);
    }


    public int getLevelPoints() {
        return super.getLevelPoints();
    }


    public int getCurrentPoints() {
        return super.getCurrentPoints();
    }


    public int getBallsLeft() {
        return super.getBallsLeft();
    }


    public int dropBall() {
        return super.dropBall();
    }


    public boolean isGameOver() {
        return super.isGameOver();
    }

    /**
     * This method is just an example. Change it or delete it at wish.
     * <p>
     * Checks whether the game has a winner or not
     *
     * @return true if the game has a winner, false otherwise
     */
    public boolean winner() {
        return false;
    }
}
