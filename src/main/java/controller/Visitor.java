package controller;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.level.RealLevel;


/**
 * Class who will notify to game when is something change.
 *
 * @author Camilo Jara Do Nascimento
 */
public interface Visitor {
    /**
     * Notify to game and pass throw the next level if the current game points are the same as cumulative Level points.
     * @param realLevel a Real Level.
     * @see RealLevel
     */
    void visitRealLevel(RealLevel realLevel);

    /**
     * Notify to game and change the current game points if a Glass Brick is destroyed.
     * @param glassBrick a Glass Brick.
     * @see GlassBrick
     */
    void visitGlassBrick(GlassBrick glassBrick);

    /**
     * Notify to game and change the current game points if a Wooden Brick is destroyed.
     * @param woodenBrick a Wooden Brick.
     * @see WoodenBrick
     */
    void visitWoodenBrick(WoodenBrick woodenBrick);

    /**
     * Notify to game and change the current number of balls if a Metal Brick is destroyed.
     * @param metalBrick a Metal Brick.
     * @see MetalBrick
     */
    void visitMetalBrick(MetalBrick metalBrick);
}
