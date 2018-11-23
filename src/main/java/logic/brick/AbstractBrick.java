package logic.brick;

import java.util.Observable;

public abstract class AbstractBrick extends Observable implements Brick{
    private int hits;
    private int score;

    public AbstractBrick(int hits, int score){
        this.hits = hits;
        this.score = score;
    }

    @Override
    public void hit() {
        if(remainingHits() != 0) {
            hits -= 1;
            if(isDestroyed()){
                setChanged();
                notifyObservers(this); //le paso el ladrillo
            }
        }


    }

    @Override
    public boolean isDestroyed() {
        return hits==0;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int remainingHits() {
        return hits;
    }

}
