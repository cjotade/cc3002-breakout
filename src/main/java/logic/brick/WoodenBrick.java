package logic.brick;

import controller.LogicElement;
import controller.Visitor;

/**
 * Class for a WoodenBrick.
 *
 * @author Camilo Jara Do Nascimento.
 */
public class WoodenBrick extends AbstractBrick implements LogicElement {
    /**
     * Constructor for a default WoodenBrick.
     */
    public WoodenBrick() {
        super(3, 200);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitWoodenBrick(this);
    }
}
