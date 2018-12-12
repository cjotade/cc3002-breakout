package gui.control;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import logic.brick.Brick;

public class BrickComponent extends Component {
    private Brick brick;

    public BrickComponent(Brick brick) {
        this.brick = brick;
    }

    public void hitBrick() {
       brick.hit();
    }

    public boolean isDestroyed(){
        return brick.isDestroyed();
    }

    public boolean isMetalComponent(){
        return brick.isMetalBrick();
    }
}
