package logic.brick;

import controller.LogicElement;
import controller.Visitor;

public class MetalBrick extends AbstractBrick implements LogicElement {
    public MetalBrick() {
        super(10, 0);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitMetalBrick(this);
    }
}
