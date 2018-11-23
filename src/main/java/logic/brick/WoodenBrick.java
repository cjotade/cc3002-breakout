package logic.brick;

import controller.LogicElement;
import controller.Visitor;

public class WoodenBrick extends AbstractBrick implements LogicElement {
    public WoodenBrick() {
        super(3, 200);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitWoodenBrick(this);
    }
}
