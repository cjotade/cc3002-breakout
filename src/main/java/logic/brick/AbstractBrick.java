package logic.brick;

public abstract class AbstractBrick implements Brick{
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
