package controller;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.NullLevel;
import logic.level.RealLevel;


/**
 * Class who will notify to game when .
 */
public interface Visitor {
    void visitRealLevel(RealLevel realLevel);
    void visitNullLevel(NullLevel nullLevel);
    void visitGlassBrick(GlassBrick glassBrick);
    void visitWoodenBrick(WoodenBrick woodenBrick);
    void visitMetalBrick(MetalBrick metalBrick);
}
