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
}
