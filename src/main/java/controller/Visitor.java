package controller;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.NullLevel;
import logic.level.RealLevel;


/**
 * Class who will notify to game when .
 */
public class Visitor {
    public void visitRealLevel(RealLevel realLevel){

    }
    public void visitNullLevel(NullLevel nullLevel){

    }

    public void visitGlassBrick(GlassBrick glassBrick){
        glassBrick.
    }

    public void visitWoodenBrick(WoodenBrick woodenBrick){

    }

    public void visitMetalBrick(MetalBrick metalBrick){

    }
}
