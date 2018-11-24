package logic.brick;

import controller.LogicElement;
import controller.Visitor;
import logic.level.Level;

import java.util.Observer;

public class GlassBrick extends AbstractBrick implements LogicElement {
    public GlassBrick(){
        super(1,50);
    }

    public void accept(Visitor visitor){
        visitor.visitGlassBrick(this);
    }
}
