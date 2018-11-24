package logic.brick;

import controller.LogicElement;
import controller.Visitor;
import logic.level.Level;

import java.util.Observable;
import java.util.Observer;

public class MetalBrick extends AbstractBrick implements LogicElement {
    public MetalBrick() {
        super(10, 0);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitMetalBrick(this);
    }
}
