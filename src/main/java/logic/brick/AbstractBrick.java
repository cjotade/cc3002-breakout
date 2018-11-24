package logic.brick;

import controller.Game;
import logic.level.Level;

import java.util.Observable;

public abstract class AbstractBrick extends Observable implements Brick{
    private int remainingHits;
    private int score;

    public AbstractBrick(int hits, int score){
        this.remainingHits = hits;
        this.score = score;
    }

    @Override
    public void hit() {
        if(remainingHits() != 0) {
            remainingHits -= 1;
            if(isDestroyed()){
                setChanged();
                notifyObservers(this);
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return remainingHits==0;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int remainingHits() {
        return remainingHits;
    }

    @Override
    public void subscribeLevelObserver(Level o){
        addObserver(o);
    }

    @Override
    public void subscribeGameObserver(Game o) {
        addObserver(o);
    }
}
