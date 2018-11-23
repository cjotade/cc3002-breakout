package logic.brick;

import controller.LogicElement;
import controller.Visitor;

public class GlassBrick extends AbstractBrick implements LogicElement {
    public GlassBrick(){
        super(1,50);
    }



    public void accept(Visitor visitor){
        visitor.visitGlassBrick(this);
    }
}
