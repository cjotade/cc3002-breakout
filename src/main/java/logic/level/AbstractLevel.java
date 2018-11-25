package logic.level;

import controller.Game;
import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;

import java.util.*;

public abstract class AbstractLevel extends Observable implements Level{
    protected String name;
    protected List<Brick> brickList;
    protected Level next;
    protected int scoreLevel;
    protected int currentScoreLevel = 0;

    public AbstractLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        this.name = name;
        brickList = createListBricks(numberOfBricks,probOfGlass,probOfMetal,seed);
        scoreLevel = getPoints();
    }

    public AbstractLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed, Level next) {
        this(name, numberOfBricks, probOfGlass, probOfMetal, seed);
        this.next = next;
    }

    public AbstractLevel(String name, List<Brick> brickList) {
        this.name = name;
        this.brickList = brickList;
        scoreLevel = getPoints();
    }


    public List<Brick> createListBricks(int numberOfBricks, double probOfGlass, double probOfMetal, int seed){
        List<Brick> brickList = new ArrayList<>();
        Random rand = new Random(seed);
        for(int i = 0; i<numberOfBricks; i++) {
            double prob = rand.nextDouble();
            if (prob < probOfGlass) {
                GlassBrick glassBrick = new GlassBrick();
                glassBrick.subscribeLevelObserver(this);
                brickList.add(glassBrick);
            } else {
                WoodenBrick woodenBrick = new WoodenBrick();
                woodenBrick.subscribeLevelObserver(this);
                brickList.add(woodenBrick);
            }
        }
        for(int j = 0; j<numberOfBricks; j++){
            double probM = rand.nextDouble();
            if (probM < probOfMetal){
                MetalBrick metalBrick = new MetalBrick();
                metalBrick.subscribeLevelObserver(this);
                brickList.add(new MetalBrick());
            }
        }
        return brickList;
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
    public int getPoints() {
        int points = 0;
        for(Brick brick : brickList){
            points += brick.getScore();
        }
        return points;
    }

    public void subscribeGameObserver(Game game) {
        addObserver(game);
    }
}
