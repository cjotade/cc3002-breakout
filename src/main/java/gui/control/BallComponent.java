package gui.control;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

/**
 * Class for a Ball Component.
 *
 * @author Camilo Jara Do Nascimento
 */
public class BallComponent extends Component {
    private PhysicsComponent physics;


    @Override
    public void onUpdate(double tpf) {
        limitVelocity();
    }

    private void limitVelocity() {
        if (Math.abs(physics.getLinearVelocity().getX()) < 5 * 60) {
            physics.setLinearVelocity(Math.signum(physics.getLinearVelocity().getX()) * 5 * 60, physics.getLinearVelocity().getY());
        }

        if (Math.abs(physics.getLinearVelocity().getY()) < 5 * 60) {
            physics.setLinearVelocity(physics.getLinearVelocity().getX(), Math.signum(physics.getLinearVelocity().getY()) * 5 * 60);
        }

    }

}
