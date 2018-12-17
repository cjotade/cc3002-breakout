package gui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import gui.control.BarComponent;
import gui.control.BrickComponent;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.brick.Brick;

import java.util.List;

import static com.almasb.fxgl.app.DSLKt.texture;

public class BreakoutFactory {

    private List<Entity> BrickEntityList;

    public static Entity newBrick(double x, double y, Brick brick) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        Color color = Color.RED;
        if(brick.isWoodenBrick()){
            color = Color.BROWN;
        }
        if(brick.isGlassBrick()){
            color = Color.AQUA;
        }
        if(brick.isMetalBrick()){
            color = Color.LIGHTGRAY;
        }

        return Entities.builder()
                .at(x, y)
                .type(BreakoutType.BRICK)
                .bbox(new HitBox("Brick", BoundingShape.box(100,30)))
                .viewFromNode(new Rectangle(100, 30, color))
                .with(physics, new CollidableComponent(true))
                .with(new BrickComponent(brick))
                .build();
    }

    public static Entity newBar(double x, double y, BreakoutType breakoutType){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);

        return Entities.builder()
                .at(x, y)
                .type(breakoutType.BAR)
                //.viewFromNodeWithBBox(new Rectangle(180, 25, Color.BLUE))
                .viewFromTextureWithBBox("bar5.png") //ball_icon
                .with(physics,new CollidableComponent(true))
                .with(new BarComponent())
                .build();
    }

    public static Entity newBall(double x, double y) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().restitution(1f).density(0.1f));
        //physics.setOnPhysicsInitialized(() -> physics.setLinearVelocity(-50, -1000));

        return Entities.builder()
                .at(x, y)
                .type(BreakoutType.BALL)
                .viewFromTextureWithBBox("moon1.png")
                .with(physics,new CollidableComponent(true))
                .build();
    }

    public static Entity newWall(){
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(BreakoutType.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }

    /**
     * Get a new entity with game background to set.
     * @return background entity
     */
    static Entity newBackground() {
        return Entities.builder()
                .viewFromTexture("back5.png")
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    /**
     * Get a new explosion effect entity.
     * @param x Position in x axis.
     * @param y Position in y axis.
     * @return effect entity
     */
    static Entity newFireEffect(double x, double y){
        ParticleEmitter emitter = ParticleEmitters.newFireEmitter();
        emitter.setColor(Color.ORANGE);
        emitter.setBlendMode(BlendMode.HARD_LIGHT);
        ParticleComponent component = new ParticleComponent(emitter);
        return Entities.builder()
                .at(x,y)
                .with(component)
                .build();
    }

}
