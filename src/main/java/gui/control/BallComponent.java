package gui.control;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class BallComponent extends Component {
    private static final float BOUNCE_FACTOR = 1.5f;
    private static final float SPEED_DECAY = 0.66f;

    private PhysicsComponent physics;
    private float speed = 0;
    private Vec2 velocity = new Vec2();

    private boolean initialCond = true;

    @Override
    public void onUpdate(double tpf) {
        if(initialCond) {
            speed = 1200 * (float) tpf;
            velocity.mulLocal(SPEED_DECAY);
            if (entity.getX() < 0) {
                velocity.set(BOUNCE_FACTOR * (float) -entity.getX(), 0);
            } else if (entity.getRightX() > FXGL.getApp().getWidth()) {
                velocity.set(BOUNCE_FACTOR * (float) -(entity.getRightX() - FXGL.getApp().getWidth()), 0);
            }
            physics.setBodyLinearVelocity(velocity);
        }
        else{
            limitVelocity();
        }
    }

    private void limitVelocity() {
        if (Math.abs(physics.getLinearVelocity().getX()) < 5 * 60) {
            physics.setLinearVelocity(Math.signum(physics.getLinearVelocity().getX()) * 5 * 60, physics.getLinearVelocity().getY());
        }

        if (Math.abs(physics.getLinearVelocity().getY()) < 5 * 60) {
            physics.setLinearVelocity(physics.getLinearVelocity().getX(), Math.signum(physics.getLinearVelocity().getY()) * 5 * 60);
        }

    }

    public void left(){
        velocity.set(-speed,0);
    }

    public void right(){
        velocity.set(speed,0);
    }

    public boolean isInitialCond() {
        return initialCond;
    }

    public void setInitialCond(boolean initialCond) {
        this.initialCond = initialCond;
    }
}
