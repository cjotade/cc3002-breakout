package logic.brick;

import controller.LogicElement;
import controller.Visitor;

/**
 * Class for a MetalBrick.
 *
 * @author Camilo Jara Do Nascimento.
 */
public class MetalBrick extends AbstractBrick implements LogicElement {
    /**
     * Constructor for a default MetalBrick.
     */
    public MetalBrick() {
        super(10, 0);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitMetalBrick(this);
    }

    @Override
    public boolean isGlassBrick() {
        return false;
    }

    @Override
    public boolean isWoodenBrick() {
        return false;
    }

    @Override
    public boolean isMetalBrick() {
        return true;
    }

}
