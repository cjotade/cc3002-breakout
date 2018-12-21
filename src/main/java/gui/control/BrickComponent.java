package gui.control;

import com.almasb.fxgl.entity.component.Component;
import logic.brick.Brick;

/**
 * Class for a Brick Component
 *
 * @author Camilo Jara Do Nascimento
 */
public class BrickComponent extends Component {
    private Brick brick;

    /**
     * Constructor for a default Brick Component.
     *
     * @param brick the brick {@link logic.brick.Brick}
     *
     */
    public BrickComponent(Brick brick) {
        this.brick = brick;
    }

    /**
     * Defines that a brick has been hit.
     * Implementations should consider the events that a hit to a brick can trigger.
     */
    public void hitBrick() {
       brick.hit();
    }

    /**
     * Gets whether the brick object is destroyed or not.
     *
     * @return true if the brick is destroyed, false otherwise
     */
    public boolean isDestroyed(){
        return brick.isDestroyed();
    }

    /**
     * Gets the remaining hits the brick has to receive before being destroyed.
     *
     * @return the remaining hits to destroy de brick
     */
    public int remainingHits(){
        return brick.remainingHits();
    }

    /**
     * Gets whether the brick Component is Metal Brick.
     *
     * @return true if the brick Component is Metal, false otherwise
     */
    public boolean isMetalComponent(){
        return brick.isMetalBrick();
    }

    /**
     * Gets whether the brick Component is Wooden Brick.
     *
     * @return true if the brick Component is Wooden, false otherwise
     */
    public boolean isWoodenComponent(){
        return brick.isWoodenBrick();
    }

    /**
     * Gets whether the brick Component is Glass Brick.
     *
     * @return true if the brick Component is Glass, false otherwise
     */
    public boolean isGlassComponent(){
        return brick.isGlassBrick();
    }

}
