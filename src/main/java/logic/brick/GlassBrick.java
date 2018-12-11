package logic.brick;

import controller.LogicElement;
import controller.Visitor;

/**
 * Class for a GlassBrick.
 *
 * @author Camilo Jara Do Nascimento.
 */
public class GlassBrick extends AbstractBrick implements LogicElement {
    /**
     * Constructor for a default GlassBrick.
     */
    public GlassBrick(){
        super(1,50);
    }

    @Override
    public void accept(Visitor visitor){
        visitor.visitGlassBrick(this);
    }

    @Override
    public boolean isGlassBrick() {
        return true;
    }

    @Override
    public boolean isWoodenBrick() {
        return false;
    }

    @Override
    public boolean isMetalBrick() {
        return false;
    }
}
